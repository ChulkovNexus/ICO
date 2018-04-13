package com.icoalert.ui

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.View


abstract class TabbedPagerAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm) {

    abstract fun getTabView(position : Int) : View
}