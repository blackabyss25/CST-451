package com.example.lighthouse.model;

import java.io.*;

public class Affirmation implements Serializable{
    private int id;
    private String text;
    private boolean needsReminding;

    public Affirmation() {
        this.id = 0;
        this.text = "";
    }
    public Affirmation(int id, String text) {
        this.id = 0;
        this.text = "";
        this.needsReminding = false;
    }
    public Affirmation(String text){
        this.id = 0;
        this.text = text;
    }

    public String toString(){
        return "ID: " + this.id + " Text: " + this.text + " /n";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getNeedsReminding() {
        return needsReminding;
    }

    public void setNeedsReminding(boolean needsReminding) {
        this.needsReminding = needsReminding;
    }
}
