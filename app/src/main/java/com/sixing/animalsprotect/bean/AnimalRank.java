package com.sixing.animalsprotect.bean;

import android.graphics.drawable.Drawable;

public class AnimalRank {
    private Drawable animal_pic;
    private String animal_name;
    private String animal_votes;
    private String animal_rank;
    private String animal_id;

    public AnimalRank(String animal_id,Drawable animal_pic,String animal_name,String animal_votes,String animal_rank){
        this.animal_id=animal_id;
        this.animal_pic=animal_pic;
        this.animal_name=animal_name;
        this.animal_votes=animal_votes;
        this.animal_rank=animal_rank;
    }

    public Drawable getAnimal_pic() {
        return animal_pic;
    }

    public void setAnimal_pic(Drawable animal_pic) {
        this.animal_pic = animal_pic;
    }

    public String getAnimal_name() {
        return animal_name;
    }

    public void setAnimal_name(String animal_name) {
        this.animal_name = animal_name;
    }

    public String getAnimal_votes() {
        return animal_votes;
    }

    public void setAnimal_votes(String animal_votes) {
        this.animal_votes = animal_votes;
    }

    public String getAnimal_rank() {
        return animal_rank;
    }

    public void setAnimal_rank(String animal_rank) {
        this.animal_rank = animal_rank;
    }

    public String getAnimal_id() {
        return animal_id;
    }

    public void setAnimal_id(String animal_id) {
        this.animal_id = animal_id;
    }
}
