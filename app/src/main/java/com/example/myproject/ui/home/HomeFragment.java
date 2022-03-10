package com.example.myproject.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myproject.NewsModel;
import com.example.myproject.R;
import com.example.myproject.RetrofitApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    TextView headlines[] = new TextView[10];
    int ids[] = {R.id.headline1, R.id.headline2, R.id.headline3, R.id.headline4, R.id.headline5,
            R.id.headline6, R.id.headline7, R.id.headline8, R.id.headline9, R.id.headline10};



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        for(int i = 0; i < headlines.length ; i++){
            headlines[i] = v.findViewById(ids[i]);
        }

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://nba-stories.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

        Call<List<NewsModel>> call = retrofitApi.getNewsModel();
        call.enqueue(new Callback<List<NewsModel>>() {
            @Override
            public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                if(!response.isSuccessful()){
                    for(int i = 0; i < headlines.length ; i++){
                        headlines[i].setText("unable to reach server");
                    }

                }
                List<NewsModel> data = response.body();
                for(int i = 0; i < headlines.length ; i++){
                    headlines[i].setText(data.get(i).getTitle() + " (source: " + data.get(i).getSource() + ")");
                }

                for(int i = 0; i < headlines.length ; i++){
                    final int j = i;
                    headlines[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.get(j).getUrl()));
                            startActivity(browserIntent);
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<List<NewsModel>> call, Throwable t) {

            }
        });
        return v;
    }



}