package com.example.myproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;

import java.util.ArrayList;

public class GameListAdapter extends ArrayAdapter<Game> {
    private Context context;
    private int resource;
    TextView date;
    TextView homeTeam;
    TextView awayTeam;
    TextView scoreHomeTeam;
    TextView scoreAwayTeam;
    ImageView homeTeamImg;
    ImageView awayTeamImg;
    public GameListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Game> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource,parent,false);
        initViews(convertView);
        setViews(position);
        return convertView;
    }

    public void initViews(View convertView){
        date = convertView.findViewById(R.id.date);
        homeTeam = convertView.findViewById(R.id.nameHome);
        awayTeam = convertView.findViewById(R.id.nameAway);
        scoreHomeTeam = convertView.findViewById(R.id.scoreHome);
        scoreAwayTeam = convertView.findViewById(R.id.scoreAway);
        homeTeamImg = convertView.findViewById(R.id.imageHome);
        awayTeamImg = convertView.findViewById(R.id.imageAway);
    }

    public void setViews(int position){
        date.setText(getItem(position).getDate());
        homeTeam.setText(getItem(position).getHomeTeam());
        scoreHomeTeam.setText(String.valueOf(getItem(position).getHomeTeamScore()));
        awayTeam.setText(getItem(position).getAwayTeam());
        scoreAwayTeam.setText(String.valueOf(getItem(position).getAwayTeamScore()));
        homeTeamImg.setImageResource(getImageId(context, getImgName(getItem(position).getHomeTeam())));
        awayTeamImg.setImageResource(getImageId(context, getImgName(getItem(position).getAwayTeam())));
    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }

    public String getImgName(String teamName){
        return teamName.replaceAll(" ", "_").toLowerCase();
    }

}