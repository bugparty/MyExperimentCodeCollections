package com.example.network;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hanbowen on 2014/8/25.
 */
public class DemoActivity extends android.app.ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new SimpleAdapter(this, getData(),
                android.R.layout.simple_list_item_1,
                new String[]{"title"},
                new int[]{android.R.id.text1}));


    }
    protected List<Map<String,Object>> getData(){
        ArrayList<Map<String,Object>> list = new ArrayList<Map<String, Object>>();

        Map<String,Object> obj = new HashMap<String, Object>();
        obj.put("title","Http Get Demo(使用java.net)");
        obj.put("class",HttpGetExample.class);
        list.add(obj);
        obj = new HashMap<String, Object>();
        obj.put("title","HttpPost Demo(使用apache.http)");
        obj.put("class",HttpPostExample.class);
        list.add(obj);
        obj = new HashMap<String, Object>();
        obj.put("title","HttpPost Demo(使用Poster)");
        obj.put("class",HttpPostExample2.class);
        list.add(obj);
        obj = new HashMap<String, Object>();
        obj.put("title","Baidu Router GetSystenInfo Demo");
        obj.put("class",BRGetDnsExample.class);
        list.add(obj);
        obj = new HashMap<String, Object>();
        obj.put("title","Baidu Router GetSystenInfo Demo(using WebApi");
        obj.put("class",BRGetDnsExample2.class);
        list.add(obj);
        obj = new HashMap<String, Object>();
        obj.put("title","Baidu Router GetStatusInfo Demo(using WebApi");
        obj.put("class",WebApiGetSystemInfoExample.class);
        list.add(obj);

        return list;

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Map<String, Object> map = (Map<String, Object>)l.getItemAtPosition(position);
        Intent i = new Intent();
        i.setClass(this, (Class)map.get("class"));
        startActivity(i);
    }
}
