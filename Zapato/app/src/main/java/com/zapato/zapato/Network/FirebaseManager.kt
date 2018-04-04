package com.zapato.zapato.Network

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import com.zapato.zapato.HomeView.Home
import com.zapato.zapato.MainActivity
import com.zapato.zapato.Model.User
import android.support.design.widget.Snackbar
import android.support.annotation.NonNull
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.*
import com.zapato.zapato.LoginView.Login


/**
 * Created by adrian on 4/2/18.
// */
class FirebaseManager {

    // Firebase Auth Object.
    lateinit var firebaseAuth: FirebaseAuth
    // Database reference
    var my_users_Ref = FirebaseDatabase.getInstance().getReference("users")



    // MARK - Find out if user existed already, true: do nothing, else: write new user data to the database

    fun checkUserData(myRef: DatabaseReference) {
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


    // MARK - Write new user data to the database

    fun writeNewUser(username: String, email: String, userId: String) {
        // create new user object
        val user = User(username, email)
        //upload user object to database
        my_users_Ref!!.child(userId).setValue(user)
    }



    // MARK - Return Current User

    fun CurrenUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }


}