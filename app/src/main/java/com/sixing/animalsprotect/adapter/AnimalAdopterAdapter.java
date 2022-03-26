package com.sixing.animalsprotect.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.sixing.animalsprotect.R;
import com.sixing.animalsprotect.bean.AnimalInformation;
import com.sixing.animalsprotect.constant.Constants;
import com.sixing.animalsprotect.ui.animal.AnimalActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class AnimalAdopterAdapter extends RecyclerView.Adapter<AnimalAdopterAdapter.Holder>{
    private List<AnimalInformation> animalInformations;
    private Context context;
    private View.OnClickListener onClickListener;
    public AnimalAdopterAdapter(Context context, List<AnimalInformation> animalInformations, View.OnClickListener onClickListener){
        this.animalInformations=animalInformations;
        this.context=context;
        this.onClickListener=onClickListener;
    }

    public void add(AnimalInformation animalInformation){
        animalInformations.add(animalInformation);
    }


    static class Holder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView1;
        TextView textView2;
        ConstraintLayout bg;
        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.animal_pic);
            textView1=itemView.findViewById(R.id.animal_name);
            textView2=itemView.findViewById(R.id.surplusFood);
            bg=itemView.findViewById(R.id.bg);
        }
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=View.inflate(context,R.layout.item_adoption_animal,null);
        Holder holder=new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        AnimalInformation animalInformation=animalInformations.get(position);
        try{
            holder.imageView.setImageDrawable(context.getResources().obtainTypedArray(R.array.animal_pictures).getDrawable(position%11));
        }catch (Exception e){
            e.printStackTrace();
        }
//        holder.imageView.setImageDrawable(iconDrawable(animalAdoption.getAnimal_pic().getAbsolutePath()));
        holder.textView1.setText(animalInformation.getName());
        holder.textView2.setText(String.valueOf(animalInformation.getSurplusFood()));
        holder.bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, AnimalActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString(Constants.ANIMALID,animalInformation.getId());
                intent.putExtra(Constants.ANIMALIDBUNDLE,bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return animalInformations.size();
    }
}
