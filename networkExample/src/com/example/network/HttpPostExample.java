package com.example.network;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpPostExample extends Activity {
    /**
     * Called when the activity is first created.
     */
    private Button btLoad;
    private TextView tvContent;
    private EditText etUrl;
    private EditText etKey1,etKey2,etKey3;
    private EditText etValue1,etValue2,etValue3;
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
    protected  void bindControls(){
        btLoad = (Button)findViewById(R.id.button);
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
    private void load(String urlStr){
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(urlStr);
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair(etKey1.getText().toString(),
                    etValue1.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair(etKey2.getText().toString(),
                    etValue2.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair(etKey3.getText().toString(),
                    etValue3.getText().toString()));

            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = client.execute(post);
            readStream(response.getEntity().getContent());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void readStream(InputStream is){

        int ch;
        StringBuilder sb = new StringBuilder();
        try{while((ch = is.read())!= -1)
            sb.append((char)ch);}
        catch (IOException e){
            e.printStackTrace();
        }

        tvContent.setText(sb.toString());


    }
}
