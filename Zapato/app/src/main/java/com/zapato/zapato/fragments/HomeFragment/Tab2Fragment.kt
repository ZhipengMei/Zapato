package com.zapato.zapato.fragments.HomeFragment;

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
import java.util.*


class Tab2Fragment : Fragment() {
    private var gridView: GridView? = null
    private var mShoeList: ArrayList<Shoe>? = null
    private var mImageAdapter: ImageAdapter? = null

    companion object {
        val TAG = "Tab2Fragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView: Starting.")

        var thisView = inflater.inflate(R.layout.product_browse, container, false)

        //fetch Near Me function
        FirebaseManager().fetch(
                FirebaseManager().nearme_ref,
                {
                    mShoeList = it
                    //parse mShoeList data into ImageAdapter
                    mImageAdapter = ImageAdapter(this.context, mShoeList!!);

                    gridView = thisView.findViewById<View>(R.id.product_browse) as GridView
                    gridView!!.adapter = mImageAdapter
                    //optional
                    gridView!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                        //Toast.makeText(activity, "" + position, Toast.LENGTH_SHORT).show()
                        SegueManager().passDatatoProductDetail(this@Tab2Fragment.context!!, mShoeList!![position])
                    }
                    thisView = thisView
                }
        )

        return thisView
    }

}
