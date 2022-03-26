package com.sixing.animalsprotect.bean;

public class BroadcastCommit {
    private String userName;
    private String word;
    private String userId;
    public BroadcastCommit(String userName,String userId,String word){
        this.userName=userName;
        this.userId=userId;
        this.word=word;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
