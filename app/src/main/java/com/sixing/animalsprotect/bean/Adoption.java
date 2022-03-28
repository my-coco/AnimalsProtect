package com.sixing.animalsprotect.bean;

public class Adoption {
    private String animal_id;
    private String user_phone;
    private String animal_name;
    private float surplusFood;



    public float getSurplusFood() {
        return surplusFood;
    }

    public void setSurplusFood(float surplusFood) {
        this.surplusFood = surplusFood;
    }

    public String getAnimal_id() {
        return animal_id;
    }

    public void setAnimal_id(String animal_id) {
        this.animal_id = animal_id;
    }

    public String getAnimal_name() {
        return animal_name;
    }

    public void setAnimal_name(String animal_name) {
        this.animal_name = animal_name;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }
}
