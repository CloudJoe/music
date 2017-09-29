package com.music;

import java.util.List;

/**
 * Created by joe on 2017/9/2.
 */

public class Data {
    private String title;
    private String mid;
    private String singer;
    private List<SizeBean> sizeBeanList;

    public Data(String mid, String title, String singer, List<SizeBean> sizeBeanList) {
        this.mid = mid;
        this.title = title;
        this.singer = singer;
        this.sizeBeanList=sizeBeanList;
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

    public List<SizeBean> getSizeBeanList() {
        return sizeBeanList;
    }

}
