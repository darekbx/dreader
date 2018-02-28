package com.dreader.ui

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.dreader.model.Item

class PagerAdapter(
        fm: FragmentManager?,
        val items: List<Item>) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int) = ItemFragment().apply { item = items[position]}

    override fun getCount() = items.size
}