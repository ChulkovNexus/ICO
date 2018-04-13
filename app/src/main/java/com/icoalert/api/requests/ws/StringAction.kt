package com.icoalert.api.requests.ws

import android.content.Context
import android.text.TextUtils
import com.icoalert.api.ws.ApiMessage


class StringAction : ApiMessage {

    var action: String? = null
    var ns: String? = null
    var message: String? = null
    var description: String? = null

    val isError: Boolean
        get() = ACTION_ERROR == action       //NON-NLS

    fun getErrorString(context: Context): String {
        var additionalString = ""
        if (!TextUtils.isEmpty(description)) {
            additionalString += " ($description)"
        }
        val resourceId = context.resources.getIdentifier(message, "string", context.packageName)
        return if (resourceId == 0) {
            message!! + additionalString
        } else {
            context.getString(resourceId) + additionalString
        }
    }

    companion object {
        val ACTION_INFO = "info"
        val ACTION_ERROR = "error"        //NON-NLS
        val CONFIRMATION_EMAIL_SENT = "CONFIRMATION_EMAIL_SENT"

        fun getErrorString(context: Context, message: String): String {
            val resourceId = context.resources.getIdentifier(message, "string", context.packageName)
            return if (resourceId == 0) {
                message
            } else if (message == CONFIRMATION_EMAIL_SENT) {
                context.getString(resourceId)
            } else {
                context.getString(resourceId)
            }
        }
    }
}