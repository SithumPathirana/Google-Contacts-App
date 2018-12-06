package com.example.sithum.sampleapplication.retrofit


import android.content.Context
import android.util.Log
import com.squareup.moshi.Json


class OuthToken{
    private val TAG = "OAuthToken"

    @Json(name = "access_token")
    private val accessToken: String? = null
    @Json(name = "token_type")
    private val tokenType: String? = null
    @Json(name = "expires_in")
    private val expiresIn: Long = 0
    private var expiredAfterMilli: Long = 0
    @Json(name = "refresh_token")
    private val refreshToken: String? = null

//    fun save() {
//        Log.e(TAG, "Savng the following element " + this)
//        //update expired_after
//        expiredAfterMilli = System.currentTimeMillis() + expiresIn * 1000
//        Log.e(
//            TAG,
//            "Savng the following element and expiredAfterMilli =" + expiredAfterMilli + " where now=" + System.currentTimeMillis() + " and expired in =" + expiresIn
//        )
//        val sp = MyApplication.instance.getSharedPreferences(OAUTH_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
//        val ed = sp.edit()
//        ed.putString(SP_TOKEN_KEY, accessToken)
//        ed.putString(SP_TOKEN_TYPE_KEY, tokenType)
//        ed.putLong(SP_TOKEN_EXPIRED_AFTER_KEY, expiredAfterMilli)
//        ed.putString(SP_REFRESH_TOKEN_KEY, refreshToken)
//        ed.commit()
//    }
}