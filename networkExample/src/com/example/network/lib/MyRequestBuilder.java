package com.example.network.lib;

import android.net.Uri;
import android.widget.EditText;
import android.widget.TextView;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanbowen on 2014/8/25.
 */
public class MyRequestBuilder {
    public Params params;
    protected String urlStr;
    private HttpClient client;
    private HttpPost post;
    private HttpGet get;
    private HttpResponse response;
    private Header header;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public MyRequestBuilder() {
        client = new DefaultHttpClient();
        params = new Params();
    }

    public MyRequestBuilder(String urlStr) {
        this();
        this.urlStr = urlStr;


    }

    public String getUrl() {
        return urlStr;
    }

    public void setUrl(String urlStr) {
        this.urlStr = urlStr;

    }

    public boolean post() {
        post = new HttpPost(urlStr);
        try {
            post.setEntity(new UrlEncodedFormEntity(params.getParams()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }

        try {
            response = client.execute(post);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }
    public boolean get(Uri uri) {

        get = new HttpGet(uri.toString());
        if(header != null){
            get.setHeader(header);
        }
        try {
            response = client.execute(get);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }
    public JSONObject getResponseJson(){
        JSONObject jobj;
        try {
           jobj = new JSONObject(getResponseBody());
        } catch (JSONException e) {
            e.printStackTrace();
            jobj = null;
        }
        return jobj;
    }
    public String getResponseBody() {
        int ch;

        InputStream is = null;
        try {
            is = response.getEntity().getContent();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        StringBuilder sb = new StringBuilder();
        try {
            while ((ch = is.read()) != -1)
                sb.append((char) ch);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return sb.toString();
    }
    public HttpResponse getResponse(){
        return response;
    }

    /**
     * Created by hanbowen on 2014/8/25.
     */
    public static class Params {
        private List<NameValuePair> nameValuePairs;

        public Params() {
            this.nameValuePairs  = new ArrayList<NameValuePair>();
        }
        public Params add(String key, String value){
            nameValuePairs.add(new BasicNameValuePair(key,
                    value));
            return this;
        }
        public Params add(TextView tvKey, EditText etValue){
            nameValuePairs.add(new BasicNameValuePair(tvKey.getText().toString(),
                    etValue.getText().toString()));
            return this;
        }
        public Params add(EditText etKey, EditText etValue){
            nameValuePairs.add(new BasicNameValuePair(etKey.getText().toString(),
                    etValue.getText().toString()));
            return this;
        }
        public List<NameValuePair> getParams(){
            return nameValuePairs;
        }
    }
}
