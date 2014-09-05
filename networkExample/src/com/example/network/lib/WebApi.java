package com.example.network.lib;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.util.Log;
import com.example.network.model.BRCookie;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by hanbowen on 2014/8/27.
 */
public class WebApi {
    public static final String TAG = "Router.WebApi";
    private String password;
    private Context context;
    private static final String RouterHostName = "x.du";
    private String hostAvailable =null;
    private Header loginHeader;
    public WebApi(Context context, String password) {
        this.password = password;
        this.context =  context;

    }
    public void init(){
        hostAvailable = judgeHost();
        loadToken();
    }
    private String judgeHost(){
        if(checkHostAvailable(RouterHostName))
            return RouterHostName;
        else{
            DhcpInfo di = getDhcpInfo();
            return IPV4.hex2IPStr(di.gateway);
        }
    }
    private boolean checkHostAvailable(String hostname){
        boolean isAvailable;

            try {
                isAvailable = InetAddress.getByName(hostname).isReachable(2000);
            } catch (UnknownHostException e){
                isAvailable = false;
            }
            catch (IOException e) {
                isAvailable = false;
                e.printStackTrace();
            }
        return isAvailable;
    }
    private DhcpInfo getDhcpInfo(){
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
        return dhcpInfo;
    }
    private void loadToken(){
        MyRequestBuilder client = new MyRequestBuilder();
        Uri uri = new Uri.Builder()
                .scheme("http")
                .authority(hostAvailable)
                .encodedPath("cgi-bin/luci")
                .build();

        client.setUrl(uri.toString());
        client.params
                .add("username", "root")
                .add("bd_web", "1")
                .add("password", this.password);
        client.post();
        HttpResponse response = client.getResponse();
        loginHeader = response.getFirstHeader("Set-Cookie");

    }
    public JSONObject getSystemInfo(){

        BRCookie cookie = new BRCookie();
        cookie.setNameValue(loginHeader.toString());
        Uri uri;
        uri = new Uri.Builder().scheme("http")
                .authority("x.du").appendEncodedPath(cookie.getPath())
                .appendQueryParameter("_",""+System.currentTimeMillis())
                .appendEncodedPath("admin/private/get_system_status")
                .build();
        Log.d(TAG, "URI builded "+uri.toString());
        MyRequestBuilder client2 = new MyRequestBuilder();
        client2.setHeader(loginHeader);
        client2.setUrl(uri.toString());
        client2.params
                .add("username", "root")
                .add("bd_web", "1")
                .add("password", this.password);
        client2.post();
        return client2.getResponseJson();
    }
    public JSONObject getStatusInfo(){

        BRCookie cookie = new BRCookie();
        cookie.setNameValue(loginHeader.toString());
        Uri uri;
        uri = new Uri.Builder().scheme("http")
                .authority("x.du").appendEncodedPath(cookie.getPath())
                .appendPath(";stok="+cookie.getStok())
                .appendEncodedPath("admin/private/get_status_info?cap=1&version=1&cpu_core=1&uptime=1&device_num=1&mac=1&disk_info=1")
                .build();
        Log.d(TAG, "URI builded "+uri.toString());
        MyRequestBuilder client2 = new MyRequestBuilder();
        client2.setHeader(loginHeader);
        client2.setUrl(uri.toString());
        client2.params
                .add("username", "root")
                .add("bd_web", "1")
                .add("password", this.password);
        client2.post();
        return client2.getResponseJson();
    }
}
