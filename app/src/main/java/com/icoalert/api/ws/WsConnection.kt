package com.icoalert.api.ws

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.icoalert.api.requests.ws.ErrorAction
import com.icoalert.api.requests.ws.StringAction
import com.icoalert.api.requests.ws.request.IcoListRequest
import com.icoalert.api.requests.ws.request.IcoPreviewRequest
import com.icoalert.api.requests.ws.response.IcoListResponse
import com.icoalert.api.requests.ws.response.IcoPreviewResponse
import okhttp3.*
import okio.ByteString
import org.json.JSONObject
import rx.subjects.BehaviorSubject

class WsConnection(val context: Context) : WebSocketListener() {

    private var ws : WebSocket? = null
    private var lastNs = 0
    private var gson = Gson()
    var messagesBehavour = BehaviorSubject.create<BaseMessage<ApiMessage>>()
    private val NORMAL_CLOSURE_STATUS = 1000

    private fun connectWebSocket() {
        val request = Request.Builder().url("http://ico-list.eoadm.com:8008/ws").build()
        val client = OkHttpClient()
        ws = client.newWebSocket(request, this)
        client.dispatcher().executorService().shutdown()
    }

    fun sendRequest(baseMessage: BaseMessage<out ApiMessage>){
        if (ws == null) connectWebSocket()
        val message = gson.toJson(baseMessage)
        Log.d("wsIcoAlert--->>", message)
        ws?.send(message)
    }

    fun increaseAndGetLastNs() : Int {
        return lastNs++
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
//        webSocket.send("Hello, it's SSaurel !")
//        webSocket.send("What's up ?")
//        webSocket.send(ByteString.decodeHex("deadbeef"))
//        webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !")
    }

    override fun onMessage(webSocket: WebSocket, income: String) {
        Log.d("wsIcoAlert<<---", income)
        val res: BaseMessage<ApiMessage>
        val serrializedMessage: ApiMessage
        val jsonObject = JSONObject(income)
        val action = jsonObject.getString("action")
        val message = jsonObject.getString("message")
        val ns = jsonObject.getString("ns")

        serrializedMessage = serrializeMessage(action, message)
        res = BaseMessage(serrializedMessage, action)
        res.ns = ns
        messagesBehavour.onNext(res)
    }

    private fun serrializeMessage(action: String, message: String): ApiMessage {
        var serrialized:ApiMessage
        try {
            serrialized = when (action) {
                IcoPreviewRequest.ACTION -> gson.fromJson(message, IcoPreviewResponse::class.java)
                IcoListRequest.ACTION -> gson.fromJson(message, IcoListResponse::class.java)
                else -> ErrorAction("no serrializer for response")
            }
        }
        catch (e : JsonParseException) {
            e.printStackTrace()
            return ErrorAction(e.message!!)
        }
        return serrialized
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
//        output("Receiving bytes : " + bytes.hex())
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
//        webSocket.close(NORMAL_CLOSURE_STATUS, null)
//        output("Closing : $code / $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
//        output("Error : " + t.message)
    }

}
