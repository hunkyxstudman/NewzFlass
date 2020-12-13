package com.dikamjitborah.hobarb.newzflass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.dikamjitborah.hobarb.newzflass.Adapter.NewsAdapter_main;
import com.dikamjitborah.hobarb.newzflass.ApiHandling.NewsApi;
import com.dikamjitborah.hobarb.newzflass.ApiHandling.NewsApi_calls;
import com.dikamjitborah.hobarb.newzflass.Model.NewsSchema;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    NewsAdapter_main newsAdapter_main;
    ListView listView;

    ProgressBar progressBar;
     ArrayList<NewsSchema> news;
     NewsAdapter_main newsAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.tb_main);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextAppearance(this, R.style.font);
        toolbar.setTitle("SPACE NEWS");
       // toolbar.setTitleTextAppearance(this, R.style.armata);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        progressBar = findViewById(R.id.pb_main);

        listView = findViewById(R.id.lv_main);
        NewsApi_calls newsApi_calls = new NewsApi_calls(this,news, newsAdapter, listView);
        newsApi_calls.getSpaceData(5);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem1 = menu.findItem(R.id.search_tb1_menu);
        SearchView searchView = (SearchView) menuItem1.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
               // discussionForumAdapter.getFilter().filter(s);
                return true;
            }
        });
        return true;
    }
}