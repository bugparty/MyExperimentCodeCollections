package com.example.network;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpGetExample extends Activity {
    /**
     * Called when the activity is first created.
     */
    private Button btLoad;
    private TextView tvContent;
    private EditText etUrl;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Disable network thread limit on activity_http_get_example thread
        StrictMode.ThreadPolicy policy = new StrictMode.
                ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_get_example);
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
    }
    private void load(String urlStr){
        URL url;
        try {
            url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) url
                    .openConnection();
            readStream(con.getInputStream());
        } catch (Exception e) {
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
