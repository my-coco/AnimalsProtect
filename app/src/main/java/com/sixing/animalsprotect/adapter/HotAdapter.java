package com.sixing.animalsprotect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sixing.animalsprotect.R;

import java.util.List;

public class HotAdapter extends BaseAdapter{
    private List<String> hot_list;
    private Context context;

    public HotAdapter(List<String> hot_list,Context context){
        this.hot_list=hot_list;
        this.context=context;
    }

    @Override
    public int getCount() {
        return hot_list.size();
    }

    @Override
    public Object getItem(int position) {
        return hot_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_hot,parent,false);
        }
        ((TextView)convertView.findViewById(R.id.hot_title)).setText(hot_list.get(position));
        return convertView;
    }
}
