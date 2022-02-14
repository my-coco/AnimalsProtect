package com.sixing.animalsprotect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sixing.animalsprotect.R;
import com.sixing.animalsprotect.bean.AnimalHome;
import com.sixing.animalsprotect.bean.SearchResult;

import java.util.ArrayList;
import java.util.List;

public class AnimalHomeAdapter extends BaseAdapter {
    private List<SearchResult> searchResults;
    private Context context;
    private View.OnClickListener onClickListener;

    public AnimalHomeAdapter(Context context, List<SearchResult> searchResults, View.OnClickListener onClickListener){
        this.searchResults=searchResults;
        this.context=context;
        this.onClickListener=onClickListener;
    }

    @Override
    public int getCount() {
        return searchResults.size();
    }

    @Override
    public Object getItem(int position) {
        return searchResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Hodler hodler=null;
        if(convertView==null){
            hodler=new Hodler();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_home_animal,parent,false);
            hodler.imageView=convertView.findViewById(R.id.imageView);
            hodler.name=convertView.findViewById(R.id.item_title);
            hodler.tag=convertView.findViewById(R.id.tag);
            convertView.setTag(hodler);
        }else{
            hodler=(Hodler) convertView.getTag();
        }
        SearchResult searchResult=searchResults.get(position);
        hodler.imageView.setImageDrawable(context.getDrawable(R.drawable.xiaomao1));
        hodler.name.setText(searchResult.getName());
        hodler.tag.setText("#新朋友");

        return convertView;
    }

    static class Hodler{
        ImageView imageView;
        TextView name;
        TextView tag;
    }
}
