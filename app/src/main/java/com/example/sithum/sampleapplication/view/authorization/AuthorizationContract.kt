package com.example.sithum.sampleapplication.view.authorization

import android.content.Context
import com.example.sithum.sampleapplication.BasePresenter
import com.example.sithum.sampleapplication.BaseView

interface AuthorizationContract {

    interface View:BaseView<Presenter>{
       fun setContactsActivity(newTask:Boolean)
    }


    interface Presenter:BasePresenter{
        fun getTokenFromUrl(code:String?,ctx: Context)
    }
}