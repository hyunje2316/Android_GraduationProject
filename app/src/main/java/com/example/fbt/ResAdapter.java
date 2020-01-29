package com.example.fbt;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class ResAdapter extends BaseAdapter {
    private ArrayList<ResItem> resItems = new ArrayList<>();

    @Override
    public int getCount(){
        return resItems.size();
    }

    @Override
    public ResItem getItem(int position){
        Log.v("he", String.valueOf(position)); return resItems.get(position);
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    public  void clear(){
        resItems.clear();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_restaurant_list, parent, false);
        }

        TextView rest_text = (TextView) convertView.findViewById(R.id.rest_list);
        TextView score_text = (TextView) convertView.findViewById(R.id.score_list);
        RatingBar rating = (RatingBar) convertView.findViewById(R.id.score_bar);

        ResItem resItem = getItem(position);

        rest_text.setText(resItem.getRestname());
        score_text.setText(resItem.getScore());
        rating.setRating(Float.parseFloat(resItem.getScore()));

        return convertView;
    }

    public void addItem(String rest, String score){
        ResItem rItem = new ResItem();

        rItem.setRestname(rest);
        rItem.setScore(score);

        resItems.add(rItem);
    }
}
