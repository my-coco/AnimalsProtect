package com.sixing.animalsprotect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sixing.animalsprotect.R;
import com.sixing.animalsprotect.bean.BroadcastCommit;

import java.util.List;

public class BroadcastCommitAdapter extends BaseAdapter {
    private List<BroadcastCommit> broadcastCommits;
    private Context context;

    public BroadcastCommitAdapter(List<BroadcastCommit> broadcastCommits,Context context){
        this.broadcastCommits=broadcastCommits;
        this.context=context;
    }
    @Override
    public int getCount() {
        return broadcastCommits.size();
    }

    @Override
    public Object getItem(int position) {
        return broadcastCommits.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Hodler hodler=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_broadcast_people_commit,parent,false);
            hodler=new Hodler();
            hodler.name=convertView.findViewById(R.id.name);
            hodler.words=convertView.findViewById(R.id.words);
            convertView.setTag(hodler);
        }else{
            hodler=(Hodler)convertView.getTag();
        }
        BroadcastCommit broadcastCommit=broadcastCommits.get(position);
        hodler.name.setText(broadcastCommit.getName());
        hodler.words.setText(broadcastCommit.getWords());
        return convertView;
    }

    static class Hodler{
        TextView name;
        TextView words;
    }
}
