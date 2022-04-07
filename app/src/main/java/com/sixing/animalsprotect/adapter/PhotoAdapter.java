package com.sixing.animalsprotect.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sixing.animalsprotect.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.Holder> {
    private Context context;
    private ArrayList<String> path;
    private boolean select;
    private Handler handler;
    public int index;
    public  PhotoAdapter(Context context, ArrayList<String> path, Handler handler){
        this.context=context;
        this.path=path;
        select=false;
        index=-1;
        this.handler=handler;
    }


    @NonNull
    @Override
    public PhotoAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=View.inflate(context,R.layout.item_photo,null);
        PhotoAdapter.Holder holder=new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoAdapter.Holder holder, @SuppressLint("RecyclerView") int position) {
        holder.imageView.setImageURI(Uri.parse(path.get(position)));
        holder.select_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!select){
                    holder.select_ic.setImageDrawable(context.getDrawable(R.drawable.select));
                    index=position;
                    select=true;
                    handler.sendEmptyMessage(1);
                }else{
                    if(index==position){
                        holder.select_ic.setImageDrawable(context.getDrawable(R.drawable.select_no));
                        select=false;
                        handler.sendEmptyMessage(0);
                    }else{
                        handler.sendEmptyMessage(2);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return path.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private ImageView select_ic;
        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            select_ic=itemView.findViewById(R.id.select_ic);
        }
    }
}
