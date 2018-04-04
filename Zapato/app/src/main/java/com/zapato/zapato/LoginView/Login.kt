package com.zapato.zapato.LoginView

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.*
import com.zapato.zapato.HomeView.Home
import com.zapato.zapato.Model.User
import com.zapato.zapato.Network.FirebaseManager
import com.zapato.zapato.R

/**
 * Created by adrian on 4/2/18.
 */

class Login: AppCompatActivity() {

    // Firebase Auth Object.
    lateinit var firebaseAuth: FirebaseAuth

    // Google API Client object.
    lateinit var googleApiClient: GoogleApiClient

    // Google Sign In button .
    private lateinit var signInButton: com.google.android.gms.common.SignInButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signInButton = findViewById<View>(R.id.sign_in_button) as com.google.android.gms.common.SignInButton

        // Getting Firebase Auth Instance into firebaseAuth object.
        firebaseAuth = FirebaseAuth.getInstance()

        createGoogleSignInOption()

        // Adding Click listener to User Sign in Google button.
        signInButton.setOnClickListener { UserSignInMethod() }
    }

    fun createGoogleSignInOption() {
        // Creating and Configuring Google Sign In object.
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        // Creating and Configuring Google Api Client.
        googleApiClient = GoogleApiClient.Builder(this@Login)
                .enableAutoManage(this@Login  /* OnConnectionFailedListener */) { }
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build()
    }


    // Sign In function Starts From Here.
    fun UserSignInMethod() {

        // Passing Google Api Client into Intent.
        val AuthIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)

        startActivityForResult(AuthIntent, RequestSignInCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RequestSignInCode) {
            val googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            Log.d("myTag", "This is my message");

            if (googleSignInResult.isSuccess) {
                val googleSignInAccount = googleSignInResult.signInAccount
                FirebaseUserAuth(googleSignInAccount)
            } else {
                Log.d("myTag", "This is my message: googleSignInResult failed");
            }

        }
    }


    // sign user into Zapato's firebase with google's credential
    fun FirebaseUserAuth(googleSignInAccount: GoogleSignInAccount?) {

        val authCredential = GoogleAuthProvider.getCredential(googleSignInAccount!!.idToken, null)

        Toast.makeText(this@Login, "" + authCredential.provider, Toast.LENGTH_LONG).show()

        this.firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener { task: Task<AuthResult> ->
            if (task.isSuccessful) {
                //Registration OK

                // check if user profile data already in database by using reference with uid end point
                FirebaseManager().checkUserData(FirebaseManager().my_users_Ref.child(FirebaseManager().CurrenUser().toString()))

                // segue to tab_activity
                val intent = Intent(this, Home::class.java)
                startActivity(intent)

            } else {
                //Registration error
                Toast.makeText(this@Login, "Something Went Wrong", Toast.LENGTH_LONG).show()
            }
        }
    }


    // default method
    companion object {
        // TAG is for show some tag logs in LOG screen.
        val TAG = "Login"

        // Request sing in code. Could be anything as you required.
        val RequestSignInCode = 7
    }

}
