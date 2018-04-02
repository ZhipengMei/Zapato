package com.zapato.zapato;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

   private static final String TAG = "MainActivity";

   private SectionsPageAdapter mSectionsPageAdapter;
   private ViewPager mViewPager;
   private ArrayList<LinearLayout> bottomTabsList;


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      Log.d(TAG, "onCreate: Starting.");

      mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

      // Set up ViewPager with the sections adapter
      mViewPager = (ViewPager)findViewById(R.id.container); //creates id for ViewPager in main layout
      setupViewPager(mViewPager);

      TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
      tabLayout.setupWithViewPager(mViewPager);

      final View homeDot = findViewById(R.id.home_dot);
      homeDot.setVisibility(View.VISIBLE);

      bottomTabsList = new ArrayList<>();
      final LinearLayout homeTab = findViewById(R.id.home_tab);      bottomTabsList.add(homeTab);
      final LinearLayout searchTab = findViewById(R.id.search_tab);  bottomTabsList.add(searchTab);
      final LinearLayout cameraTab = findViewById(R.id.camera_tab);  bottomTabsList.add(cameraTab);
      final LinearLayout heartTab = findViewById(R.id.heart_tab);    bottomTabsList.add(heartTab);
      final LinearLayout accountTab = findViewById(R.id.account_tab);bottomTabsList.add(accountTab);

      /*
      Markers
      homeTab=0, searchTab=1, cameraTab=2, heartTab=3, accountTab=4
       */

      homeTab.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            resetMarker(0);
         }
      });
      searchTab.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            // start Search Activity
            startActivity(new Intent(MainActivity.this, SearchActivity.class));
            resetMarker(1);
         }
      });
      cameraTab.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, NewPostActivity.class));
            resetMarker(2);
         }
      });
      heartTab.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            resetMarker(3);
         }
      });
      accountTab.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            resetMarker(4);
         }
      });
   }

   public void resetMarker(int mark){
      findViewById(R.id.home_dot).setVisibility(View.INVISIBLE);
      findViewById(R.id.search_dot).setVisibility(View.INVISIBLE);
      findViewById(R.id.camera_dot).setVisibility(View.INVISIBLE);
      findViewById(R.id.heart_dot).setVisibility(View.INVISIBLE);
      findViewById(R.id.account_dot).setVisibility(View.INVISIBLE);

      switch (mark){
         case 0: findViewById(R.id.home_dot).setVisibility(View.VISIBLE);
            break;

         case 1: findViewById(R.id.search_dot).setVisibility(View.VISIBLE);
            break;

         case 2: findViewById(R.id.camera_dot).setVisibility(View.VISIBLE);
            break;

         case 3: findViewById(R.id.heart_dot).setVisibility(View.VISIBLE);
            break;

         case 4: findViewById(R.id.account_dot).setVisibility(View.VISIBLE);
             break;

         default: Toast.makeText(MainActivity.this, "reset marker error", Toast.LENGTH_SHORT);
            break;
      }

   }

   private void setupViewPager(ViewPager viewPager) {
      Log.d("setupViewPager","in method");
      SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
      adapter.addFragment(new Tab1Fragment(), "Buy Now");
      adapter.addFragment(new Tab2Fragment(), "Near Me");
      adapter.addFragment(new Tab3Fragment(), "Trending");
      viewPager.setAdapter(adapter);
   }

}
