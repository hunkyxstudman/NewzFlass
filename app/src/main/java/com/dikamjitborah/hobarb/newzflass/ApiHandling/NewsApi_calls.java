package com.dikamjitborah.hobarb.newzflass.ApiHandling;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.dikamjitborah.hobarb.newzflass.Adapter.NewsAdapter_main;
import com.dikamjitborah.hobarb.newzflass.MainActivity;
import com.dikamjitborah.hobarb.newzflass.Model.NewsSchema;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsApi_calls {
    Context context;
    ArrayList<NewsSchema> news;
    NewsAdapter_main newsAdapter;
    ListView listView;

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

    public void getSpaceData(int limit){
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

                }

            }

            @Override
            public void onFailure(Call<List<NewsSchema>> call, Throwable t) {
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
