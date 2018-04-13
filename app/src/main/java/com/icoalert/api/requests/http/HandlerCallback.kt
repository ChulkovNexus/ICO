/*
 * Copyright © ExpertOption Ltd. All rights reserved.
 */

package com.icoalert.api.requests.http

import com.icoalert.api.data.ErrorEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class HandlerCallback<T> : Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            if (response.body() == null) {
                val errorEntity = ErrorEntity(ErrorEntity.ERROR_CODE_NO_CONTENT)
                onError(errorEntity)
            } else if (response.body()!=null) {
                onComplite(response.body()!!)
            }
        } else {
            val errorEntity: ErrorEntity
            if (response.code() == 404) {
                errorEntity = ErrorEntity(ErrorEntity.ERROR_CODE_NOT_FOUND)
            } else if (response.code() == 400) {
                errorEntity = ErrorEntity(ErrorEntity.ERROR_CODE_BAD_REQUEST)
            } else if (response.code() == 405) {
                errorEntity = ErrorEntity(ErrorEntity.ERROR_CODE_METHOD_NOT_ALLOWED)
            } else if (response.code() == 401) {
                errorEntity = ErrorEntity(ErrorEntity.ERROR_AUTH_NEEDED)
            } else if (response.code() >= 500) {
                errorEntity = ErrorEntity(ErrorEntity.ERROR_CODE_SERVER_EXCEPTION)
            } else if (response.code() >= 422) {
                errorEntity = ErrorEntity()
                try {
                    errorEntity.errorDesc = response.errorBody()!!.string()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            } else {
                errorEntity = ErrorEntity(ErrorEntity.ERROR_UNKNOUN_EXEPTION)
            }
            onError(errorEntity)
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        val errorEntity: ErrorEntity
        if (t is SocketTimeoutException) {
            errorEntity = ErrorEntity(ErrorEntity.ERROR_CODE_TIMEOUT)
        } else if (t is UnsupportedOperationException && t.message == "JsonNull") {
            errorEntity = ErrorEntity(ErrorEntity.ERROR_CODE_NO_CONTENT)
        } else if (t is UnknownHostException) {
            errorEntity = ErrorEntity(ErrorEntity.ERROR_NO_INTERNET)
        } else {
            errorEntity = ErrorEntity(ErrorEntity.ERROR_UNKNOUN_EXEPTION)
            t.printStackTrace()
        }

        onError(errorEntity)
    }

    abstract fun onError(error: ErrorEntity)
    abstract fun onComplite(result: T)
}
