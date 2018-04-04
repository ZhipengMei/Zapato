package com.zapato.zapato.HomeView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zapato.zapato.Model.ImageAdapter;
import com.zapato.zapato.R;
import com.zapato.zapato.Model.Shoe;

import java.util.ArrayList;

/**
 * Created by agustincards on 2/28/18.
 */

public class Tab1Fragment extends Fragment {
   public static final String TAG = "Tab1Fragment";
   DatabaseReference reference = FirebaseDatabase.getInstance().getReference("sample_data");
   private GridView gridView;
   private ArrayList<Shoe> mShoeList;
   private ImageAdapter mImageAdapter;
   Shoe myShoe;

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      Log.d(TAG, "onCreateView: Starting.");
//      mShoeList = new ArrayList<Shoe>();
//      mImageAdapter = new ImageAdapter(getContext(), mShoeList);
//      populateArray();

      View view =  inflater.inflate(R.layout.product_browse, container, false);
      gridView = (GridView) view.findViewById(R.id.product_browse);
      gridView.setAdapter(mImageAdapter);
      gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
         }
      });

      return view;
   }

   private void populateArray(){
      final int[] pictures = {
              R.drawable.nike_aqua,
              R.drawable.nike_blackbullet,
              R.drawable.nike_vapormax_wolf,
              R.drawable.nike_wheatsport,
              R.drawable.nike_aqua,
              R.drawable.nike_blackbullet,
              R.drawable.nike_vapormax_wolf,
              R.drawable.nike_wheatsport,
              R.drawable.nike_aqua,
              R.drawable.nike_blackbullet,
              R.drawable.nike_vapormax_wolf,
              R.drawable.nike_wheatsport,
              R.drawable.nike_aqua,
              R.drawable.nike_blackbullet,
              R.drawable.nike_vapormax_wolf,
              R.drawable.nike_wheatsport};

      reference.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
            System.out.println("Iterating through snapshot(shoes)");

            int i = 0;
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
               String name = snapshot.child("name").getValue().toString();
               int size = Integer.parseInt(snapshot.child("size").getValue().toString());
               int drawID = pictures[i];
               i++;
               System.out.printf("Shoe Name: %s, Size %d\n", name, size);
               System.out.println("shoeList size inside loop: " + mShoeList.size());
               //myShoe = new Shoe(name, size, drawID);
               //mShoeList.add(myShoe);
               //mImageAdapter.notifyDataSetChanged();

            }
         }

         @Override
         public void onCancelled(DatabaseError databaseError) {
            System.out.println("The read failed: " + databaseError.getCode());
         }
      });
   }

}
