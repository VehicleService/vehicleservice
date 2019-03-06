package com.example.navigationdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.navigationdemo.R;

public class SplashActivity extends AppCompatActivity {

    public static int Time_out=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setTitle("Vehicle Service Booking");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(SplashActivity.this,DirectionActivity.class);
                startActivity(i);
            }
        },Time_out);

    }
}
