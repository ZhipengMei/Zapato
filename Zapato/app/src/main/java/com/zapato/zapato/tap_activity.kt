package com.zapato.zapato

/**
 * Created by adrian on 2/12/18.
 */

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_tab.*

class tap_activity : AppCompatActivity() {

    // Firebase Auth Object.
    lateinit var firebaseAuth: FirebaseAuth

    // Google API Client object.
    lateinit var googleApiClient: GoogleApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)

        // setup tap host
        tab_host.setup()

        // setup tabs
        var spec = tab_host.newTabSpec("Tab One")
        spec.setContent(R.id.tab_one)
        spec.setIndicator("Tab One")
        tab_host.addTab(spec)

        spec = tab_host.newTabSpec("Tab Two")
        spec.setContent(R.id.tab_two)
        spec.setIndicator("Tab Two")
        tab_host.addTab(spec)

        spec = tab_host.newTabSpec("Tab Three")
        spec.setContent(R.id.tab_three)
        spec.setIndicator("Tab Three")
        tab_host.addTab(spec)



        // Getting Firebase Auth Instance into firebaseAuth object.
        firebaseAuth = FirebaseAuth.getInstance()

        // Getting Current Login user details.
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            username_textview.text = firebaseUser.email!!.toString()
        }


        // Creating and Configuring Google Sign In object.
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        // Creating and Configuring Google Api Client.
        googleApiClient = GoogleApiClient.Builder(this@tap_activity)
                .enableAutoManage(this@tap_activity  /* OnConnectionFailedListener */) { }
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build()

        logout_button.setOnClickListener { view ->
            Log.d("omg", "here1")
            UserSignOutFunction()
            Log.d("omg", "here2")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    fun UserSignOutFunction() {
        Log.d("omg", "here3")
        // Sing Out the User.
        firebaseAuth.signOut()
        Log.d("omg", "here4")
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback {
            // Write down your any code here which you want to execute After Sign Out.
            Log.d("omg", "here5")
            // Printing Logout toast message on screen.
            Toast.makeText(this@tap_activity, "Logout Successfully", Toast.LENGTH_LONG).show()
        }

    }


}