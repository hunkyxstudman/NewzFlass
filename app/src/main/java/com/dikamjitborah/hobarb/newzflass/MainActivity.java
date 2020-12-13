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
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.dikamjitborah.hobarb.newzflass.Adapter.NewsAdapter_main;
import com.dikamjitborah.hobarb.newzflass.ApiHandling.NewsApi;
import com.dikamjitborah.hobarb.newzflass.Model.NewsSchema;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    NewsAdapter_main newsAdapter_main;
    ListView listView;





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

        listView = findViewById(R.id.lv_main);


        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://spaceflightnewsapi.net/api/v2/").addConverterFactory(GsonConverterFactory.create()).build();
        NewsApi newsApi = retrofit.create(NewsApi.class);
        Call<List<NewsSchema>> call = newsApi.getArticles(10);
        call.enqueue(new Callback<List<NewsSchema>>() {
            @Override
            public void onResponse(Call<List<NewsSchema>> call, Response<List<NewsSchema>> response) {

                if(!response.isSuccessful())
                {
                    Toast.makeText(MainActivity.this, "" + response.code(), Toast.LENGTH_SHORT).show();
                }
                else {
                    ArrayList<NewsSchema> news = (ArrayList<NewsSchema>) response.body();
                    newsAdapter_main = new NewsAdapter_main(getApplicationContext(), news);
                    listView.setAdapter(newsAdapter_main);

                }

            }

            @Override
            public void onFailure(Call<List<NewsSchema>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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