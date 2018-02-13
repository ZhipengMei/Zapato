package com.zapato.zapato

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_tab.*

/**
 * Created by adrian on 2/12/18.
 */
class loading_activity: AppCompatActivity() {

    // Firebase Auth Object.
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        // Getting Firebase Auth Instance into firebaseAuth object.
        firebaseAuth = FirebaseAuth.getInstance()

        // Getting Current Login user details.
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            // segue to tab_activity
            val intent = Intent(this, tap_activity::class.java)
            startActivity(intent)
        } else {
            // segue to main_activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}