package com.sixing.animalsprotect.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sixing.animalsprotect.R;
import com.sixing.animalsprotect.bean.AnimalInformation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class AnimalAdopterAdapter extends BaseAdapter {
    private List<AnimalInformation> animalInformations;
    private Context context;
    private View.OnClickListener onClickListener;
    public AnimalAdopterAdapter(Context context, List<AnimalInformation> animalInformations, View.OnClickListener onClickListener){
        this.animalInformations=animalInformations;
        this.context=context;
        this.onClickListener=onClickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_adoption_animal,parent,false);
            holder=new Holder();
            holder.imageView=convertView.findViewById(R.id.animal_pic);
            holder.textView1=convertView.findViewById(R.id.animal_name);
            holder.textView2=convertView.findViewById(R.id.surplusFood);
            convertView.setTag(holder);
        }else{
            holder=(Holder)convertView.getTag();
        }

       AnimalInformation animalInformation=animalInformations.get(position);
        holder.imageView.setImageDrawable(context.getDrawable(R.drawable.xiaomao1));
//        holder.imageView.setImageDrawable(iconDrawable(animalAdoption.getAnimal_pic().getAbsolutePath()));
        holder.textView1.setText(animalInformation.getName());
        holder.textView2.setText(String.valueOf(animalInformation.getSurplusFood()));
        return convertView;
    }

    public void add(AnimalInformation animalInformation){
        animalInformations.add(animalInformation);
    }


    static class Holder{
        ImageView imageView;
        TextView textView1;
        TextView textView2;
    }

    private Drawable iconDrawable(String file) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        Drawable drawable = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            drawable = new BitmapDrawable(context.getResources(), bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return drawable;
    }

    @Override
    public int getCount() {
        return animalInformations.size();
    }

    @Override
    public Object getItem(int position) {
        return animalInformations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
