package com.example.sithum.sampleapplication.view.authorization

import android.content.Context
import android.util.Log
import com.example.sithum.sampleapplication.Constants
import com.example.sithum.sampleapplication.api.RetrofitBuilder
import com.example.sithum.sampleapplication.models.OuthToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthorizationPresenter(private val view:AuthorizationContract.View ):AuthorizationContract.Presenter{
     private val TAG="AuthorizationPresenter"

    override fun start() {

    }

    override fun getTokenFromUrl(code:String?,ctx:Context) {
        val oAuthServer = RetrofitBuilder.getSimpleClient(ctx)
        val getRequestTokenFormCall = oAuthServer.requestTokenForm(
            code,
            Constants.CLIENT_ID,
            Constants. REDIRECT_URI,
            Constants. GRANT_TYPE_AUTHORIZATION_CODE
        )

        getRequestTokenFormCall.enqueue(object : Callback<OuthToken> {
            override fun onResponse(call: Call<OuthToken>, response: Response<OuthToken>) {
                Log.e(TAG, "===============New Call==========================")
                Log.e(
                    TAG,
                    "The call getRequestTokenFormCall succeed with code=" + response.code() + " and has body = " + response.body()
                )
//                ok we have the token
                response.body()?.save()
                view.setContactsActivity(true)

            }

            override fun onFailure(call: Call<OuthToken>, t: Throwable) {
                println(""+t.localizedMessage)
            }

        })
    }

}