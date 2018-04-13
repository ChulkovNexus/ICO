package com.icoalert.ui.activitypager

import android.support.v4.view.MenuItemCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.view.MenuItem
import android.widget.SearchView
import com.icoalert.api.data.DataManager
import com.icoalert.api.requests.ws.request.IcoPreviewRequest
import com.icoalert.api.requests.ws.response.IcoPreviewResponse
import com.icoalert.api.ws.ApiMessage
import com.icoalert.ui.BaseViewModelActivity
import com.icoalert.ui.viewmodel.BaseViewModel

class MainActivitiyViewModel(context: BaseViewModelActivity) : BaseViewModel(context), SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener, SearchView.OnCloseListener {


    val adapter = MainActivityPagerAdapter(context)

    override fun onRefresh() {
        downloadIcos()
    }

    override fun onStart() {
        super.onStart()
        if (dataManager.getListByStatus(DataManager.ICO_STATUS_ACTIVE).isEmpty() &&
                dataManager.getListByStatus(DataManager.ICO_STATUS_UPCOMING).isEmpty() &&
                !dataManager.isosRequestedBehavour.value) {
            downloadIcos()
        }
    }

    override fun onQueryTextSubmit(text: String?): Boolean {
        dataManager.searchRequest = text?:""
        downloadIcos()
        return true
    }

    override fun onQueryTextChange(text: String?): Boolean {
        return true
    }

    override fun onClose(): Boolean {
        if (dataManager.searchRequest != "") {
            dataManager.searchRequest = ""
            downloadIcos()
        }
        return false
    }

    private fun downloadIcos() {
        dataManager.isosRequestedBehavour.onNext(true)
        makeRequest(IcoPreviewRequest.getIcoPreview(dataManager.isPreico, dataManager.searchRequest, previewCallback))
    }

    val previewCallback : (ApiMessage, IcoPreviewResponse)-> Unit = { request, response:IcoPreviewResponse ->
        dataManager.getListByStatus(DataManager.ICO_STATUS_ACTIVE).clear()
        dataManager.getListByStatus(DataManager.ICO_STATUS_ACTIVE).addAll(response.active!!)

        dataManager.getListByStatus(DataManager.ICO_STATUS_UPCOMING).clear()
        dataManager.getListByStatus(DataManager.ICO_STATUS_UPCOMING).addAll(response.upcoming!!)
        dataManager.isosRequestedBehavour.onNext(false)
    }

    fun setIcoType(isPreIco: Boolean) {
        if (dataManager.isPreico != isPreIco && !dataManager.isosRequestedBehavour.value) {
            dataManager.isPreico = isPreIco
            downloadIcos()
        }
    }
}