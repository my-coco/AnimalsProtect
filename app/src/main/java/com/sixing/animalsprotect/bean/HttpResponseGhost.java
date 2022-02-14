package com.sixing.animalsprotect.bean;

public class HttpResponseGhost<T> {
    private String code;
    private String msg;
    private T datas;

    public T getDatas() {
        return datas;
    }

    public void setDatas(T datas) {
        this.datas = datas;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public class AddressInfor{
        public String id;
        public String provinceId;
        public String simpleName;
        public String level;
        public String areaName;
        public String areaCode;
        public String cityId;
        public String parentId;
        public String pinYin;
        public String countyId;
    }
}
