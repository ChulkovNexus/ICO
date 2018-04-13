package com.icoalert.api.requests.ws.request

import com.icoalert.api.requests.ws.response.IcoListResponse
import com.icoalert.api.ws.ApiMessage
import com.icoalert.api.ws.BaseMessage
import com.icoalert.api.ws.CallbackedMessage


class IcoListRequest : ApiMessage {

    var is_preico = false
    var start = 0
    var end = 0
    var status = 0

    constructor(start: Int, end: Int, status: Int, is_preico: Boolean) {
        this.is_preico = is_preico
        this.start = start
        this.end = end
        this.status = status
    }

    companion object {
        val ACTION = "ico_list"    //NON-NLS

        fun getIcoList(start: Int, end: Int, status: Int, is_preico: Boolean, callback: (IcoListRequest, IcoListResponse) -> Unit): CallbackedMessage<IcoListRequest, IcoListResponse> {
            val request = BaseMessage(IcoListRequest(start, end, status, is_preico), ACTION)
            return CallbackedMessage(request, callback)
        }
    }

}