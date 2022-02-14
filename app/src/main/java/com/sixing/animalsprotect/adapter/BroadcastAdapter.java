package com.sixing.animalsprotect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.sixing.animalsprotect.R;
import com.sixing.animalsprotect.bean.Broadcast;

import java.util.List;

public class BroadcastAdapter extends BaseAdapter {
    private List<Broadcast> broadcasts;
    private Context context;
    private View.OnClickListener onClickListener;

    public BroadcastAdapter(List<Broadcast> broadcasts,Context context,View.OnClickListener onClickListener){
        this.broadcasts=broadcasts;
        this.context=context;
        this.onClickListener=onClickListener;
    }

    @Override
    public int getCount() {
        return broadcasts.size();
    }

    @Override
    public Object getItem(int position) {
        return broadcasts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Hodler hodler=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_broadcast,parent,false);
            hodler=new Hodler();
            hodler.pic=convertView.findViewById(R.id.pic);
            hodler.name=convertView.findViewById(R.id.name);
            hodler.words=convertView.findViewById(R.id.words);
            hodler.gridView=convertView.findViewById(R.id.pic_grid);
            hodler.time=convertView.findViewById(R.id.time);
            hodler.prase_names=convertView.findViewById(R.id.people_names);
            hodler.commits=convertView.findViewById(R.id.people_words);
            hodler.praise_bg=convertView.findViewById(R.id.praise_bg);
            convertView.setTag(hodler);
        }else{
            hodler=(Hodler)convertView.getTag();
        }

        Broadcast broadcast=broadcasts.get(position);
        hodler.pic.setImageDrawable(broadcast.getPic());
        hodler.name.setText(broadcast.getName());
        hodler.words.setText(broadcast.getWords());
        hodler.time.setText(broadcast.getTime());
        //图片
        //hodler.gridView
        if (broadcast.getPrase_name()!=null){
            String names="";
            for(String i:broadcast.getPrase_name()){
                names+=i+"、";
            }
            hodler.prase_names.setText(names.substring(0,names.length()-1));
        }else{
            hodler.praise_bg.setVisibility(View.GONE);
        }
        //评论
        if(broadcast.getBroadcastCommits()!=null){
            BroadcastCommitAdapter broadcastCommitAdapter=new BroadcastCommitAdapter(broadcast.getBroadcastCommits(),context);
            hodler.commits.setAdapter(broadcastCommitAdapter);
        }

        return convertView;
    }

    static class Hodler{
        ImageView pic;
        TextView name;
        TextView words;
        GridView gridView;
        TextView time;
        TextView prase_names;
        ListView commits;
        ConstraintLayout praise_bg;
    }
}
