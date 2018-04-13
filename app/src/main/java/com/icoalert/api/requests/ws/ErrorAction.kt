package com.icoalert.api.requests.ws


import com.icoalert.api.ws.ApiMessage


class ErrorAction : ApiMessage {

    var action: String? = null
    var ns: String? = null
    var message: String? = null

    constructor(message: String){
        this.message = message
    }
}