package com.dikamjitborah.hobarb.newzflass.ApiHandling;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.dikamjitborah.hobarb.newzflass.Adapter.NewsAdapter_main;
import com.dikamjitborah.hobarb.newzflass.MainActivity;
import com.dikamjitborah.hobarb.newzflass.Model.NewsSchema;
import com.dikamjitborah.hobarb.newzflass.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class NewsApi_calls {
    boolean done = false;

    Context context;
    ArrayList<NewsSchema> news;
    NewsAdapter_main newsAdapter;
    ListView listView;
    public View pb_load_view;
    public boolean pb_loading = false;
    Handler pb_handler;

    public NewsApi_calls(Context context, ArrayList<NewsSchema> news, NewsAdapter_main newsAdapter, ListView listView){
        this.context = context;
        this.news = news;
        this.newsAdapter = newsAdapter;
        this.listView = listView;


    }

    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://spaceflightnewsapi.net/api/v2/").addConverterFactory(GsonConverterFactory.create()).build();
    NewsApi newsApi = retrofit.create(NewsApi.class);

    /*public static NewsApi_calls getInstance()
    {
        return new NewsApi_calls();
    }*/

    public boolean getSpaceData(int limit){
        Call<List<NewsSchema>> call = newsApi.getArticles(limit);
        call.enqueue(new Callback<List<NewsSchema>>() {
            @Override
            public void onResponse(Call<List<NewsSchema>> call, Response<List<NewsSchema>> response) {

                if(!response.isSuccessful())
                {
                    Toast.makeText(context, "" + response.code(), Toast.LENGTH_SHORT).show();
                }
                else {
                    //progressBar.setVisibility(View.GONE);
                    news = (ArrayList<NewsSchema>) response.body();
                    newsAdapter = new NewsAdapter_main(context, news);
                    listView.setAdapter(newsAdapter);
                    //Toast.makeText(context, "nesapi"+news, Toast.LENGTH_SHORT).show();
                    done = true;
                    LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                    pb_load_view = layoutInflater.inflate(R.layout.loading_footer, null);
                    pb_handler = new loadingHandler();
                    listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

                if(absListView.getLastVisiblePosition() == news.size()-1 && listView.getCount()>=5 && pb_loading==false)
                {
                    pb_loading=true;
                    Thread thread = new threadGetMoreData();
                    thread.start();
                }

            }
        });


                }

            }

            @Override
            public void onFailure(Call<List<NewsSchema>> call, Throwable t) {
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return done;
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
        NewsApi_calls newsApi_calls = new NewsApi_calls(context,news, newsAdapter, listView);
        newsApi_calls.getSpaceData(5, 5);

        halves = news;
        return halves;
    }

    private void getSpaceData(int limit, int skip) {

        Call<List<NewsSchema>> call = newsApi.getArticles(limit, skip);
        call.enqueue(new Callback<List<NewsSchema>>() {
            @Override
            public void onResponse(Call<List<NewsSchema>> call, Response<List<NewsSchema>> response) {

                if(!response.isSuccessful())
                {
                    Toast.makeText(context, "eror" + response.code(), Toast.LENGTH_SHORT).show();
                }
                else {
                    //progressBar.setVisibility(View.GONE);
                    news = (ArrayList<NewsSchema>) response.body();
                }

            }

            @Override
            public void onFailure(Call<List<NewsSchema>> call, Throwable t) {
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
