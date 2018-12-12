package com.example.sithum.sampleapplication.view.authorization

import com.example.sithum.sampleapplication.BasePresenter
import com.example.sithum.sampleapplication.BaseView

interface AuthorizationContract {

    interface View:BaseView<Presenter>{

    }


    interface Presenter:BasePresenter{
        fun getTokenFromUrl()
    }
}