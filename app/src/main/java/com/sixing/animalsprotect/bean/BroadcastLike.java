package com.sixing.animalsprotect.bean;

public class BroadcastLike {
    private String userName;
    private String userId;
    public BroadcastLike(String userName, String userId){
        this.userName=userName;
        this.userId=userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
