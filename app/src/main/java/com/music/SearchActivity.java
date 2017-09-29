package com.music;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.BottomSheetDialog;
import org.json.JSONObject;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private Handler handler;
    private String vkey, uin,  edkeyword, json;
    private Security security;
    private List<Data> mdata;
    private ListView listview;
    private String D = "https://c.y.qq.com/soso/fcgi-bin/client_search_cp?ct=24&qqmusic_ver=1298&new_json=1&remoteplace=txt.yqq.center&t=0&aggr=1&cr=1&catZhida=1&lossless=0&flag_qc=0&p=1&n=40&w=";
    private String Z = "&&jsonpCallback=searchCallbacksong2020&format=jsonp&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0";
    private String T = "http://dl.stream.qqmusic.qq.com/M5000041vb5D3WMtrl.mp3?vkey=D715580975C90F4AAB1EEFAE5F8D321D4C30F81608F67E6D023EB18C9CDCCC9B4D4198004872ADA5B8CA18202E806ED6177215DD5E13BF8C&guid=1234567890&uin=2500937204&fromtag=64";
    private String TAG = "test";
    private String HEAD="http://dl.stream.qqmusic.qq.com/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        vkey = bundle.getString("vkey");
        uin = bundle.getString("uin");
        listview = (ListView) findViewById(R.id.list);
        if (!isStoragePermissionGranted()) {
            Toast.makeText(this, "请给予SD卡读写权限", Toast.LENGTH_LONG).show();
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Data date = mdata.get(position);
                List<SizeBean> sizeBeanList=date.getSizeBeanList();
                String name=date.getName();
                String mid=date.getMid();
                showBSDialog(sizeBeanList,name,mid);


            }


        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.search_view);
        searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setSubmitButtonEnabled(true);
        searchView.onActionViewExpanded();
        TextView textView = (TextView) searchView
                .findViewById(android.support.v7.appcompat.R.id.search_src_text);
        textView.setHintTextColor(Color.WHITE); //hint文字颜色
        textView.setTextColor(Color.WHITE);
        handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0x123:
                        MyAdapter adapter = new MyAdapter(SearchActivity.this, R.layout.listitem, mdata);
                        listview.setAdapter(adapter);
                        break;
                }
                super.handleMessage(msg);
            }
        };

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (s.length() > 0) {
                    security = new Security();
                    edkeyword = security.getURLEncoderString(s);
                    Log.d("decode", edkeyword);
                    analytic();
                    return false;
                }
                searchView.setIconified(true);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }

    private void analytic() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Http http = new Http();
                    json = http.sendGet(D + edkeyword + Z);
                    Log.d("json", json);
                    json = json.substring(9, json.length() - 1);
                    json = security.jsonob(json, "data");
                    json = security.jsonob(json, "song");
                    json = security.jsonob(json, "list");
                    mdata = security.jsonarray(json);
                    handler.sendEmptyMessage(0x123);
                } catch (Exception e) {
                }
            }
        }).start();
    }


    private void showBSDialog(final List<SizeBean> sizeBeanList, final String singername,final String mid) {
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.checksize, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.bs_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final SizeAdapter adapter = new SizeAdapter(this,sizeBeanList);
        adapter.setItemClickListener(new SizeAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                SizeBean sizeBean=sizeBeanList.get(pos);

                int i=sizeBean.getI();
                DownloadUtils downloadUtils = new DownloadUtils(SearchActivity.this);
                switch (i){
                    case 1:
                        String url1=HEAD+"M500"+mid+".mp3?vkey="+vkey+"&guid=1234567890"+"&uin="+uin+"&fromtag=64";
                        downloadUtils.downloadAPK(url1, singername+"(标准)"+".mp3");
                        dialog.dismiss();
                        break;
                    case 2:
                        String url2=HEAD+"M800"+mid+".mp3?vkey="+vkey+"&guid=1234567890"+"&uin="+uin+"&fromtag=64";
                        downloadUtils.downloadAPK(url2, singername+"(高品质)"+".mp3");
                        dialog.dismiss();
                        break;

                    case 3:
                        String url3=HEAD+"A000"+mid+".ape?vkey="+vkey+"&guid=1234567890"+"&uin="+uin+"&fromtag=64";
                        downloadUtils.downloadAPK(url3, singername+".ape");
                        dialog.dismiss();

                        break;
                    case 4:
                        String url4=HEAD+"F000"+mid+".flac?vkey="+vkey+"&guid=1234567890"+"&uin="+uin+"&fromtag=64";
                        downloadUtils.downloadAPK(url4, singername+".flac");
                        dialog.dismiss();
                        break;
                }

            }
        });
        recyclerView.setAdapter(adapter);
        dialog.setContentView(view);
        dialog.show();
        searchView.clearFocus();
    }


}

