package com.example.sithum.sampleapplication.view.login

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class LoginPresenter(private val view:LoginContract.View):LoginContract.Presenter {

    init {
        this.view.setPresenter(this)


    }

    override fun start() {
          
    }

    override fun signIn(gso: GoogleSignInOptions, mGoogleSignInClient: GoogleSignInClient) {

    }
}