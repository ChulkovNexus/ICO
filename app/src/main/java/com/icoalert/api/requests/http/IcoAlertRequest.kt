/*
 * Copyright © ExpertOption Ltd. All rights reserved.
 */

package com.icoalert.api.requests.http

import com.icoalert.api.data.IcoDto
import retrofit2.Call
import retrofit2.http.GET
import java.util.*

interface IcoAlertRequest {

    @get:GET("icos")
    val icos: Call<ArrayList<IcoDto>>

    //    @GET("platform/faq")
    //    Call<FaqData.FaqDataList> getFaq(@Query("lang") String language, @Query("json") int json);
    //
    //    @GET("d/project/7/api/?")
    //    Call<ContactsData.ContactsDataList> getContacts(@Query("lang") String language);
    //
    //    @GET("counters/online.json")
    //    Call<OnlineData> getOnline();
    //
    //    @Multipart
    //    @POST("web/verification/upload/")
    //    Call<String> uploadDocuments(@Part("upload_id_file\"; filename=\"248194725.jpeg") RequestBody file, @Part("token") RequestBody token, @Part("document_id") RequestBody documentId);
    //
    //    @GET("ping/")
    //    Call<PingData> ping();
    //
    //    @GET("pong/")
    //    Call<String> pong(@Query("pong") int pong);
    //
    //    @GET("android/version.json")
    //    Call<VersionData> version();
}
