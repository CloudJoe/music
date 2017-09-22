package com.music;

/**
 * Created by joe on 2017/9/2.
 */

public class Data {
    private String title;
    private String mid;
    private String singer;
    private String vkey;
    private String s128, s320, sacc, sape, sdts, sflac, sogg, stry;

    public Data(String mid, String title, String singer, String s128, String s320, String sacc, String sape, String sdts, String sflac, String sogg, String stry) {
        this.mid = mid;
        this.title = title;
        this.singer = singer;
        this.s128 = s128;
        this.s320 = s320;
        this.sacc = sacc;
        this.sape = sape;
        this.sdts = sdts;
        this.sflac = sflac;
        this.sogg = sogg;
        this.stry = stry;

    }

    public String getName() {
        return title;
    }

    public void setName(String title) {
        this.title = title;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) { this.singer = singer; }

    public String getS128() { return s128; }

    public String getS320() {
        return s320;
    }

    public String getSacc() {
        return sacc;
    }

    public String getSape() {
        return sape;
    }

    public String getSdts() {
        return sdts;
    }

    public String getSflac() {
        return sflac;
    }

    public String getSogg() {
        return sogg;
    }

    public String getStry() {
        return stry;
    }

}
