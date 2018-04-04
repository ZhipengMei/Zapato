package com.zapato.zapato.Model

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.zapato.zapato.ProductDetailView.ProductDetail
import java.io.Serializable

class SegueManager: AppCompatActivity() {

    // IMPORTANT
    // this block enables passing data from one view to another
    fun passDatatoProductDetail(thisContext: Context, data: Any) {
        val intent = Intent(thisContext, ProductDetail::class.java)
        val bundle = Bundle()
        bundle.putSerializable("productDetail", data as Serializable)
        intent.putExtras(bundle)
        thisContext.startActivity(intent)
    }

}