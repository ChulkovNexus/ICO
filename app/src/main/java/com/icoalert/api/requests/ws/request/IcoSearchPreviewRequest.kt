package com.icoalert.api.requests.ws.request

import com.icoalert.api.requests.ws.response.IcoPreviewResponse
import com.icoalert.api.ws.ApiMessage
import com.icoalert.api.ws.BaseMessage
import com.icoalert.api.ws.CallbackedMessage

class IcoSearchPreviewRequest : ApiMessage {

    var is_preico = false
    var search_string = ""
    constructor(is_preico: Boolean, search_string: String) {
        this.is_preico = is_preico
        this.search_string = search_string
    }

    companion object {
        val ACTION = "ico_preview"    //NON-NLS

        fun getIcoSearchPreview(is_preico: Boolean, search_string: String, callback: (IcoSearchPreviewRequest, IcoPreviewResponse) -> Unit): CallbackedMessage<IcoSearchPreviewRequest, IcoPreviewResponse> {
            val request = BaseMessage(IcoSearchPreviewRequest(is_preico, search_string), ACTION)
            return CallbackedMessage(request, callback)
        }
    }

}