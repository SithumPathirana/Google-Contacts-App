package com.example.sithum.sampleapplication.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.sithum.sampleapplication.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.GoogleAuthProvider


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var signInButton: SignInButton
    private lateinit var mGoogleSignInClient:GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        println("Client Id : "+ R.string.default_web_client_id)

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        signInButton=findViewById(R.id.sign_in_button)
        signInButton.setOnClickListener {
            when (it.id) {
                R.id.sign_in_button -> signIn()
            }// ...

        }
        auth = FirebaseAuth.getInstance()
    }


    override fun onStart() {
        super.onStart()
        val currentUser = auth.getCurrentUser()
         val a= currentUser?.getIdToken(true)
        updateUI(currentUser)
    }

    private fun updateUI(currentUser:FirebaseUser?){
       if (currentUser != null){
           val intent= Intent(this, Home::class.java)
           startActivity(intent)
       } else{
           println("User has not logged in")
       }
    }


    private fun signIn() {
        mGoogleSignInClient.signOut()
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent,100)

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 100) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("", "Google sign in failed", e)
                // ...
            }

        }
    }



    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?) {
        Log.d("", "firebaseAuthWithGoogle:" + acct?.id!!)

        val credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("", "signInWithCredential:success")
                    val user = auth.currentUser
                    println(user?.email)
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("", "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }

                // ...
            }
    }
}
