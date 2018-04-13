package com.icoalert.api.ws


import java.util.concurrent.atomic.AtomicInteger

class CallbackedMessage<Req : ApiMessage, Res : ApiMessage>(var message: BaseMessage<Req>, var callback: ((Req, Res) -> Unit)?) {

    constructor(message: Req, action: String, callback: ((Req, Res) -> Unit)?) : this(BaseMessage<Req>(message, action), callback) {}

    init {
        message.ns = lastPid.getAndIncrement().toString() + ""
    }

    companion object {
        var lastPid = AtomicInteger()       // last pid - it's just an auto incremental value
    }
}
