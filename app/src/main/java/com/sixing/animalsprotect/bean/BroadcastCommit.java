package com.sixing.animalsprotect.bean;

public class BroadcastCommit {
    private String name;
    private String words;
    public BroadcastCommit(String name,String words){
        this.name=name;
        this.words=words;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }
}
