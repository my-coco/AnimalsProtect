package com.sixing.animalsprotect.bean;

import android.graphics.drawable.Drawable;

import java.util.List;

public class Broadcast {
    private String id;
    private Drawable pic;
    private String name;
    private String words;
    private String time;
    private Boolean like=false;
    private List<BroadcastLike> prase_names;
    private List<BroadcastCommit> broadcastCommits;

    public Broadcast(String id,Drawable pic,String name,String words,String time,List<BroadcastLike> prase_names,List<BroadcastCommit> broadcastCommits){
        this.id=id;
        this.pic=pic;
        this.name=name;
        this.words=words;
        this.time=time;
        this.prase_names=prase_names;
        this.broadcastCommits=broadcastCommits;
    }

    public Drawable getPic() {
        return pic;
    }

    public void setPic(Drawable pic) {
        this.pic = pic;
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

    public List<BroadcastCommit> getBroadcastCommits() {
        return broadcastCommits;
    }

    public void setBroadcastCommits(List<BroadcastCommit> broadcastCommits) {
        this.broadcastCommits = broadcastCommits;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<BroadcastLike> getPrase_names() {
        return prase_names;
    }

    public void setPrase_names(List<BroadcastLike> prase_names) {
        this.prase_names = prase_names;
    }

    public Boolean getLike() {
        return like;
    }

    public void setLike(Boolean like) {
        this.like = like;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
