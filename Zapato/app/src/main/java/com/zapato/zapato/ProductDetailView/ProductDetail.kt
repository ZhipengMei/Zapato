package com.zapato.zapato.ProductDetailView

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.bumptech.glide.Glide
import com.google.firebase.FirebaseNetworkException
import com.zapato.zapato.Model.Shoe
import com.zapato.zapato.Network.FirebaseManager
import com.zapato.zapato.R
import kotlinx.android.synthetic.main.activity_new_post_1.*
import kotlinx.android.synthetic.main.activity_product_detail.*

class ProductDetail: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        configureToolBarUI()
        configurePassedData()

    }

    //top tool bar
    private fun configureToolBarUI() {
        // toolbar_productdetail is defined in the layout file
        setSupportActionBar(toolbar_productdetail);

        // Get a support ActionBar corresponding to this toolbar
        // Enable the Up button
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true);
    }

    private fun configurePassedData() {
        // passed in shoe object
        val shoeObject = intent.extras!!.getSerializable("productDetail") as Shoe

        shoe_name.setText(shoeObject.name)
        shoe_price.setText(shoeObject.price.toString())
        shoe_condition.setText(shoeObject.shoeCondition.toString())
        Glide.with(this).load(shoeObject.shoeImageUrl).into(shoe_image)

        configureUserProfile(shoeObject.sellerID)

    }

    private fun configureUserProfile(sellID: String) {
        FirebaseManager().fetchSingleUser(FirebaseManager().my_users_Ref.child(sellID), {
            // set the sell's name
            seller_details.setText(it.name)
        })

    }

}