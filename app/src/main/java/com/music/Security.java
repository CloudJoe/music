package com.music;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Security {
    private final static String ENCODE = "UTF-8";
    private List<Data> x;

    public String getG_TK(String str) {
        int hash = 5381;
        for (int i = 0, len = str.length(); i < len; ++i) {
            hash += (hash << 5) + (int) (char) str.charAt(i);
        }
        return (hash & 0x7fffffff) + "";
    }


    public String getURLEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, ENCODE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;


    }

    public String jsonob(String json, String keyword) {
        String y = "";
        try {
            JSONObject so = new JSONObject(json);
            if (so.has(keyword)) {
                y = so.getString(keyword);
            } else {
                return null;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return y;
    }

    public String jsonar(String json) {
        String y = "";
        JSONArray so = null;
        try {
            so = new JSONArray(json);
            JSONObject jo = so.getJSONObject(0);
            y = jo.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return y;
    }


    public List<Data> jsonarray(String json) {
        x = new ArrayList<>();
        Security security = new Security();
        try {
            JSONArray so = new JSONArray(json);
            if (so.length() == 0) {
                return null;
            } else {
                for (int i = 0; i < so.length(); i++) {
                    JSONObject jo = so.getJSONObject(i);

                    if (jo.has("mid")) {
                        String mid = jo.getString("mid");
                        String title = jo.getString("title");
                        String jsonob = security.jsonob(String.valueOf(jo), "singer");
                        String jsonfile = security.jsonob(String.valueOf(jo), "file");
                        String  s128= security.jsonob(jsonfile, "size_128");
                        String  s320= security.jsonob(jsonfile, "size_320");
                        String  sacc= security.jsonob(jsonfile, "size_acc");
                        String  sape= security.jsonob(jsonfile, "size_ape");
                        String  sdts= security.jsonob(jsonfile, "size_dts");
                        String  sflac= security.jsonob(jsonfile, "size_flac");
                        String  sogg= security.jsonob(jsonfile, "size_ogg");
                        String  stry= security.jsonob(jsonfile, "size_try");
                        String jsonobar = security.jsonar(jsonob);
                        String singer = security.jsonob(jsonobar, "title");
                        Data d = new Data(mid, title, singer,s128,s320,sacc,sape,sdts,sflac,sogg,stry);
                        x.add(d);
                    } else {
                        Log.e("未找到", "jo");
                        continue;

                    }

                }
            }
        } catch (JSONException e) {
        }
        return x;
    }

    public void musicSize() {

    }
}