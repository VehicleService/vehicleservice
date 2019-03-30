package com.example.navigationdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SelectionActivity extends AppCompatActivity {
    Button submit;
    RadioGroup vehicletype, serviceType;
    RadioButton vtype, stype;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference referencev, references, reference;
    int i, j;
    String vkey, skey;
    SessionManager sessionManager;
    HashMap<String, String> userdata;
    int service_id, vehicle_id;
    String uploadurl = "http://cas.mindhackers.org/vehicle-service-booking/public/api/nearbygarages";



    String garageid,servicetypeid,vehicletypeid,twowheelerregularcost,fourwheelerregularcost,twoemergencycostperkilometer,
            fourwheeleremergencycostperkilometer;




    //Api request data
    String userid, servicetype_id, vehicletype_id, session_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        setTitle("Select Vehicle And Service");
        submit = (Button) findViewById(R.id.btnSubmit);
        vehicletype = (RadioGroup) findViewById(R.id.rgVehicleType);
        serviceType = (RadioGroup) findViewById(R.id.rgServiceType);
        firebaseDatabase = FirebaseDatabase.getInstance();
        referencev = firebaseDatabase.getReference("VehicleType");
        references = firebaseDatabase.getReference("ServiceType");
        reference = firebaseDatabase.getReference("UserDetails");
        sessionManager = new SessionManager(SelectionActivity.this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = vehicletype.getCheckedRadioButtonId();
                j = serviceType.getCheckedRadioButtonId();
                if (i == -1 || j == -1) {
                    Toast.makeText(SelectionActivity.this, "Select any option", Toast.LENGTH_SHORT).show();
                } else {
                    vtype = (RadioButton) findViewById(i);
                    stype = (RadioButton) findViewById(j);
                    userdata = sessionManager.getapidata();
                    if (vtype.getText().toString().equalsIgnoreCase("TwoWheeler")) {
                        vehicle_id = Integer.valueOf(userdata.get("two-wheeler"));
                        sessionManager.setVehicle("two-wheeler");
                    } else if (vtype.getText().toString().equalsIgnoreCase("FourWheeler")) {
                        vehicle_id = Integer.valueOf(userdata.get("four-wheeler"));
                        sessionManager.setVehicle("two-wheeler");
                    } else {
                        Toast.makeText(SelectionActivity.this, "Select Vehicle Type", Toast.LENGTH_SHORT).show();
                    }
                    if (stype.getText().toString().equalsIgnoreCase("Regular")) {
                        service_id = Integer.valueOf(userdata.get("regular"));
                        sessionManager.setService("regular");
                    } else if (stype.getText().toString().equalsIgnoreCase("Emergency")) {
                        service_id = Integer.valueOf(userdata.get("emergency"));
                        sessionManager.setService("emergency");
                    } else {
                        Toast.makeText(SelectionActivity.this, "Select Service Type", Toast.LENGTH_SHORT).show();
                    }
                    sessionManager.setserveh(String.valueOf(service_id),String.valueOf(vehicle_id));
                    uploadData();
                    referencev.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                if (vtype.getText().toString().equalsIgnoreCase(snapshot.child("type").getValue().toString())) {
                                    vkey = snapshot.getKey();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    references.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                if (stype.getText().toString().equalsIgnoreCase(snapshot.child("type").getValue().toString())) {
                                    skey = snapshot.getKey();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    if (vkey != null && skey != null) {
                        Toast.makeText(SelectionActivity.this, "data" + vkey + "" + skey, Toast.LENGTH_SHORT).show();
                        sessionManager.putKeys(vkey, skey);
                        HashMap<String, String> data = sessionManager.getData();
                        String key = data.get("Key");
                      //  reference.child(key).child("vehicletypeId").setValue(vkey);
                       // reference.child(key).child("servicetypeId").setValue(skey);

                    }
                }
            }
        });

    }

    private void uploadData() {
        String Url = appendToUrl(uploadurl, getParams());
        Log.d("TAG", "uploadData: url: "+Url);
       // Toast.makeText(this, service_id + "" + vehicle_id, Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);
               // Toast.makeText(SelectionActivity.this,response, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SelectionActivity.this, MainActivity.class);
                if(!response.equals(Integer.toString(-1))){
                    try {


                        JSONArray data=new JSONArray(response);
                        Log.d("response1",data.toString());
                        ArrayList<Nearbygarages> values=new ArrayList<>();
                       // Toast.makeText(SelectionActivity.this, ""+data, Toast.LENGTH_LONG).show();
                        for (int i=0;i<data.length();i++) {
                            JSONObject object = data.getJSONObject(i);
                            Nearbygarages nearbygarages=new Nearbygarages(String.valueOf(object.getInt("id")),
                                    object.getString("name"),object.getString("phone"),object.getString("visting_charge")
                                    ,object.getString("latitude"),object.getString("longitude"));
                            values.add(nearbygarages);

                        }
                        Log.d("array",values.toString());
                        intent.putExtra("Details",values);
                        startActivity(intent);

                    } catch (Exception e) {
                        Toast.makeText(SelectionActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Log.d("nodata","not available");
                    Toast.makeText(SelectionActivity.this, " response not available", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.getMessage());
                Toast.makeText(SelectionActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public HashMap<String, String> getParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put("service_id", String.valueOf(service_id));
        params.put("vehicle_id", String.valueOf(vehicle_id));
        return params;
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
}
