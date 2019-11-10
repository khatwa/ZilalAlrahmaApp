package com.khatwa.zilalalrahmaapp.Network;


import com.khatwa.zilalalrahmaapp.Model.NewsItem;
import com.khatwa.zilalalrahmaapp.Model.NewsListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("NewsDetails.php")
    Call<NewsItem> getNewsDetails(@Query("newsId") int newsID);

    @GET("NewsList.php")
    Call<NewsListResponse> getNewsList(@Query("page") int PageNo);

}