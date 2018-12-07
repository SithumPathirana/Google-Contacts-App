package com.example.sithum.sampleapplication.retrofit

import android.content.Context
import com.example.sithum.sampleapplication.retrofit.converters.StringConverterFactory
import com.example.sithum.sampleapplication.retrofit.interceptors.OuthIntercepter
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object RetrofitBuilder{

    val BASE_URL = "https://www.googleapis.com/"

    val CONTACTS_URL = "https://www.google.com/m8/feeds/contacts/default/"

    fun getSimpleClient(ctx:Context) :OAuthServerIntf{

         val retrofit = Retrofit.Builder()
             .client(getSimpleOkHttpClient(ctx))
             .addConverterFactory(StringConverterFactory())
             .addConverterFactory(MoshiConverterFactory.create())
             .baseUrl(BASE_URL)
             .build()
         return retrofit.create(OAuthServerIntf::class.java)
     }


     fun getSimpleOkHttpClient(ctx:Context):OkHttpClient{
        val myCacheDir = File(ctx.cacheDir, "OkHttpCache")
        val cacheSize = 1024 * 1024
        val cacheDir = Cache(myCacheDir, cacheSize.toLong())
        val httpLogInterceptor = HttpLoggingInterceptor()
        httpLogInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            //add a cache
            .cache(cacheDir)
            .addInterceptor(httpLogInterceptor)
            .build()
    }

    fun getAuthClient(ctx:Context):OAuthServerIntf{
      val  raCustom = Retrofit.Builder ()
          .client(getOAuthOkHttpClient(ctx))
          .baseUrl(CONTACTS_URL)
          .addConverterFactory(SimpleXmlConverterFactory.create())
          .build()

        val webServer = raCustom.create(OAuthServerIntf::class.java)
        return webServer
    }

    fun getOAuthOkHttpClient(ctx:Context):OkHttpClient{
        val myCacheDir = File(ctx.cacheDir, "OkHttpCache")
        val cacheSize = 1024 * 1024
        val cacheDir = Cache(myCacheDir, cacheSize.toLong())
        val oAuthInterceptor = OuthIntercepter()
        val httpLogInterceptor = HttpLoggingInterceptor()
        httpLogInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .cache(cacheDir)
            .addInterceptor(oAuthInterceptor)
            .addInterceptor(httpLogInterceptor)
            .build()
    }
}