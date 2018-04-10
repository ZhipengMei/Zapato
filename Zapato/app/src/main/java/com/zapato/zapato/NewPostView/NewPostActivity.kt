package com.zapato.zapato.NewPostView

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.bumptech.glide.Glide
import com.zapato.zapato.Model.Shoe
import com.zapato.zapato.Network.FirebaseManager
import com.zapato.zapato.R
import kotlinx.android.synthetic.main.activity_new_post_1.*

class NewPostActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post_1)

        // toolbar_newpost is defined in the layout file
        setSupportActionBar(toolbar_newpost);

        // Get a support ActionBar corresponding to this toolbar
        // Enable the Up button
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true);

        Log.d("Firebase_Zapato_Tag", "In NewPostActivity")

        //TODO add temporary fake shoe data (optional feature for MVP)
        //add_button.setOnClickListener{ createNewShoe() }


        // TODO example code on how to load image with URL
        Glide.with(this).load("https://s3.amazonaws.com/appsdeveloperblog/Micky.jpg").into(sample_imageView)

    }

    fun createNewShoe() {

        // new pair of shoe's info
        val newShoe1 = Shoe("Nike Aqua", 9.0, 130.0)
        //read image file as fileinputstream from asset/images folder
        val newShoe1imageFile = applicationContext.assets.open("images/nike_aqua.jpg")

        val newShoe2 = Shoe("Nike Black Bullet", 10.5, 120.0)
        val newShoe2imageFile = applicationContext.assets.open("images/nike_blackbullet.jpg")

        val newShoe3 = Shoe("Nike Vapormax Wolf", 9.5, 100.0)
        val newShoe3imageFile = applicationContext.assets.open("images/nike_vapormax_wolf.jpg")

        val newShoe4 = Shoe("Nike Wheatsport", 11.5, 140.0)
        val newShoe4imageFile = applicationContext.assets.open("images/nike_wheatsport.jpg")

        FirebaseManager().uploadShoeObject(newShoe1, newShoe1imageFile)
        FirebaseManager().uploadShoeObject(newShoe2, newShoe2imageFile)
        FirebaseManager().uploadShoeObject(newShoe3, newShoe3imageFile)
        FirebaseManager().uploadShoeObject(newShoe4, newShoe4imageFile)
    }



}