package com.example.navigationdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        vehicleowner=(Button)findViewById(R.id.btnVehicleOwner);
        garageowner=(Button)findViewById(R.id.btnGarageOwner);
        vehicleowner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DirectionActivity.this,LoginActivity.class);
                startActivity(i);

            }
        });
        garageowner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DirectionActivity.this,LoginActivityG.class);
                startActivity(i);
            }
        });

    }
}
