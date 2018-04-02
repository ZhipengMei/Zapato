package com.zapato.zapato.NewPostView;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.zapato.zapato.R;

public class NewPostActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_new_post);

      // my_child_toolbar is defined in the layout file
      Toolbar myChildToolbar =
              (Toolbar) findViewById(R.id.toolbar_newpost);
      setSupportActionBar(myChildToolbar);

      // Get a support ActionBar corresponding to this toolbar
      ActionBar ab = getSupportActionBar();

      // Enable the Up button
      ab.setDisplayHomeAsUpEnabled(true);
   }
}
