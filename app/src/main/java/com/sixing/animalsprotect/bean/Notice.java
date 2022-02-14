package com.sixing.animalsprotect.bean;

public class Notice {
    private String id;
    private String text;
    private String date;
    private int isAnimal;
    private String owerId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIsAnimal() {
        return isAnimal;
    }

    public void setIsAnimal(int isAnimal) {
        this.isAnimal = isAnimal;
    }

    public String getOwerId() {
        return owerId;
    }

    public void setOwerId(String owerId) {
        this.owerId = owerId;
    }
}
