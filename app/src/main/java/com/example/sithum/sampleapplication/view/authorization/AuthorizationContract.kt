package com.example.sithum.sampleapplication.view.authorization

import android.content.Context
import com.example.sithum.sampleapplication.BasePresenter
import com.example.sithum.sampleapplication.BaseView

interface AuthorizationContract {

    interface View{
       fun setContactsActivity(newTask:Boolean)
        fun launchConsentScreen()
    }


    interface Presenter{
        fun getTokenFromUrl(code:String?,ctx: Context)
        fun setAuthUrl()
    }
}