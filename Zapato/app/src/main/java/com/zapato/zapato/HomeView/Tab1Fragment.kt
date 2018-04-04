package com.zapato.zapato.HomeView

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import com.zapato.zapato.Model.ImageAdapter

import com.zapato.zapato.Network.FirebaseManager
import com.zapato.zapato.R
import com.zapato.zapato.Model.Shoe
import com.zapato.zapato.ProductDetailView.ProductDetail
import kotlinx.android.synthetic.main.grid_item.view.*
import java.io.Serializable

import java.util.ArrayList

class Tab1Fragment : Fragment() {
    private var gridView: GridView? = null
    private var mShoeList: ArrayList<Shoe>? = null
    private var mImageAdapter: ImageAdapter? = null

    companion object {
        val TAG = "Tab1Fragment"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView: Starting.")

        var thisView = inflater!!.inflate(R.layout.product_browse, container, false)

        //fetch Buy Now function
        FirebaseManager().fetch(FirebaseManager().buynow_ref, {
            mShoeList = it
            //parse mShoeList data into ImageAdapter
            mImageAdapter = ImageAdapter(this.context, mShoeList!!);

            gridView = thisView.findViewById<View>(R.id.product_browse) as GridView
            gridView!!.adapter = mImageAdapter
            //optional
            //gridView!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id -> Toast.makeText(activity, "" + position, Toast.LENGTH_SHORT).show() }
            gridView!!.onItemClickListener = AdapterView.OnItemClickListener {
                parent, view, position, id -> Toast.makeText(activity, "" + mShoeList!![position].name.toString(), Toast.LENGTH_SHORT).show()

                val intent = Intent(this@Tab1Fragment.context, ProductDetail::class.java)

                val bundle = Bundle()
                bundle.putSerializable("productDetail", mShoeList!![position] as Serializable)
                intent.putExtras(bundle)

                startActivity(intent)
            }
        })

        return thisView
    }


//    //fetch All Shoes function
//    fun configureFetchAllShoe() {
//        FirebaseManager().fetchAllShoes({
//            // override mShoeList with returned from database
//            mShoeList = it
//            //parse mShoeList data into ImageAdapter
//            mImageAdapter = ImageAdapter(this.context, mShoeList!!);
//
//            gridView!!.adapter = mImageAdapter
//            //optional
//            gridView!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id -> Toast.makeText(activity, "" + position, Toast.LENGTH_SHORT).show() }
//        })
//    }

}
