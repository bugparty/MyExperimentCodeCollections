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
import com.example.network.lib.WebApi;
import com.example.network.model.BRCookie;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.json.JSONObject;

public class HttpPostExample2 extends Activity {
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

    private void load(String urlStr) {
        WebApi webApi = new WebApi(getApplicationContext(), etValue3.getText().toString());
        JSONObject wan = webApi.getSystemInfo();
        Log.d(TAG, "URI builded "+wan.toString());
        tvContent.setText(wan.toString());
    }


}
