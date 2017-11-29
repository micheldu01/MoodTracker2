package com.michelapplication.moodtracker.BDD;

/**
 * Created by michel on 29/11/2017.
 */

public class Mood {

    private int id;
    private int color;
    private int sizeColor;
    private int sizeComnent;
    private String comment;

    public Mood() {
    }

    public Mood(int color, int sizeColor, int sizeCommnent, String comment) {
        this.color = color;
        this.sizeColor = sizeColor;
        this.sizeComnent = sizeCommnent;
        this.comment = comment;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getSizeColor() {
        return sizeColor;
    }

    public void setSizeColor(int sizeColor) {
        this.sizeColor = sizeColor;
    }

    public int getSizeCommnent() {
        return sizeComnent;
    }

    public void setSizeCommnent(int sizeCommnent) {
        this.sizeComnent = sizeCommnent;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
