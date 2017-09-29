package com.music;

/**
 * Created by Administrator on 2017/9/27/027.
 */
public class SizeBean {
    private String timbre;
    private int i;
    private double size;

    public SizeBean(int i, String timbre, double size) {
        this.i = i;
        this.timbre = timbre;
        this.size = size;
    }

    public String getTimbre() {
        return timbre;
    }

    public void setTimbre(String timbre) {
        this.timbre = timbre;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}
