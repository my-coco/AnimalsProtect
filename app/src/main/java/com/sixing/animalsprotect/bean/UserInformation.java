package com.sixing.animalsprotect.bean;

import java.io.InputStream;

public class UserInformation{
    private String user_phone;
    private String user_password;
    private String user_name;
    private String photo;
    private String bg_photo;

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBg_photo() {
        return bg_photo;
    }

    public void setBg_photo(String bg_photo) {
        this.bg_photo = bg_photo;
    }
}
