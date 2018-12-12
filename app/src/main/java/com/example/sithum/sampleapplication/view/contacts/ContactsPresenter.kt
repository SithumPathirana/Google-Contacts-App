package com.example.sithum.sampleapplication.view.contacts

import android.content.Context
import android.util.Log
import com.example.sithum.sampleapplication.MyApplication
import com.example.sithum.sampleapplication.api.RetrofitBuilder
import com.example.sithum.sampleapplication.models.Responce
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactsPresenter(private val view:ContactsContract.View):ContactsContract.Presenter{
    private val TAG = "ContactsPresenter"

    init {
        this.view.setPresenter(this)
    }

    override fun start() {

    }

    override fun getContacts(ctx:Context) {
        Log.e(TAG,"Getting the contacts from server ")
        val server = RetrofitBuilder.getAuthClient(ctx)
        val contactList: Call<Responce> = server.getContacts("2011-03-16T00:00:00",100)

        contactList.enqueue(object: Callback<Responce> {

            override fun onResponse(call: Call<Responce>, response: Response<Responce>) {
                view.showContactList(response.body()?.contacts)
            }

            override fun onFailure(call: Call<Responce>, t: Throwable) {
                Log.e(TAG,"Error while fetching the contacts")
                view.showContactsFerchError()
            }
        })
    }

    override fun signOut() {
        FirebaseAuth.getInstance().signOut()
        val sp = MyApplication.instance?.getSharedPreferences(com.example.sithum.sampleapplication.models.OAUTH_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        sp?.edit()?.remove(com.example.sithum.sampleapplication.models.SP_TOKEN_KEY)?.apply()
        sp?.edit()?.remove(com.example.sithum.sampleapplication.models.SP_REFRESH_TOKEN_KEY)?.apply()
        sp?.edit()?.remove(com.example.sithum.sampleapplication.models.SP_TOKEN_TYPE_KEY)?.apply()
        sp?.edit()?.remove(com.example.sithum.sampleapplication.models.SP_TOKEN_EXPIRED_AFTER_KEY)?.apply()

        if(FirebaseAuth.getInstance().currentUser == null){
             view.goBackToLoginActivity(true)
        }
    }




}