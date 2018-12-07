package com.example.sithum.sampleapplication.retrofit.interceptors

import android.content.Intent
import android.text.TextUtils
import android.util.Log
import com.example.sithum.sampleapplication.view.Home
import com.example.sithum.sampleapplication.MyApplication
import com.example.sithum.sampleapplication.retrofit.OuthToken
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class OuthIntercepter:Interceptor{

    private val TAG = "OAuthInterceptor"
    private var accessToken: String? = null
    private var accessTokenType:String? = null
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        //find the token
        val oauthToken = OuthToken.Factory.create()
        accessToken = oauthToken?.accessToken
        accessTokenType = oauthToken?.tokenType
        //add it to the request
        val builder = chain.request().newBuilder()
        if (!TextUtils.isEmpty(accessToken) && !TextUtils.isEmpty(accessTokenType)) {
            Log.e(TAG, "In the interceptor adding the header authorization with : $accessTokenType $accessToken")
            builder.header("Authorization", accessTokenType + " " + accessToken)
        } else {
            Log.e(TAG, "In the interceptor there is a problem with : $accessTokenType $accessToken")
            //you should launch the loginActivity to fix that:
            val i = Intent(MyApplication.instance, Home::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            MyApplication.instance?.startActivity(i)
        }
        //proceed to the call
        return chain.proceed(builder.build())
    }

}