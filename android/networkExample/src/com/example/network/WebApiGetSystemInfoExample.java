package com.example.network;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.network.lib.MyRequestBuilder;
import com.example.network.model.BRCookie;
import org.apache.http.Header;
import org.apache.http.HttpResponse;

public class WebApiGetSystemInfoExample extends Activity {
    public static final String TAG = "HttpPostExample2";
    /**
     * Called when the activity is first created.
     */
    private Button btLoad;
    private TextView tvContent;
    private EditText etUrl;
    private EditText etKey1, etKey2, etKey3;
    private EditText etValue1, etValue2, etValue3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Disable network thread limit on activity_http_get_example thread
        StrictMode.ThreadPolicy policy = new StrictMode.
                ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_post_example);
        bindControls();
    }



    private void load(String urlStr) {
        MyRequestBuilder client = new MyRequestBuilder();

        client.setUrl(urlStr);
        client.params.add(etKey1, etValue1).add(etKey2, etValue2).add(etKey3, etValue3);
        client.post();
        HttpResponse response = client.getResponse();
        Header h1 = response.getFirstHeader("Set-Cookie");
        Header [] hs = response.getHeaders("Set-Cookie");
        StringBuilder sb = new StringBuilder(100);
        for(Header s : hs){
            sb.append(s.toString());
        }
        Log.d(TAG, "Hi,"+sb.toString());
        BRCookie cookie = new BRCookie();
        cookie.setNameValue(h1.toString());
        Uri uri;
        uri = new Uri.Builder().scheme("http")
                .authority("x.du").appendEncodedPath(cookie.getPath())
                .appendPath(";stok="+cookie.getStok())
                .appendEncodedPath("admin/private/get_status_info?cap=1&version=1&cpu_core=1&uptime=1&device_num=1&mac=1&disk_info=1")
                .build();
       Log.d(TAG, "URI builded "+uri.toString());
        MyRequestBuilder client2 = new MyRequestBuilder();
        client2.setHeader(h1);
        client2.setUrl(uri.toString());
        client2.params.add(etKey1, etValue1).add(etKey2, etValue2).add(etKey3, etValue3);
        client2.post();
        tvContent.setText(client2.getResponseBody());

    }

    protected void bindControls() {
        btLoad = (Button) findViewById(R.id.button);
        tvContent = (TextView) findViewById(R.id.textView);
        tvContent.setMovementMethod(new ScrollingMovementMethod());
        etUrl = (EditText) findViewById(R.id.etUrl);
        btLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = etUrl.getText().toString();
                load(url);
            }
        });
        etKey1 = (EditText) findViewById(R.id.etKey1);
        etKey2 = (EditText) findViewById(R.id.etKey2);
        etKey3 = (EditText) findViewById(R.id.etKey3);
        etValue1 = (EditText) findViewById(R.id.etValue1);
        etValue2 = (EditText) findViewById(R.id.etValue2);
        etValue3 = (EditText) findViewById(R.id.etValue3);

    }
}
