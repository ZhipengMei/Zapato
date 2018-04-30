package com.zapato.zapato.ProductDetailView

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.zapato.zapato.Model.Shoe
import com.zapato.zapato.Network.FirebaseManager
import com.zapato.zapato.R
import com.zapato.zapato.fragments.BaseFragment
import kotlinx.android.synthetic.main.activity_product_detail.*


class ProductDetail: BaseFragment() {

    companion object {
        val TAG = "Tab1Fragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView: Starting.")

        var thisView = inflater.inflate(R.layout.activity_product_detail, container, false)


        return thisView
    }

    //top tool bar
    private fun configureToolBarUI() {
        // toolbar_productdetail is defined in the layout file
        (activity as AppCompatActivity).setSupportActionBar(toolbar_productdetail)


        // Get a support ActionBar corresponding to this toolbar
        // Enable the Up button
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true);
    }

    private fun configurePassedData() {
        //TODO testing
        val intent = activity!!.intent

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