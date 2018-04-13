package com.icoalert.ui.activitypager

import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.icoalert.R
import com.icoalert.api.data.DataManager
import com.icoalert.ui.BaseViewModelActivity
import com.icoalert.ui.TabbedPagerAdapter
import com.icoalert.ui.fragmenticoslist.IcosListFragment


class MainActivityPagerAdapter(val context : BaseViewModelActivity) : TabbedPagerAdapter(context.supportFragmentManager) {

    private var texts: Array<String> = context.resources.getStringArray(R.array.main_pager_tabs)
    private val activeIcosFragment = IcosListFragment.getInstance(DataManager.ICO_STATUS_ACTIVE)
    private val upcomingIcosFragment = IcosListFragment.getInstance(DataManager.ICO_STATUS_UPCOMING)

    override fun getItem(position: Int): Fragment {
        when (position) {
            1 -> return upcomingIcosFragment
            else -> return activeIcosFragment
        }
    }

    override fun getCount(): Int {
        return texts.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return texts[position]
    }

    override fun getTabView(position: Int): View {
        val view = LayoutInflater.from(context).inflate(R.layout.main_pager_tab, null)
        (view as TextView).text = texts[position]
        return view
    }
}