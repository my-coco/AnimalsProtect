package com.sixing.animalsprotect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sixing.animalsprotect.R;
import com.sixing.animalsprotect.bean.AnimalRank;

import java.util.List;

public class AnimalRankAdapter extends BaseAdapter {

    private List<AnimalRank> animalRanks;
    private Context context;

    public AnimalRankAdapter(Context context, List<AnimalRank> animalRanks){
        this.animalRanks=animalRanks;
        this.context=context;
    }

    @Override
    public int getCount() {
        return animalRanks.size();
    }

    @Override
    public Object getItem(int position) {
        return animalRanks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Hodler hodler=null;
        AnimalRank animalRank=animalRanks.get(position);
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_rank_animal,parent,false);
            hodler=new Hodler();
            hodler.animal_pic=convertView.findViewById(R.id.animal_rank_pic);
            hodler.animal_name=convertView.findViewById(R.id.animal_name);
            hodler.animal_votes=convertView.findViewById(R.id.animal_votes);
            hodler.animal_rank_tx=convertView.findViewById(R.id.animal_rank_tx);
            convertView.setTag(hodler);
        }else{
            hodler=(Hodler)convertView.getTag();
        }
        hodler.animal_pic.setImageDrawable(animalRank.getAnimal_pic());
        hodler.animal_name.setText(animalRank.getAnimal_name());
        hodler.animal_votes.setText(animalRank.getAnimal_votes());
        hodler.animal_rank_tx.setText(animalRank.getAnimal_rank());

        return convertView;
    }

    static class Hodler{
        ImageView animal_pic;
        TextView animal_name;
        TextView animal_votes;
        TextView animal_rank_tx;
    }
}
