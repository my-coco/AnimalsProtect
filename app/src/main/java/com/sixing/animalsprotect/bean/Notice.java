package com.sixing.animalsprotect.bean;

import java.util.List;

public class Notice {
    private String id;
    private String text;
    private String date;
    private int isAnimal;
    private String owerId;
    private List<BroadcastLike> like;
    private List<BroadcastCommit> words;

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

    public List<BroadcastLike> getLike() {
        return like;
    }

    public void setLike(List<BroadcastLike> like) {
        this.like = like;
    }

    public List<BroadcastCommit> getWords() {
        return words;
    }

    public void setWords(List<BroadcastCommit> words) {
        this.words = words;
    }
}
