package com.example.sithum.sampleapplication.api

import com.example.sithum.sampleapplication.models.OuthToken
import com.example.sithum.sampleapplication.models.Responce
import retrofit2.Call
import retrofit2.http.*

interface OAuthServerIntf {

    @FormUrlEncoded
    @POST("oauth2/v4/token")
    fun requestTokenForm(@Field("code") code:String?,
                         @Field("client_id")client_id:String,
                         @Field("redirect_uri")redirect_uri:String,
                         @Field("grant_type")grant_type:String): Call<OuthToken>

    @FormUrlEncoded
    @POST("oauth2/v4/token")
    fun refreshTokenForm(
        @Field("refresh_token") refresh_token: String?,
        @Field("client_id") client_id: String,
        @Field("grant_type") grant_type: String
    ): Call<OuthToken>


    @GET("contacts/default/full")
    fun getContacts(@Query("updated-min") updated_min: String,
                    @Query("max-results") max_results: Int): Call<Responce>
}