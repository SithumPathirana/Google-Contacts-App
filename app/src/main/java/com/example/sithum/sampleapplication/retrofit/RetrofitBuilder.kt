package com.example.sithum.sampleapplication.retrofit

import android.content.Context
import com.example.sithum.sampleapplication.retrofit.converters.StringConverterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import okhttp3.logging.HttpLoggingInterceptor

object RetrofitBuilder{

    val BASE_URL = "https://www.googleapis.com/"

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
}