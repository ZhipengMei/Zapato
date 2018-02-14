package com.zapato.zapato

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_tab.*
import android.preference.PreferenceManager
import android.content.SharedPreferences
import android.util.Log


/**
 * Created by adrian on 2/12/18.
 */
class loading_activity: AppCompatActivity() {

    // Firebase Auth Object.
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        oneTimeCode()

    }

    // this method only run once when the app first launch to prompt login or use as guest mode
    fun oneTimeCode() {
        // Getting Firebase Auth Instance into firebaseAuth object.
        firebaseAuth = FirebaseAuth.getInstance()


        // PreferenceManager takes care of saving state to the device
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        if (!prefs.getBoolean("firstTime", false)) {
            // run your "one time" code here
            Log.d("firstLaunch", "firstLaunch")

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

            // mark first time has runned.
            val editor = prefs.edit()
            editor.putBoolean("firstTime", true)
            editor.commit()
        } else {
            Log.d("not firstLaunch", "not firstLaunch")
            // segue to tab_activity
            val intent = Intent(this, tap_activity::class.java)
            startActivity(intent)
        }
    }

}