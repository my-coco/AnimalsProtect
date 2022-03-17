package com.sixing.animalsprotect.bean;

public class SearchResult {
    private String id;
    private String name;
    private int isAnimal;

    public SearchResult(String id,String name,int isAnimal){
        this.id=id;
        this.name=name;
        this.isAnimal=isAnimal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsAnimal() {
        return isAnimal;
    }

    public void setIsAnimal(int isAnimal) {
        this.isAnimal = isAnimal;
    }
}
