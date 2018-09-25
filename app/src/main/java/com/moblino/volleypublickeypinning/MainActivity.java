package com.moblino.volleypublickeypinning;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLSocketFactory;

public class MainActivity extends AppCompatActivity {

    private static final String SECURE_URL = "YOUT_SECURE_URL";
    public static final String PUBLIC_KEY = "SERVER_PUBLIC_KEY";
    public static final String TAG = "MainActivity";
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        requestQueue = Volley.newRequestQueue(this, new HurlStack(null, pinnedSSLSocketFactory()));
    }

    private void sendRequest() {
        StringRequest request = new StringRequest(Request.Method.GET, SECURE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "Response: " + response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Request failed: " + error.toString());
            }
        });
        requestQueue.add(request);
    }

    private SSLSocketFactory pinnedSSLSocketFactory() {
        try {
            return new TLSSocketFactory(PUBLIC_KEY);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void buttonClick(View view) {
        sendRequest();
    }
}
