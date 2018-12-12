package com.example.sithum.sampleapplication.view.login


import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class LoginPresenter(private val view:LoginContract.View):LoginContract.Presenter {

    init {
        this.view.setPresenter(this)


    }

    override fun start() {

    }

    override fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?,auth:FirebaseAuth,activity:MainActivity) {
        Log.d("", "firebaseAuthWithGoogle:" + acct?.id!!)
        val credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("", "signInWithCredential:success")
                    val user = auth.currentUser
                    println(user?.email)
                    view.updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("", "signInWithCredential:failure", task.exception)
                    view.updateUI(null)
                }

                // ...
            }
    }


}