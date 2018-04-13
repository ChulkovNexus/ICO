package com.icoalert.api.requests.ws.response

import com.icoalert.api.data.IcoDto
import com.icoalert.api.ws.ApiMessage


class IcoPreviewResponse : ApiMessage {
    val active: List<IcoDto>? = null
    val upcoming: List<IcoDto>? = null
    val finished: List<IcoDto>? = null
}