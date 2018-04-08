package com.zapato.zapato.SearchView;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import com.zapato.zapato.R;

public class SearchActivity extends ListActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_search);

      // Get the intent, verify the action and get the query
      Intent intent = getIntent();
      if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
         String query = intent.getStringExtra(SearchManager.QUERY);
         doMySearch(query);
      }

   }

   private void doMySearch(String query)
   {
      //TODO: search data: https://developer.android.com/guide/topics/search/search-dialog.html
   }
}
