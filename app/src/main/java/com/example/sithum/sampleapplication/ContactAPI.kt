package com.example.sithum.sampleapplication


import io.reactivex.Observable
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


const val token:String="ya29.GltpBsDVJ5f9BnXLVlhCWq5kVtiML1xJj7_qN-Vs2HF-zbLs45OjC90-DQoPi7kWtZE0_0U4PRMCq08LQjjsTdIUOu-f-G-ZCaFKdmCnHJzjI7pS9PZg5uS5dR70"
//
//object Model{
//   data class Result(val feed: Feed)
//   data class Feed(val entry: ArrayList<Entry>)
//   data class Entry(val name:String, val number:String,@DrawableRes val resId:Int)
//}
//
//interface GoogleContactsApiService{
//
//   @Headers(
//      "GData-Version: 3.0",
//      "Authorization:Bearer $token"
//   )
//   @GET("full")
//   fun getContacts():Observable<Model.Result>
//
//   companion object {
//       fun create():GoogleContactsApiService{
//          val retrofit=Retrofit.Builder()
//             .addCallAdapterFactory(
//                RxJava2CallAdapterFactory.create())
//             .addConverterFactory(
//                GsonConverterFactory.create())
//             .baseUrl("https://www.google.com/m8/feeds/contacts/default/")
//             .build()
//
//          return retrofit.create(GoogleContactsApiService::class.java)
//       }
//   }
//}

 interface GoogleContactsAPI {


     @Headers(
         "GData-Version: 3.0",
          "Authorization:Bearer $token"
     )
    @GET("full")
    fun getContacts(@Query("updated-min") updated_min: String,
                    @Query("max-results") max_results: Int):Observable<Responce>

    companion object {
        fun create():GoogleContactsAPI{
            val retrofit=Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    SimpleXmlConverterFactory.createNonStrict(
                        Persister(AnnotationStrategy())
                    ))
                .baseUrl("https://www.google.com/m8/feeds/contacts/default/")
                .build()
            return retrofit.create(GoogleContactsAPI::class.java)

        }
    }

}