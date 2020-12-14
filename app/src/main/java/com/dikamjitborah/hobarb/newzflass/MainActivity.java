package com.dikamjitborah.hobarb.newzflass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
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
    ListView listView;

    ProgressBar progressBar;
    public static ArrayList<NewsSchema> news;
     NewsAdapter_main newsAdapter;

    public  View pb_load_view;
    public boolean pb_loading = false;
    Handler pb_handler;




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

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        pb_load_view = layoutInflater.inflate(R.layout.loading_footer, null);
        pb_handler = new loadingHandler();

        listView = findViewById(R.id.lv_main);
        Toast.makeText(this, ""+news, Toast.LENGTH_SHORT).show();
        NewsApi_calls newsApi_calls = new NewsApi_calls(this,news, newsAdapter, listView);
        newsApi_calls.getSpaceData(5) ;
        //Toast.makeText(this, ""+news, Toast.LENGTH_SHORT).show();  // news is null because api call not finished??/ how to do async task

        /*listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

                if(absListView.getLastVisiblePosition() == news.size()-1 && listView.getCount()>=10 && pb_loading==false)
                {
                    pb_loading=true;
                    Thread thread = new threadGetMoreData();
                    thread.start();
                }

            }
        });
*/



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

    public class loadingHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    listView.addFooterView(pb_load_view);
                    break;
                case 1:
                    newsAdapter.addListItemToAdapter((ArrayList<NewsSchema>) msg.obj);
                    listView.removeFooterView(pb_load_view);
                    pb_loading = false;
                    break;
                default:
                    break;
            }
        }
    }

    public class threadGetMoreData extends Thread{
        @Override
        public void run() {
            pb_handler.sendEmptyMessage(0);
            ArrayList<NewsSchema> halves_got = getmoredata();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message msg = pb_handler.obtainMessage(1, halves_got);
            pb_handler.sendMessage(msg);

        }
    }

    private ArrayList<NewsSchema> getmoredata(){
        ArrayList<NewsSchema> halves = new ArrayList<>();
        NewsApi_calls newsApi_calls = new NewsApi_calls(this,news, newsAdapter, listView);
        newsApi_calls.getSpaceData(5);

        return halves;
    }
}