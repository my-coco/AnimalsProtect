package com.sixing.animalsprotect.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sixing.animalsprotect.R;
import com.sixing.animalsprotect.bean.SearchResult;
import com.sixing.animalsprotect.constant.Constants;
import com.sixing.animalsprotect.ui.animal.AnimalActivity;
import com.sixing.animalsprotect.ui.shelter.ShelterActivity;

import java.util.List;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.Hodler> {
    private List<SearchResult> searchResults;
    private Context context;

    public AnimalAdapter(List<SearchResult> searchResults,Context context){
        this.searchResults=searchResults;
        this.context=context;
    }

    @NonNull
    @Override
    public AnimalAdapter.Hodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=View.inflate(context,R.layout.item_my,null);
        Hodler hodler=new Hodler(view);
        return hodler;
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalAdapter.Hodler holder, @SuppressLint("RecyclerView") int position) {
        holder.textView.setText(searchResults.get(position).getName());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchResult searchResult=searchResults.get(position);
                if (searchResult.getIsAnimal()==1){
                    Intent intent=new Intent(context, AnimalActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString(Constants.ANIMALID,searchResult.getId());
                    intent.putExtra(Constants.ANIMALIDBUNDLE,bundle);
                    context.startActivity(intent);
                }else {
                    Intent intent=new Intent(context, ShelterActivity.class);
                    intent.putExtra(Constants.SHELTERID,searchResult.getId());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    public class Hodler extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        public Hodler(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
            textView=itemView.findViewById(R.id.title_tx);
        }
    }
}
