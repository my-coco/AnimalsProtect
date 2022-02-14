package com.sixing.animalsprotect.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sixing.animalsprotect.R;
import com.sixing.animalsprotect.ui.main.RankFragment;

import java.util.List;

public class AddressAdapter extends BaseAdapter {
    private List<String> address;
    private Context context;
    private String TAG="AddressAdapter";

    public AddressAdapter(Context context, List<String> provinces) {
        this.context=context;
        this.address=provinces;
    }

    @Override
    public int getCount() {
        return address.size();
    }

    @Override
    public Object getItem(int position) {
        return address.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_address,parent,false);
        }
        TextView textView=convertView.findViewById(R.id.address_name);
        textView.setText(address.get(position));
        return convertView;
    }
}
