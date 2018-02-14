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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_tab.*

class tap_activity : AppCompatActivity() {

    // Firebase Auth Object.
    lateinit var firebaseAuth: FirebaseAuth

    // Google API Client object.
    lateinit var googleApiClient: GoogleApiClient

    // reference to databse
    var ref = FirebaseDatabase.getInstance().getReference("sample_data")

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


        // Prepare user's Google credential for later log out
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


        // Getting Firebase Auth Instance into firebaseAuth object.
        firebaseAuth = FirebaseAuth.getInstance()

        if (firebaseAuth.currentUser != null) {
            username_textview.text = firebaseAuth.currentUser!!.email!!.toString()
        }

        isUserSignIn() // default check
        logout_button.setOnClickListener { //view ->
            if (logout_button.text == "Logout") {
                UserSignOutFunction()
                isUserSignIn() // check again after logout
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

        add_button.setOnClickListener { loadDatabase(ref) }


    } // \onCreate


    fun loadDatabase(firebaseData: DatabaseReference) {
        val key = firebaseData.push().key
        Log.d("testing", key)
        firebaseData.child(key).setValue("testing data")
    }

//    fun add_button_action() {
//        Log.d("testing", "add btn clicked")
//
//        val key = ref.child("sampleData").push().key
//        it.uuid = key
//        firebaseData.child("salads").child(key).setValue(it)
//
//        ref!!.child("sampleData").setValue("hi")
//
//    }

    // sign user out of google and firebase
    fun UserSignOutFunction() {

        // Sing Out the User.
        firebaseAuth.signOut()

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback {
            // Write down your any code here which you want to execute After Sign Out.
            // Printing Logout toast message on screen.
            Toast.makeText(this@tap_activity, "Logout Successfully", Toast.LENGTH_LONG).show()
        }
    }

    // user persist state check
    fun isUserSignIn() {
        //get current signed in user
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            // user object return, hide display label
            username_textview.visibility = View.VISIBLE
            logout_button.text = "Logout"
        } else {
            // no user object return, show display label
            username_textview.visibility = View.GONE
            logout_button.text = "Sign In"
        }
    }




}