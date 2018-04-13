package com.icoalert.api.requests.ws.request

import com.icoalert.api.requests.ws.response.IcoPreviewResponse
import com.icoalert.api.ws.ApiMessage
import com.icoalert.api.ws.BaseMessage
import com.icoalert.api.ws.CallbackedMessage


class IcoPreviewRequest : ApiMessage {

    var is_preico = false
    var search_string: String

    constructor(is_preico: Boolean, searchString: String) {
        this.is_preico = is_preico
        this.search_string = searchString
    }

    companion object {
        val ACTION = "ico_preview"    //NON-NLS

        fun getIcoPreview(is_preico: Boolean, searchString: String = "", callback: (IcoPreviewRequest, IcoPreviewResponse) -> Unit): CallbackedMessage<IcoPreviewRequest, IcoPreviewResponse> {
            val request = BaseMessage(IcoPreviewRequest(is_preico, searchString), ACTION)
            return CallbackedMessage(request, callback)
        }
    }

}