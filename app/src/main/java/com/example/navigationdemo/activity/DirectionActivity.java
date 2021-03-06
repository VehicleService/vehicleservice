package com.example.navigationdemo.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.example.navigationdemo.GarageActivity.LoginActivityG;
import com.example.navigationdemo.R;

public class DirectionActivity extends AppCompatActivity {
    Button vehicleowner,garageowner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);
        setTitle("Vehicle Service Booking");
        vehicleowner=(Button)findViewById(R.id.btnVehicleOwner);
        garageowner=(Button)findViewById(R.id.btnGarageOwner);
        vehicleowner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DirectionActivity.this,LoginActivity.class);
                startActivity(i);
                finish();

            }
        });
        garageowner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DirectionActivity.this,LoginActivityG.class);
                startActivity(i);
                finish();
            }
        });

    }

}
