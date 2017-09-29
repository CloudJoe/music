package com.music;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebActivity extends AppCompatActivity {

    private  String LOGIN_URL="https://ui.ptlogin2.qq.com/cgi-bin/login?daid=384&pt_no_auth=1&style=11&appid=1006102&s_url=https%3A%2F%2Fy.qq.com%2Fportal%2Fprofile.html";
    WebView webview;
    String  a,x,y,z;
    String finnalurl,skey,uin;
    Security security;
    Handler handler;
    private  String LOGIN="https://y.qq.com/portal/profile.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_login);
        webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {

                if (msg.what == 0x123) {
                    Intent intent=new Intent();
                    intent.setClass(WebActivity.this, SearchActivity.class);
                    intent.putExtra("vkey",a);
                    intent.putExtra("uin",uin);
                    startActivity(intent);
                    finish();
                }
            }
        };
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(webview, url);
                CookieManager cookieManager = CookieManager.getInstance();
                Log.d("url", url);
                if (url.indexOf("y.qq.com/portal/profile.html") != -1) {
                    String CookieStr = cookieManager.getCookie(url);
                    if (CookieStr != null) {
                        Log.d("cookie", CookieStr);
                        if (CookieStr.indexOf("skey=@") != -1) {
                             skey = CookieStr.substring(CookieStr.indexOf("skey=@") + 6, CookieStr.indexOf("skey=@") + 15);
                            Log.d("skey",skey);
                             uin = CookieStr.substring(CookieStr.indexOf("; uin=o") + 7, CookieStr.indexOf("; skey=@"));
                            security = new Security();
                            y = security.getG_TK(skey);
                            finnalurl="http://c.y.qq.com/base/fcgi-bin/fcg_music_express_mobile3.fcg?g_tk="+ y + "&loginUin="+ uin  + "&hostUin=0&format=json&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0&cid=205361747&uin=" + uin +"&songmid=003a1tne1nSz1Y&filename=C400003a1tne1nSz1Y.m4a&guid=1234567890";
                            Log.d("uin", uin);
                            Log.d("finnalurl", finnalurl);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Http http=new Http();
                                        z=http.sendGet(finnalurl);
                                        a= security.jsonob(z,"data");
                                        a=security.jsonob(a,"items");
                                        a=security.jsonar(a);
                                        a=security.jsonob(a,"vkey");
                                        Log.d("finally——key",a);
                                        handler.sendEmptyMessage(0x123);//发送消息
                                    } catch (Exception e) {}
                                }

                            }).start();}
                            else {
                            webview.loadUrl(LOGIN_URL);
                        }

                    }

                } else {

                }
            }

        });
        webview.loadUrl(LOGIN);

    }}
//https://c.y.qq.com/soso/fcgi-bin/client_search_cp?ct=24&qqmusic_ver=1298&new_json=1&remoteplace=txt.yqq.center&t=0&aggr=1&cr=1&catZhida=1&lossless=0&flag_qc=0&p=1&n=40&w=%E5%91%A8%E6%9D%B0%E4%BC%A6&&jsonpCallback=searchCallbacksong2020&format=jsonp&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0
// Toast.makeText(getApplicationContext(), "Oh no! " , Toast.LENGTH_SHORT).show();