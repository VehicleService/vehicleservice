package com.example.navigationdemo.GarageActivity;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navigationdemo.Pojo.Notification;
import com.example.navigationdemo.Pojo.Notificationdetails;
import com.example.navigationdemo.R;

import java.util.List;
import java.util.Locale;

public class UserdetailsActivity extends AppCompatActivity {

    TextView name,phone,servicetype,vehicletype,address;
    Button agree,disagree;
    String addresss;
    Notificationdetails notification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetails);
        setTitle("UserDetails");
        notification= (Notificationdetails) getIntent().getSerializableExtra("notificationdetails");
//        Log.d("notre",notification.getName()+notification.getLatitude()+notification.getPhone()+notification.getLongitude()+notification.getServicetype()+notification.getVehicletype());
        Log.d("notres",String.valueOf(getIntent().getSerializableExtra("notificationdetails")));
        name=(TextView)findViewById(R.id.etxtname);
        phone=(TextView)findViewById(R.id.etxtphone);
        servicetype=(TextView)findViewById(R.id.etxtservice);
        vehicletype=(TextView)findViewById(R.id.etxtvehicle);
        address=(TextView)findViewById(R.id.etxtadd);
        agree=(Button)findViewById(R.id.btnagree);
        disagree=(Button)findViewById(R.id.btndisagree);
        Geocoder geocoder=new Geocoder(UserdetailsActivity.this,Locale.getDefault());
        try{
        List<Address> addresses=geocoder.getFromLocation(Double.valueOf(notification.getLatitude()),Double.valueOf(notification.getLongitude()),1);
        addresss=addresses.get(0).getAddressLine(0);
        }catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
//        name.setText(notification.getName());
//        phone.setText(notification.getPhone());
//        servicetype.setText(notification.getServicetype());
//        vehicletype.setText(notification.getVehicletype());
//        address.setText(addresss);

    }
}
