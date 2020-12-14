package com.dikamjitborah.hobarb.newzflass.ApiHandling;

import com.dikamjitborah.hobarb.newzflass.Model.NewsSchema;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {
    @GET("articles")
    Call<List<NewsSchema>> getArticles(@Query("_limit") int _limit);
    @GET("articles")
    Call<List<NewsSchema>> getArticles(@Query("_limit") int _limit, @Query("_start") int skip);
}
