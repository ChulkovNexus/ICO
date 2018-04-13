package com.icoalert.api.requests.ws.response

import com.icoalert.api.data.IcoDto
import com.icoalert.api.ws.ApiMessage

class IcoListResponse : ApiMessage {
    var result = ArrayList<IcoDto>()
}