package com.zapato.zapato.NewPostView

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.zapato.zapato.Model.Shoe
import com.zapato.zapato.Network.FirebaseManager
import com.zapato.zapato.R
import kotlinx.android.synthetic.main.activity_new_post.*
import java.io.File
import java.io.FileInputStream

class NewPostActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)

        Log.d("Firebase_Zapato_Tag", "In NewPostActivity")

        //TODO add temporary fake shoe data (optional feature for MVP)
        add_button.setOnClickListener{ createNewShoe() }
    }

    fun createNewShoe() {

        val currentUser = FirebaseManager().CurrenUser()
        var sellerID = currentUser!!.uid

        // new pair of shoe's info
        val newShoe1 = Shoe("Nike Aqua", sellerID, 9.0, 130.0)
        //read image file as fileinputstream from asset/images folder
        val newShoe1imageFile = applicationContext.assets.open("images/nike_aqua.jpg")


        // new pair of shoe's info
        val newShoe2 = Shoe("Nike Black Bullet", sellerID, 10.5, 120.0)
        //read image file as fileinputstream from asset/images folder
        val newShoe2imageFile = applicationContext.assets.open("images/nike_blackbullet.jpg")

        // new pair of shoe's info
        val newShoe3 = Shoe("Nike Vapormax Wolf", sellerID, 9.5, 100.0)
        //read image file as fileinputstream from asset/images folder
        val newShoe3imageFile = applicationContext.assets.open("images/nike_vapormax_wolf.jpg")

        // new pair of shoe's info
        val newShoe4 = Shoe("Nike Wheatsport", sellerID, 11.5, 140.0)
        //read image file as fileinputstream from asset/images folder
        val newShoe4imageFile = applicationContext.assets.open("images/nike_wheatsport.jpg")

        FirebaseManager().uploadShoeObject(newShoe1, newShoe1imageFile)
        FirebaseManager().uploadShoeObject(newShoe2, newShoe2imageFile)
        FirebaseManager().uploadShoeObject(newShoe3, newShoe3imageFile)
        FirebaseManager().uploadShoeObject(newShoe4, newShoe4imageFile)
    }



}