package com.zapato.zapato

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import com.google.firebase.database.*
import com.zapato.zapato.R.id.guest_button
import com.zapato.zapato.User;
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // Firebase Auth Object.
    lateinit var firebaseAuth: FirebaseAuth

    // Google API Client object.
    lateinit var googleApiClient: GoogleApiClient

    // Sing out button.
    private lateinit var SignOutButton: Button

    // Google Sign In button .
    private lateinit var signInButton: com.google.android.gms.common.SignInButton

    // Write a message to the database
    //var database = FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    var my_users_Ref = FirebaseDatabase.getInstance().getReference("users")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        signInButton = findViewById<View>(R.id.sign_in_button) as com.google.android.gms.common.SignInButton

        // Getting Firebase Auth Instance into firebaseAuth object.
        firebaseAuth = FirebaseAuth.getInstance()

        createGoogleSignInOption()

        // Adding Click listener to User Sign in Google button.
        signInButton.setOnClickListener { UserSignInMethod() }

        // Guest Mode button bypass login page
        guest_button.setOnClickListener { view ->
            val intent = Intent(this, tap_activity::class.java)
            startActivity(intent)
        }

    }

    fun createGoogleSignInOption() {
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


    // sign user into Zapato's firebase with google's credential
    fun FirebaseUserAuth(googleSignInAccount: GoogleSignInAccount?) {

        val authCredential = GoogleAuthProvider.getCredential(googleSignInAccount!!.idToken, null)

        Toast.makeText(this@MainActivity, "" + authCredential.provider, Toast.LENGTH_LONG).show()

        firebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(this@MainActivity) { AuthResultTask ->
                    if (AuthResultTask.isSuccessful) {

                        readData(my_users_Ref)

                        // segue to tab_activity
                        val intent = Intent(this, tap_activity::class.java)
                        startActivity(intent)

                    } else {
                        Toast.makeText(this@MainActivity, "Something Went Wrong", Toast.LENGTH_LONG).show()
                    }
                }
    } // \FirebaseUserAuth

    // default method
    companion object {
        // TAG is for show some tag logs in LOG screen.
        val TAG = "MainActivity"

        // Request sing in code. Could be anything as you required.
        val RequestSignInCode = 7
    }

    // find out if user existed already, true: do nothing, else: write new user data to the database
    fun readData(myRef: DatabaseReference) {
        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // check if this user exist or not
                if (dataSnapshot.exists()) {
                    Log.d("Firebase_Zapato_Tag", "User Found")
                } else {

                    // Getting Firebase Auth Instance into firebaseAuth object.
                    val firebaseUser = FirebaseAuth.getInstance().currentUser

                    // creating a user object
                    val user = User(firebaseUser!!.displayName!!.toString(), firebaseUser.email!!.toString(), firebaseUser.uid)

                    // saving new user's data to firebase database
                    writeNewUser(username = user.name!!, email = user.email!!, userId = user.uid!!)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("Firebase_Zapato_Tag", "Failed to read value.", error.toException())
            }
        })
    }

    // writing data to the database
    fun writeNewUser(username: String, email: String, userId: String) {
        val user = User(username, email)
        my_users_Ref!!.child(userId).setValue(user)
    }

}

