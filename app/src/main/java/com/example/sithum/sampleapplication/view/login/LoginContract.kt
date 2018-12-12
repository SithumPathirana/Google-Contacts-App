package com.example.sithum.sampleapplication.view.login

import android.content.Context
import android.content.Intent
import com.example.sithum.sampleapplication.BasePresenter
import com.example.sithum.sampleapplication.BaseView
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


interface LoginContract {


    interface View:BaseView<Presenter>{
       fun  updateUI(currentUser: FirebaseUser?)
    }

    interface Presenter:BasePresenter{
       fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?, auth: FirebaseAuth, activity:MainActivity)
    }

}