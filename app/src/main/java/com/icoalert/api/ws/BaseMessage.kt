package com.icoalert.api.ws

import com.icoalert.api.requests.ws.StringAction


class BaseMessage<Request : ApiMessage> {
    var v: Int = 0
    var action: String
    lateinit var ns: String
    var message: Request

    constructor(message: Request, action: String) {
        this.message = message
        this.action = action
        this.v = 1
    }

    constructor(message: Request, action: String, version: Int) : this(message, action) {
        this.v = version
    }

    fun toInfo(): StringAction {
        val res = StringAction()
        res.action = StringAction.ACTION_INFO
        res.ns = ns
        return res
    }
}
