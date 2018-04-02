package com.zapato.zapato;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by agustincards on 3/6/18.
 */

public class ImageAdapter extends BaseAdapter {
   private static final String TAG = "ImageAdapter";

   private ArrayList<Shoe> shoeList;
   private LayoutInflater mInflater;

   public ImageAdapter(Context context, ArrayList<Shoe> list) {
      Log.d(TAG, "constructor: Starting.");
      shoeList = list;
      mInflater = LayoutInflater.from(context);
      System.out.println("shoeList size in Image Adapter constructor: "+shoeList.size());

   }

   @Override
   public int getCount() {
      System.out.println("shoeList size in getCount(): "+shoeList.size());
      return shoeList.size();
   }

   @Override
   public Shoe getItem(int i) {
      System.out.println("getting Item: "+i);
      return shoeList.get(i);
   }

   @Override
   public long getItemId(int i) {
      System.out.println("getting drawable: "+shoeList.get(i).drawableId);
      return shoeList.get(i).drawableId;
   }


   // create a new ImageView for each item referenced by the Adapter
   @Override
   public View getView(int i, View view, ViewGroup viewGroup) {
      Log.d(TAG,"inside getView()");
      View v = view;
      ImageView picture;
      TextView name;
      TextView price;

      if (v == null) {
         v = mInflater.inflate(R.layout.grid_item, viewGroup, false);
         v.setTag(R.id.picture, v.findViewById(R.id.picture));
         v.setTag(R.id.text, v.findViewById(R.id.text));

      }

      picture = (ImageView) v.getTag(R.id.picture);
      name = (TextView) v.getTag(R.id.text);

      Shoe shoe = getItem(i);

      picture.setImageResource(shoe.drawableId);
      name.setText(shoe.name);

      return v;
   }

}
