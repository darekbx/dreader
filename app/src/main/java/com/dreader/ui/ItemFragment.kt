package com.dreader.ui

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dreader.GlideApp
import com.dreader.R
import com.dreader.api.Endpoints.Companion.LIMIT
import com.dreader.extensions.hide
import com.dreader.extensions.show
import com.dreader.model.Item

class ItemFragment: Fragment() {

    companion object {
        val M_ADDRESS = "http://m.demotywatory.pl/"
    }

    var item: Item? = null
    var itemPosition: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        item?.let { item ->
            imageView?.let { imageView ->
                GlideApp
                        .with(view.context)
                        .load(item.imageUrl)
                        .listener(handleListener())
                        .into(imageView)

                if (item.isGif) {
                    playButton?.show()
                }
            }
        }

        playButton?.setOnClickListener { playGif() }
        imageView?.setOnClickListener { openInBrowser() }
        pageInfo?.text = "${itemPosition + 1}/$LIMIT"
    }

    private fun openInBrowser() {
        item?.let {
            val address = "${M_ADDRESS}${it.id}"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(address))
            startActivity(intent)
        }
    }

    private fun playGif() {
        playButton?.hide()
        item?.let { item ->
            imageView?.let { imageView ->
                progressBar?.show()
                GlideApp
                        .with(view?.context)
                        .asGif()
                        .load(item.gifUrl)
                        .listener(handleGifListener())
                        .override(item.gifWidth, item.gifHeight)
                        .into(imageView)

            }
        }
    }

    private fun handleListener(): RequestListener<Drawable> {
        return object : RequestListener<Drawable> {
            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                progressBar?.hide()
                return false
            }

            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                progressBar?.hide()
                return false
            }
        }
    }

    private fun handleGifListener(): RequestListener<GifDrawable> {
        return object : RequestListener<GifDrawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<GifDrawable>?, isFirstResource: Boolean): Boolean {
                progressBar?.hide()
                return false
            }

            override fun onResourceReady(resource: GifDrawable?, model: Any?, target: Target<GifDrawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                progressBar?.hide()
                return false
            }
        }
    }

    private val progressBar by lazy { view?.let { it.findViewById(R.id.progress) as ProgressBar } }
    private val playButton by lazy { view?.let { it.findViewById(R.id.play_button) as ImageView } }
    private val imageView by lazy { view?.let { it.findViewById(R.id.image) as ImageView } }
    private val pageInfo by lazy { view?.let { it.findViewById(R.id.page_info) as TextView } }
}