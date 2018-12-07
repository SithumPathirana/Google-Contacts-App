package com.example.sithum.sampleapplication.retrofit


import android.content.Context
import android.util.Log
import com.example.sithum.sampleapplication.MyApplication
import com.squareup.moshi.Json


private val OAUTH_SHARED_PREFERENCE_NAME = "OAuthPrefs"
private val SP_TOKEN_KEY = "token"
private val SP_TOKEN_TYPE_KEY = "token_type"
private val SP_TOKEN_EXPIRED_AFTER_KEY = "expired_after"
private val SP_REFRESH_TOKEN_KEY = "refresh_token"

class OuthToken{
    private val TAG = "OAuthToken"



    @Json(name = "access_token")
    var accessToken: String? = null
    @Json(name = "token_type")
    var tokenType: String? = null
    @Json(name = "expires_in")
    var expiresIn: Long = 0
     var expiredAfterMilli: Long = 0
    @Json(name = "refresh_token")
     var refreshToken: String? = null

    fun save() {
        Log.e(TAG, "Savng the following element " + this)
        //update expired_after
        expiredAfterMilli = System.currentTimeMillis() + expiresIn * 1000
        Log.e(
            TAG,
            "Savng the following element and expiredAfterMilli =" + expiredAfterMilli + " where now=" + System.currentTimeMillis() + " and expired in =" + expiresIn
        )
        val sp = MyApplication.instance?.getSharedPreferences(OAUTH_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val ed = sp?.edit()
        ed?.putString(SP_TOKEN_KEY, accessToken)
        ed?.putString(SP_TOKEN_TYPE_KEY, tokenType)
        ed?.putLong(SP_TOKEN_EXPIRED_AFTER_KEY, expiredAfterMilli)
        ed?.putString(SP_REFRESH_TOKEN_KEY, refreshToken)
        ed?.commit()
    }

     object Factory {

         fun create():OuthToken?{
             val TAG = "OAuthToken.Factory"
             var expiredAfter: Long = 0
             val sp = MyApplication.instance?.getSharedPreferences(OAUTH_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
             if (sp!!.contains(SP_TOKEN_EXPIRED_AFTER_KEY)) {
                 Log.e(TAG, "sp.contains(SP_TOKEN_EXPIRED_AFTER)")
                 expiredAfter = sp.getLong(SP_TOKEN_EXPIRED_AFTER_KEY, 0)
                 val now = System.currentTimeMillis()

                 Log.e(TAG, "Delta : " + (now - expiredAfter))
                 if (expiredAfter == 0L || now > expiredAfter) {
                     Log.e(TAG, "expiredAfter==0||now>expiredAfter, token has expired")
                     //flush token in the SP
                     val ed = sp?.edit()
                     ed?.putString(SP_TOKEN_KEY, null)
                     ed?.commit()
                     //rebuild the object according to the SP
                     val oauthToken = OuthToken()
                     oauthToken.accessToken=null
                     oauthToken.tokenType= sp?.getString(SP_TOKEN_TYPE_KEY, null)
                     oauthToken.refreshToken= sp?.getString(SP_REFRESH_TOKEN_KEY, null)
                     oauthToken.expiredAfterMilli=sp.getLong(SP_TOKEN_EXPIRED_AFTER_KEY, 0)
                     return oauthToken
                 } else {

                     Log.e(TAG, "NOT (expiredAfter==0||now<expiredAfter) current case, token is valid")
                     //rebuild the object according to the SP
                     val oauthToken = OuthToken()
                     oauthToken.accessToken=sp.getString(SP_TOKEN_KEY, null)
                     oauthToken.tokenType=sp.getString(SP_TOKEN_TYPE_KEY, null)
                     oauthToken.refreshToken=sp.getString(SP_REFRESH_TOKEN_KEY, null)
                     oauthToken.expiredAfterMilli=sp.getLong(SP_TOKEN_EXPIRED_AFTER_KEY, 0)
                     return oauthToken
                 }
             } else {
                 return null
             }

         }
     }
}