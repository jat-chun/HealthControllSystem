package com.example.healthcontrollsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.squareup.okhttp.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OkHttpClient client = new OkHttpClient();
    }
}
