package com.example.fbt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    private ArrayList<MyItem> items = new ArrayList<>();

    @Override
    public int getCount(){
        return items.size();
    }

    @Override
    public MyItem getItem(int position){
        return items.get(position);
    }

    @Override
    public long getItemId(int position){
        return 0;
    }
    public  void clear(){
        if(items.size() != 0)
            items.clear();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_listview, parent, false);
        }

        TextView nick_text = (TextView) convertView.findViewById(R.id.nick_list);
        TextView date_text = (TextView) convertView.findViewById(R.id.date_list);
        TextView contents_text = (TextView) convertView.findViewById(R.id.content_list);

        MyItem myItem = getItem(position);

        nick_text.setText(myItem.getNickname());
        date_text.setText(myItem.getTime());
        contents_text.setText(myItem.getContents());

        return convertView;
    }

    public void addItem(String nick, String time, String contents){
        MyItem mItem = new MyItem(nick, time, contents);

//        mItem.setNickname(nick);
//        mItem.setTime(time);
//        mItem.setContents(contents);
        items.add(mItem);
    }
}
