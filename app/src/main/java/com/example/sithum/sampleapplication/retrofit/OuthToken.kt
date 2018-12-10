package com.example.sithum.sampleapplication.retrofit


import android.content.Context
import android.util.Log
import com.example.sithum.sampleapplication.MyApplication
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json


public val OAUTH_SHARED_PREFERENCE_NAME = "OAuthPrefs"
public val SP_TOKEN_KEY = "token"
public val SP_TOKEN_TYPE_KEY = "token_type"
public val SP_TOKEN_EXPIRED_AFTER_KEY = "expired_after"
public val SP_REFRESH_TOKEN_KEY = "refresh_token"

 class OuthToken{
    private val TAG = "OAuthToken"



    @SerializedName("access_token")
    var accessToken: String? = null
    @SerializedName("token_type")
    var tokenType: String? = null
    @SerializedName("expires_in")
    var expiresIn: Long = 0
     @Json(name = "refresh_token")
     var refreshToken: String? = null

     var expiredAfterMilli: Long = 0

    fun save() {
        Log.e(TAG, "Savng the following element " + this)
        //update expired_after
        expiredAfterMilli = System.currentTimeMillis() + (expiresIn * 1000)
        Log.e(
            TAG,
            "Savng the following element and expiredAfterMilli =" + expiredAfterMilli + " where now=" + System.currentTimeMillis() + " and expired in =" + expiresIn
        )
        val sp = MyApplication.instance?.getSharedPreferences(OAUTH_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val ed = sp?.edit()
        Log.e(TAG, "The acess token is "+accessToken)
        Log.e(TAG, "The token type is "+tokenType)
        Log.e(TAG, "The expired after is "+expiresIn)
        Log.e(TAG, "The refresh token is "+refreshToken)

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