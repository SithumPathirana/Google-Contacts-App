package com.example.sithum.sampleapplication.api

import android.content.Context
import com.example.sithum.sampleapplication.Constants
import com.example.sithum.sampleapplication.api.interceptors.OuthIntercepter
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.io.File
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object RetrofitBuilder{

    fun getSimpleClient(ctx:Context) :ApiServerIntf{

         val retrofit = Retrofit.Builder()
             .client(getSimpleOkHttpClient(ctx))
             .addConverterFactory(GsonConverterFactory.create())
             .baseUrl(Constants.BASE_URL)
             .build()
         return retrofit.create(ApiServerIntf::class.java)
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

    fun getAuthClient(ctx:Context):ApiServerIntf{
      val  raCustom = Retrofit.Builder ()
          .client(getOAuthOkHttpClient(ctx))
          .baseUrl(Constants.CONTACTS_URL)
          .addConverterFactory(SimpleXmlConverterFactory.create())
          .build()

        val webServer = raCustom.create(ApiServerIntf::class.java)
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