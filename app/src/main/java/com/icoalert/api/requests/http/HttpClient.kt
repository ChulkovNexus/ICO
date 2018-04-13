/*
 * Copyright © ExpertOption Ltd. All rights reserved.
 */

package com.icoalert.api.requests.http

import android.content.Context
import com.icoalert.api.data.IcoDto
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit


class HttpClient {

    private val API_URL = "https://expertoption.com"

    private val retrofit: IcoAlertRequest
        get() {
            val interceptor = HttpLoggingInterceptor()
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val retrofit = Retrofit.Builder()
                    .baseUrl(API_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit.create(IcoAlertRequest::class.java)
        }

    fun getIcos(context: Context, searchRequest: String, callback: HandlerCallback<IcoDto.IcosList>) {
//        val service = retrofit
//        val call = service.icos
//        call.enqueue(callback)

        val REVIEW_TYPE = object : TypeToken<IcoDto.IcosList>() {}.type
        val gson = GsonBuilder().setLenient().create()
        val reader = JsonReader(InputStreamReader(context.assets.open("testJson.json"), "UTF-8"))
        val data : IcoDto.IcosList = gson.fromJson(reader, REVIEW_TYPE)
        Observable.timer(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe {
            callback.onComplite(data)
        }
    }

    fun getIcosWithOffset(context: Context, searchRequest: String, offset: Int, limit: Int, callback: HandlerCallback<IcoDto.IcosListByStatus>) {
//        val service = retrofit
//        val call = service.icos
//        call.enqueue(callback)
        val REVIEW_TYPE = object : TypeToken<IcoDto.IcosListByStatus>() {}.type
        val gson = GsonBuilder().setLenient().create()
        val reader = JsonReader(InputStreamReader(context.assets.open("testJsonWithOffset.json"), "UTF-8"))
        val data : IcoDto.IcosListByStatus = gson.fromJson(reader, REVIEW_TYPE)
        Observable.timer(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe {
            callback.onComplite(data)
        }
    }
}
