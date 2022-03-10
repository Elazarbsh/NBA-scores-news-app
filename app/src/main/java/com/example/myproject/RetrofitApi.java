package com.example.myproject;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitApi {

    @GET("news?limit=10")
    Call<List<NewsModel>> getNewsModel();
}
