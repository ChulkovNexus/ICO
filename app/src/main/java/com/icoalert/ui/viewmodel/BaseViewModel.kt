package com.icoalert.ui.viewmodel

import android.content.Context
import android.widget.Toast
import com.icoalert.App
import com.icoalert.api.data.DataManager
import com.icoalert.api.requests.http.HttpClient
import com.icoalert.api.requests.ws.ErrorAction
import com.icoalert.api.ws.*
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject


abstract class BaseViewModel (context: Context) {

    @Inject protected lateinit var dataManager: DataManager
    @Inject protected lateinit var httpClient: HttpClient
    @Inject protected lateinit var context: Context
    @Inject protected lateinit var wsConnection: WsConnection

    private val apiCallbacks = HashMap<String, CallbackedMessage<ApiMessage, ApiMessage>>()
    private val subs = mutableListOf<Subscription>()

    init {
        (context.applicationContext as App).icoAlertComponent.inject(this)
    }

    open fun onStart() {
        subs += wsConnection.messagesBehavour.observeOn(AndroidSchedulers.mainThread()).subscribe ({ baseMesage ->
            if (baseMesage.message is ErrorAction) {
                onError(baseMesage as BaseMessage<ErrorAction>)
            } else {
                val callback = apiCallbacks[baseMesage.ns]
                callback?.callback?.invoke(callback.message.message, baseMesage.message)
                onMessage(baseMesage)
            }
        }, {t -> t.printStackTrace()})
    }

    open fun onPause() {
        subs.forEach { it.unsubscribe() }
        subs.clear()
    }

    open fun onMessage(baseMesage: BaseMessage<ApiMessage>) {

    }

    open fun onError(baseMesage: BaseMessage<ErrorAction>) {
        Toast.makeText(context, baseMesage.message.message, Toast.LENGTH_LONG).show()
    }

    protected fun makeRequest(callbackMessage: CallbackedMessage<out ApiMessage, out ApiMessage>) {
        val baseMessage = callbackMessage.message
        baseMessage.ns = wsConnection.increaseAndGetLastNs().toString()
        if (callbackMessage.callback!=null) apiCallbacks.put(baseMessage.ns, callbackMessage as CallbackedMessage<ApiMessage, ApiMessage>)
        wsConnection.sendRequest(baseMessage)
    }
}