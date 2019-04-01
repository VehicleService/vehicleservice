package com.example.navigationdemo.activity;

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
import com.example.navigationdemo.Pojo.Nearbygarages;
import com.example.navigationdemo.R;
import com.example.navigationdemo.Utils.SessionManager;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BillActivity extends AppCompatActivity {

    TextView name,phone,address,estimate;
    String garageaddress;
    Button agree,disagree;
    SessionManager sessionManager;
    Nearbygarages nearbygarages;
    ArrayList<Nearbygarages> garagelist;
    HashMap<String,String> ids=new HashMap<>();
    HashMap<String,String> user=new HashMap<>();
    String upload="http://cas.mindhackers.org/vehicle-service-booking/public/api/notification";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        setTitle("Estimate");
        name=(TextView) findViewById(R.id.etxtname);
        phone=(TextView) findViewById(R.id.etxtphone);
        address=(TextView)findViewById(R.id.etxtadd);
        estimate=(TextView)findViewById(R.id.etxtcost);
        agree=(Button)findViewById(R.id.btnagree);
        disagree=(Button)findViewById(R.id.btndisagree);
        sessionManager=new SessionManager(BillActivity.this);
        ids=sessionManager.getserveh();
        user=sessionManager.getapidata();
         nearbygarages= (Nearbygarages) getIntent().getSerializableExtra("details");
        garagelist= (ArrayList<Nearbygarages>) getIntent().getSerializableExtra("Details");
        Geocoder geocoder=new Geocoder(BillActivity.this,Locale.getDefault());
        try {
            List<Address>  addresses=geocoder.getFromLocation(Double.valueOf(nearbygarages.getLatitude()),Double.valueOf(nearbygarages.getLongitude()),1);
            garageaddress=addresses.get(0).getAddressLine(0);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        name.setText(nearbygarages.getName());
        phone.setText(nearbygarages.getPhone());
        address.setText(garageaddress);
        estimate.setText(nearbygarages.getVisitcharge());

        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.setcost("false",nearbygarages.getVisitcharge(),nearbygarages.getId());
                uploaddata();


            }
        });
        disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(BillActivity.this,MainActivity.class);
                i.putExtra("Details",garagelist);
                startActivity(i);

            }
        });

    }

    private void uploaddata() {
        Log.d("request",nearbygarages.getId()+user.get("id")+ids.get("vehicle_id")+ids.get("service_id")
                +nearbygarages.getLongitude()+nearbygarages.getLatitude());
       //String Url=appendToUrl(upload,getParams());
        StringRequest stringRequest=new StringRequest(Request.Method.POST,upload, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("data",response);
                Intent i=new Intent(BillActivity.this,MainActivity.class);
                i.putExtra("Details",garagelist);
                startActivity(i);
              //  Toast.makeText(BillActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("error",""+error.getMessage());
                Toast.makeText(BillActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){ public HashMap<String, String> getParams() {
            HashMap<String, String> params = new HashMap<>();
            params.put("garage_id",nearbygarages.getId());
            params.put("user_id",user.get("id"));
            params.put("vehicle_id",ids.get("vehicle_id"));
            params.put("service_id",ids.get("service_id"));
            params.put("notification_type_id",String.valueOf(1));
            params.put("latitude",nearbygarages.getLatitude());
            params.put("longitude",nearbygarages.getLongitude());
            return params;
        }};
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }




//    public static String appendToUrl(String url, HashMap<String, String> parameters) {
//        try {
//            URI uri = new URI(url);
//            String query = uri.getQuery();
//            StringBuilder builder = new StringBuilder();
//
//            if (query != null)
//                builder.append(query);
//
//            for (Map.Entry<String, String> entry : parameters.entrySet()) {
//                String keyValueParam = entry.getKey() + "=" + entry.getValue();
//                if (!builder.toString().isEmpty())
//                    builder.append("&");
//
//                builder.append(keyValueParam);
//            }
//
//            URI newUri = new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), builder.toString(), uri.getFragment());
//            return newUri.toString();
//
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        return url;
//    }
}
