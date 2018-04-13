package com.icoalert.ui.fragmenticoslist

import android.content.Context
import android.databinding.ObservableField
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.icoalert.api.requests.ws.request.IcoListRequest
import com.icoalert.api.requests.ws.response.IcoListResponse
import com.icoalert.ui.viewmodel.BaseViewModel
import com.icoalert.ui.viewmodel.ProgressStatuses
import com.icoalert.utils.runOnUiThread
import rx.Subscription


class IcosListViewModel(context: Context, var fragmentType: Int) : BaseViewModel(context){

    private val subs = mutableListOf<Subscription>()
    private val LIMIT = 20
    val adapter = IcosListRecyclerAdapter()
    val layoutManager = LinearLayoutManager(context)
    var progressStatus = ObservableField<ProgressStatuses>(ProgressStatuses.Progress)
    private var start = 0
    private var allowLoadNext = false

    val scrollListener = object : RecyclerView.OnScrollListener() {
        val visibleThreshold = 1

        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val totalItemCount = layoutManager.itemCount
            val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
            if (!adapter.withProgress && allowLoadNext  && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                //End of the items
                downloadIcosWithOffset()
                recyclerView?.post {
                    adapter.withProgress = true
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        subs += dataManager.isosRequestedBehavour.subscribe({
            if (it) {
                start = 0
                adapter.withProgress = false
                adapter.notifyDataSetChanged()
                progressStatus.set(ProgressStatuses.Progress)
            } else if (dataManager.getListByStatus(fragmentType).isEmpty()) {
                progressStatus.set(ProgressStatuses.Empty)
            } else {
                progressStatus.set(ProgressStatuses.Content)
                allowLoadNext = dataManager.getListByStatus(fragmentType).size == LIMIT
                adapter.setData(dataManager.getListByStatus(fragmentType))
            }
        }, { t -> t.printStackTrace()})
    }

    private fun downloadIcosWithOffset() {
        start += LIMIT
        val localOffset = start
        makeRequest(IcoListRequest.getIcoList(start, start + LIMIT, fragmentType, dataManager.isPreico, { request, response:IcoListResponse -> runOnUiThread {
            adapter.withProgress = false
            if (localOffset == start) {
                dataManager.getListByStatus(fragmentType).addAll(response.result)
                adapter.addData(response.result)
                if (response.result.size<LIMIT) {
                    allowLoadNext = false
                }
            }
            adapter.notifyDataSetChanged()
        }}))
    }

    override fun onPause() {
        super.onPause()
        subs.forEach {
            it.unsubscribe()
        }
        subs.clear()
    }
}