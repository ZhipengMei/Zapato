package com.zapato.zapato

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zapato.zapato.User;

class MainActivity : AppCompatActivity() {

    // Firebase Auth Object.
    lateinit var firebaseAuth: FirebaseAuth

    // Google API Client object.
    lateinit var googleApiClient: GoogleApiClient

    // Sing out button.
    private lateinit var SignOutButton: Button

    // Google Sign In button .
    private lateinit var signInButton: com.google.android.gms.common.SignInButton

    // TextView to Show Login User Email and Name.
    private lateinit var LoginUserName: TextView
    private lateinit var LoginUserEmail: TextView

    // Write a message to the database
    var database = FirebaseDatabase.getInstance()//.setPersistenceEnabled(true)
    var my_users_Ref = database.getReference("users")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        signInButton = findViewById<View>(R.id.sign_in_button) as SignInButton

        SignOutButton = findViewById<View>(R.id.sign_out) as Button

        LoginUserName = findViewById<View>(R.id.textViewName) as TextView

        LoginUserEmail = findViewById<View>(R.id.textViewEmail) as TextView

        signInButton = findViewById<View>(R.id.sign_in_button) as com.google.android.gms.common.SignInButton

        // Getting Firebase Auth Instance into firebaseAuth object.
        firebaseAuth = FirebaseAuth.getInstance()

        // Hiding the TextView on activity start up time.
        LoginUserEmail.visibility = View.GONE
        LoginUserName.visibility = View.GONE

        // Creating and Configuring Google Sign In object.
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        // Creating and Configuring Google Api Client.
        googleApiClient = GoogleApiClient.Builder(this@MainActivity)
                .enableAutoManage(this@MainActivity  /* OnConnectionFailedListener */) { }
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build()


        // Adding Click listener to User Sign in Google button.
        signInButton.setOnClickListener { UserSignInMethod() }

        // Adding Click Listener to User Sign Out button.
        SignOutButton.setOnClickListener { UserSignOutFunction() }

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

            if (googleSignInResult.isSuccess) {

                val googleSignInAccount = googleSignInResult.signInAccount

                FirebaseUserAuth(googleSignInAccount)

            }

        }
    }

    fun FirebaseUserAuth(googleSignInAccount: GoogleSignInAccount?) {

        val authCredential = GoogleAuthProvider.getCredential(googleSignInAccount!!.idToken, null)

        Toast.makeText(this@MainActivity, "" + authCredential.provider, Toast.LENGTH_LONG).show()

        firebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(this@MainActivity) { AuthResultTask ->
                    if (AuthResultTask.isSuccessful) {

                        // Getting Current Login user details.
                        val firebaseUser = firebaseAuth.currentUser

                        // Showing Log out button.
                        SignOutButton.visibility = View.VISIBLE

                        // Hiding Login in button.
                        signInButton.visibility = View.GONE

                        // Showing the TextView.
                        LoginUserEmail.visibility = View.VISIBLE
                        LoginUserName.visibility = View.VISIBLE

                        // Setting up name into TextView.
                        LoginUserName.text = "NAME = " + firebaseUser!!.displayName!!.toString()

                        // Setting up Email into TextView.
                        LoginUserEmail.text = "Email = " + firebaseUser.email!!.toString()

                        // saving new user's data to firebase database
                        writeNewUser(LoginUserName.text.toString(), LoginUserEmail.text.toString());

                    } else {
                        Toast.makeText(this@MainActivity, "Something Went Wrong", Toast.LENGTH_LONG).show()
                    }
                }
    }



    fun UserSignOutFunction() {

        // Sing Out the User.
        firebaseAuth.signOut()

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback {
            // Write down your any code here which you want to execute After Sign Out.

            // Printing Logout toast message on screen.
            Toast.makeText(this@MainActivity, "Logout Successfully", Toast.LENGTH_LONG).show()
        }

        // After logout Hiding sign out button.
        SignOutButton.visibility = View.GONE

        // After logout setting up email and name to null.
        LoginUserName.text = null
        LoginUserEmail.text = null

        // After logout setting up login button visibility to visible.
        signInButton.visibility = View.VISIBLE
    }

    companion object {

        // TAG is for show some tag logs in LOG screen.
        val TAG = "MainActivity"

        // Request sing in code. Could be anything as you required.
        val RequestSignInCode = 7
    }

    fun writeNewUser(username: String, email: String) {
        val user = User(username, email)
        val userId = firebaseAuth!!.currentUser!!.uid
        my_users_Ref!!.child(userId).setValue(user)
    }


}

