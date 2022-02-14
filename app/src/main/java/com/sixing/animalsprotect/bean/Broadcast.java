package com.sixing.animalsprotect.bean;

import android.graphics.drawable.Drawable;

import java.util.List;

public class Broadcast {
    private Drawable pic;
    private String name;
    private String words;
    private String time;
    private List<String> prase_names;
    private List<BroadcastCommit> broadcastCommits;

    public Broadcast(Drawable pic,String name,String words,String time,List<String> prase_names,List<BroadcastCommit> broadcastCommits){
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


    public List<String> getPrase_name() {
        return prase_names;
    }

    public void setPrase_name(List<String> prase_names) {
        this.prase_names = prase_names;
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
}
