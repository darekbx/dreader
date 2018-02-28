package com.dreader.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.dreader.R
import com.dreader.extensions.hide
import com.dreader.model.Item
import com.dreader.viewmodel.MainViewModel

class MainActivity : FragmentActivity() {

    private lateinit var viewModel: MainViewModel
    var adapter: PagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        with(viewModel) {
            itemsList.observe(this@MainActivity, Observer {
                it?.let { initializeAdapter(it) }
            })
            errorObserver.observe(this@MainActivity, Observer { e ->
                Toast.makeText(this@MainActivity, e, Toast.LENGTH_LONG).show()
            })
        }
        viewModel.fetch()
    }

    private fun initializeAdapter(it: List<Item>) {
        adapter = PagerAdapter(supportFragmentManager, it)
        pager.adapter = adapter

        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) { }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) { }

            override fun onPageSelected(position: Int) {
                pageInfo.text = "${position + 1}/${adapter?.count}"
            }
        })

        findViewById<View>(R.id.progress).hide()
    }

    private val pager by lazy {  findViewById(R.id.pager) as ViewPager  }
    private val pageInfo by lazy { findViewById(R.id.page_info) as TextView }
}