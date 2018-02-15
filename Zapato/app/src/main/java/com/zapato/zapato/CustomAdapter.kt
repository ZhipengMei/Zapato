package com.zapato.zapato

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

/**
 * Created by adrian on 2/14/18.
 */

class CustomAdapter: BaseAdapter {


//    lateinit var img: IntArray
    var con: Context
//    var shoe: ArrayList<Shoe>
    var shoe: ArrayList<String>
    lateinit var inflator: LayoutInflater

    constructor(con: Context, shoe: ArrayList<String>) {
        this.con = con
        this.shoe = shoe
        inflator = con.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

//    constructor(con: Context, img: IntArray, name: Array<String>) {
//        this.img = img
//        this.con = con
//        this.name = name
//        inflator = con.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var holder = Holder()
        var rv: View
        rv = inflator.inflate(R.layout.row_layout, null)
        holder.tv = rv.findViewById<TextView>(R.id.textView1)
        holder.iv = rv.findViewById<ImageView>(R.id.imageView1)
        holder.tv.setText(shoe[p0].toString())
//        holder.iv.setImageResource(img[p0])

        rv.setOnClickListener(object: View.OnClickListener {
            override fun onClick(p0: View?) {

                Toast.makeText(con, holder.tv.text.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        return rv
    }

    override fun getItem(p0: Int): Any {
        return p0
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return shoe.size
    }

    public class Holder {
        lateinit var tv: TextView
        lateinit var iv: ImageView
    }

}