package com.sixing.animalsprotect.bean;

import android.graphics.drawable.Drawable;

public class AnimalHome {
    private Drawable animal_pic;
    private String title;
    private String tag;
    public AnimalHome(Drawable animal_pic,String title,String tag){
        this.animal_pic=animal_pic;
        this.title=title;
        this.tag=tag;
    }

    public Drawable getAnimal_pic() {
        return animal_pic;
    }

    public void setAnimal_pic(Drawable animal_pic) {
        this.animal_pic = animal_pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
