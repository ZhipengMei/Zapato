package com.zapato.zapato.Network

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
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
import com.google.firebase.storage.FirebaseStorage
import com.zapato.zapato.LoginView.Login
import com.zapato.zapato.Model.Shoe
import com.zapato.zapato.R
import com.google.firebase.storage.StorageReference
import java.io.File
import com.google.firebase.storage.UploadTask
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.OnFailureListener
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.util.ArrayList


/**
 * Created by adrian on 4/2/18.
// */
class FirebaseManager {

    // Firebase Auth Object.
    lateinit var firebaseAuth: FirebaseAuth

    // Database reference to "users" endpoint
    var my_users_Ref = FirebaseDatabase.getInstance().getReference("users")

    // Database reference to shoes endpoint
    var shoe_ref = FirebaseDatabase.getInstance().getReference("shoes")

    // Database reference to Trending endpoint
    var trending_ref = FirebaseDatabase.getInstance().getReference("trending")

    // Database reference to Buy Now endpoint
    var buynow_ref = FirebaseDatabase.getInstance().getReference("buynow")

    // Create a storage reference from our app to "ShoeImages" endpoint
    var storageRef = FirebaseStorage.getInstance().getReference("ShoeImages")


    // MARK - Find out if user existed already, true: do nothing, else: write new user data to the database

    fun checkUserData(myRef: DatabaseReference) {
        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // check if this user exist or not
                if (dataSnapshot.exists()) {
                    Log.d("Firebase_Zapato_Tag", "User Found")
                } else {

                    // Getting Firebase Auth Instance into firebaseAuth object.
                    val firebaseUser = CurrenUser()

                    // creating a new user object
                    val newUser = User(firebaseUser!!.displayName!!.toString(), firebaseUser.email!!.toString(), firebaseUser.uid)

                    // saving new user's data to database
                    writeNewUser(newUser)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("Firebase_Zapato_Tag", "Failed to read value.", error.toException())
            }
        })
    }



    // MARK - Write new user data to the database

    fun writeNewUser(newUser: User) {
        // create new user object without the unique id
        val user = User(newUser.name, newUser.email)
        //upload user object to database
        my_users_Ref!!.child(newUser.uid).setValue(user)
    }


    // MARK - Return Current Logged in User

    fun CurrenUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }



    // MARK - Upload new shoe object to databse

    fun uploadShoeObject(newShoe: Shoe, imageFile: InputStream) {

        Log.d("Firebase_Zapato_Tag", "Adding new shoe data to database.")

        // create a unique ID
        val key = shoe_ref.push().key

        newShoe.shoeID = key
        newShoe.sellerID = CurrenUser()!!.uid

        // upload shoe object to database
        shoe_ref.child(CurrenUser()!!.uid).child(key).setValue(newShoe)

        // upload shoe's image file to storage with the same key
        uploadImageFile(newShoe.name, key, imageFile)

        //TODO: temporary save all new shoe to buy now
        //buynow_ref.child(key).setValue(key)
    }



    // MARK - upload image file to Firebase storage

    fun uploadImageFile(shoeName: String, key: String, imageFileInputStream: InputStream) {
        Log.d("Firebase_Zapato_Tag", "Here")

        //point the storage reference to the correct end point
        storageRef = storageRef.child(key).child(shoeName)

        //upload file (InputStream format) to storage
        var uploadTask = storageRef.putStream(imageFileInputStream)

        // task listener on upload progress
        uploadTask.addOnFailureListener(OnFailureListener {
            // Handle unsuccessful uploads
            Log.d("Firebase_Zapato_Tag", "Error: Upload Shoe Image Failed")
        }).addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
            val downloadUrl = taskSnapshot.downloadUrl

            Log.d("Firebase_Zapato_Tag", downloadUrl.toString())

            // upload the url to shoe object database
            shoe_ref.child(CurrenUser()!!.uid).child(key).child("shoeImageUrl").setValue(downloadUrl.toString())
        })
    }



     // MARK - Fetch all shoe data (asynchronously, download speed depending on network speed)

    fun fetchAllShoes(callback: (ArrayList<Shoe>) -> Unit) {
        val mShoeList = ArrayList<Shoe>()

        shoe_ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    for (snapshot2 in snapshot.children) {
                        val fetchedShoe = Shoe(snapshot2)
                        mShoeList!!.add(fetchedShoe)
                    }
                }
                //completion handler, return the mShoeList to whoever called it
                callback(mShoeList!!)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })
    }



    // MARK - download image using url

    fun getBitmapFromURL(src:String): Bitmap? {
        println("inside getBitmapFromURL: " + src)

        try {
            val url = java.net.URL(src)
            val connection = url
                    .openConnection() as HttpURLConnection
            connection.setDoInput(true)
            connection.connect()
            val input = connection.getInputStream()
            val myBitmap = BitmapFactory.decodeStream(input)
            println(myBitmap)

            return myBitmap
        }
        catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }


}
