package com.zapato.zapato.fragments;

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import com.zapato.zapato.Model.ImageAdapter
import com.zapato.zapato.Model.SegueManager
import com.zapato.zapato.Model.Shoe
import com.zapato.zapato.Network.FirebaseManager
import com.zapato.zapato.R
import com.zapato.zapato.activities.MainActivity
import java.util.*

class HomeFragment : Fragment() {
    private var gridView: GridView? = null
    private var mShoeList: ArrayList<Shoe>? = null
    private var mImageAdapter: ImageAdapter? = null

    companion object {
        val TAG = "HomeFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(com.zapato.zapato.fragments.HomeFragment.Companion.TAG, "onCreateView: Starting.")

        var thisView = inflater.inflate(R.layout.product_browse, container, false)

        (activity as MainActivity).updateToolbarTitle("Home")

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
                parent, view, position, id ->
                //Toast.makeText(activity, "" + mShoeList!![position].name.toString(), Toast.LENGTH_SHORT).show()
                SegueManager().passDatatoProductDetail(this@HomeFragment.context!!, mShoeList!![position])
            }
        })

        return thisView
    }


}
