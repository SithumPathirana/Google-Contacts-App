package com.example.sithum.sampleapplication.view.login


import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


interface LoginContract {


    interface View{
       fun  updateUI(currentUser: FirebaseUser?)
    }

    interface Presenter{
       fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?, auth: FirebaseAuth, activity:MainActivity)

    }

}