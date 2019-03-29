package com.example.navigationdemo.GarageActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.navigationdemo.Importantclasses.MySingleton;
import com.example.navigationdemo.Pojo.Notificationdetails;
import com.example.navigationdemo.R;
import com.example.navigationdemo.Utils.SessionManager1;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class UserdetailsActivity extends AppCompatActivity {

    TextView name,phone,servicetype,vehicletype,address;
    Button agree,disagree;
    String addresss;
    HashMap<String,String> data;
    SessionManager1 sessionManager1;
    Notificationdetails notification;
    String vehicle,service;
    String uploadurl="http://cas.mindhackers.org/vehicle-service-booking/public/api/usernotification";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetails);
        setTitle("UserDetails");
        notification= (Notificationdetails)getIntent().getSerializableExtra("notificationdetails");
        Log.d("notre",notification.getUserid()+notification.getName()+notification.getLatitude()+notification.getPhone()+notification.getLongitude()+notification.getServicetype()+notification.getVehicletype());
        Log.d("notres",String.valueOf(getIntent().getSerializableExtra("notificationdetails")));
        Log.d("notres1",notification.getName());
        name=(TextView)findViewById(R.id.etxtname);
        phone=(TextView)findViewById(R.id.etxtphone);
        servicetype=(TextView)findViewById(R.id.etxtservice);
        vehicletype=(TextView)findViewById(R.id.etxtvehicle);
        address=(TextView)findViewById(R.id.etxtadd);
        agree=(Button)findViewById(R.id.btnagree);
        disagree=(Button)findViewById(R.id.btndisagree);
        sessionManager1=new SessionManager1(UserdetailsActivity.this);
        data=sessionManager1.getapidata();
        Geocoder geocoder=new Geocoder(UserdetailsActivity.this,Locale.getDefault());
        try{
        List<Address> addresses=geocoder.getFromLocation(Double.valueOf(notification.getLatitude()),Double.valueOf(notification.getLongitude()),1);
        addresss=addresses.get(0).getAddressLine(0);
        }catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        name.setText(notification.getName());
        phone.setText(notification.getPhone());
        servicetype.setText(notification.getServicetype());
        vehicletype.setText(notification.getVehicletype());
        address.setText(addresss);
        if(notification.getVehicletype().equalsIgnoreCase("two-wheeler")){
            vehicle=data.get("two-wheeler");
        }else if (notification.getVehicletype().equalsIgnoreCase("four-wheeler")){
            vehicle=data.get("four-wheeler");
        }
        if (notification.getServicetype().equalsIgnoreCase("regular")){
            service=data.get("regular");
        }else if (notification.getServicetype().equalsIgnoreCase("emergency")){
            service=data.get("emergency");
        }
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accepted();
            }
        });
        disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                denied();
            }
        });

    }

    private void accepted() {
        Log.d("confirm",data.get("id")+vehicle+service+notification.getUserid());
        String Url=appendToUrl(uploadurl,getParams(1));
        StringRequest stringRequest=new StringRequest(Request.Method.GET,Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("data",response);
                Toast.makeText(UserdetailsActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("error",""+error.getMessage());
                Toast.makeText(UserdetailsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        Intent i=new Intent(UserdetailsActivity.this,Main2Activity.class);
        startActivity(i);
    }
    public HashMap<String, String> getParams(int a) {
        if(a==1){
        HashMap<String, String> params = new HashMap<>();
        params.put("garage_id",data.get("id"));
        params.put("user_id",notification.getUserid());
        params.put("vehicle_id",vehicle);
        params.put("service_id",service);
        params.put("notification_type_id",String.valueOf(2));
        params.put("latitude","");
        params.put("longitude","");
        return params;}
        else{ HashMap<String, String> params = new HashMap<>();
            params.put("garage_id",data.get("id"));
            params.put("user_id",notification.getUserid());
            params.put("vehicle_id",vehicle);
            params.put("service_id",service);
            params.put("notification_type_id",String.valueOf(3));
            params.put("latitude","");
            params.put("longitude","");
            return params;

        }
    }



    public static String appendToUrl(String url, HashMap<String, String> parameters) {
        try {
            URI uri = new URI(url);
            String query = uri.getQuery();
            StringBuilder builder = new StringBuilder();

            if (query != null)
                builder.append(query);

            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                String keyValueParam = entry.getKey() + "=" + entry.getValue();
                if (!builder.toString().isEmpty())
                    builder.append("&");

                builder.append(keyValueParam);
            }

            URI newUri = new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), builder.toString(), uri.getFragment());
            return newUri.toString();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return url;
    }

    private void denied(){
        String Url=appendToUrl(uploadurl,getParams(2));
        StringRequest stringRequest=new StringRequest(Request.Method.GET,Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("data",response);
                Toast.makeText(UserdetailsActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("error",""+error.getMessage());
                Toast.makeText(UserdetailsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        Intent i=new Intent(UserdetailsActivity.this,Main2Activity.class);
        startActivity(i);

    }



}
