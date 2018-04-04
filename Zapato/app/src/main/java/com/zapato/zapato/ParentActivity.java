package com.zapato.zapato;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class ParentActivity extends AppCompatActivity {

   private TextView mTextMessage;

   private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
           = new BottomNavigationView.OnNavigationItemSelectedListener() {

      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem item) {
         switch (item.getItemId()) {
            case R.id.navigation_home:
               mTextMessage.setText(R.string.title_home);
               return true;
            case R.id.navigation_search:
               mTextMessage.setText(R.string.title_search);
               return true;
            case R.id.navigation_newpost:
               mTextMessage.setText(R.string.title_newpost);
               return true;
            case R.id.navigation_heart:
               mTextMessage.setText(R.string.title_heart);
               return true;
            case R.id.navigation_account:
               mTextMessage.setText(R.string.title_account);
               return true;
         }
         return false;
      }
   };

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_parent);

      mTextMessage = (TextView) findViewById(R.id.message);
      BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
      navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
   }

}
