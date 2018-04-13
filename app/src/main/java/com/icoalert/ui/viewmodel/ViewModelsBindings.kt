package com.icoalert.ui.viewmodel

import android.databinding.BindingAdapter
import android.databinding.DataBindingComponent
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import com.icoalert.ui.TabbedPagerAdapter
import com.icoalert.ui.fragmenticoslist.IcosListRecyclerAdapter
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class ViewModelsBindings : DataBindingComponent {

    override fun getViewModelsBindings(): ViewModelsBindings {
        return ViewModelsBindings()
    }

    @BindingAdapter("bind:listAdapter")
    fun setRecyclerViewAdapter(recyclerView: RecyclerView, adapter: IcosListRecyclerAdapter) {
        recyclerView.adapter = adapter
    }

    @BindingAdapter("bind:scrollListener")
    fun setRecyclerViewAdapter(recyclerView: RecyclerView, scrollListener: RecyclerView.OnScrollListener) {
        recyclerView.addOnScrollListener(scrollListener)
    }

    @BindingAdapter("bind:layoutManager")
    fun setRecyclerViewAdapter(recyclerView: RecyclerView, layoutManager: RecyclerView.LayoutManager ) {
        recyclerView.layoutManager = layoutManager
    }

    @BindingAdapter("bind:refreshCallback")
    fun setRefreshCallback(swipeRefreshLayout: SwipeRefreshLayout, listener: SwipeRefreshLayout.OnRefreshListener) {
        swipeRefreshLayout.setOnRefreshListener({
            Observable.timer(500, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe {
                swipeRefreshLayout.isRefreshing = false
            }
            listener.onRefresh()
        })
    }

    @BindingAdapter("bind:viewPagerAdapter", "bind:tabs")
    fun setViewPagerAdapter(viewPager: ViewPager, viewPagerAdapter: TabbedPagerAdapter, tabs: TabLayout) {
        viewPager.adapter = viewPagerAdapter
        tabs.setupWithViewPager(viewPager)
        for (i in 0 until tabs.tabCount) {
            val tab = tabs.getTabAt(i)
            tab!!.customView = viewPagerAdapter.getTabView(i)
        }
    }

}