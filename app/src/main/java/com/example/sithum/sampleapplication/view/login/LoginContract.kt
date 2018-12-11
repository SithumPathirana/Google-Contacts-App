package com.example.sithum.sampleapplication.view.login

import com.example.sithum.sampleapplication.BasePresenter
import com.example.sithum.sampleapplication.BaseView
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


interface LoginContract {


    interface View:BaseView<Presenter>{

    }

    interface Presenter:BasePresenter{
        fun signIn(gso:GoogleSignInOptions,mGoogleSignInClient: GoogleSignInClient){
            mGoogleSignInClient.signOut()
            val signInIntent = mGoogleSignInClient.signInIntent


        }
    }

}