package com.example.navigationdemo.Fragments;


import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.navigationdemo.Importantclasses.MySingleton;
import com.example.navigationdemo.Pojo.Area;
import com.example.navigationdemo.Pojo.Nearbygarages;
import com.example.navigationdemo.R;
import com.example.navigationdemo.Utils.SessionManager;
import com.example.navigationdemo.activity.LoginActivity;
import com.example.navigationdemo.activity.MainActivity;
import com.example.navigationdemo.activity.RegisterActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class Editprofileuser extends Fragment {

    Button submit;
    EditText Username,PhoneNumber,Email,Address;
    FirebaseDatabase instance;
    DatabaseReference reference;
    SessionManager sessionManager;
   Double Latitude,Longitude;
    ArrayList<Nearbygarages> garagelist;

    HashMap<String,String> profile;
   HashMap<String,String> data;

    String uploadURL = "http://cas.mindhackers.org/vehicle-service-booking/public/api/userupdateprofile";

    public Editprofileuser() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_editprofileuser, container, false);
        sessionManager=new SessionManager(getActivity());
        submit=(Button)v.findViewById(R.id.btnSubmit);
        Username=(EditText)v.findViewById(R.id.etxtUserName);
        PhoneNumber=(EditText)v.findViewById(R.id.etxtPhoneNumber);
        Email=(EditText)v.findViewById(R.id.etxtEmail);
        garagelist= (ArrayList<Nearbygarages>)getActivity().getIntent().getSerializableExtra("Details");
        Address=(EditText)v.findViewById(R.id.etxtAddress);

        data=sessionManager.getapidata();
        profile=sessionManager.getprofile();
        Username.setText(profile.get("name"));
        PhoneNumber.setText(profile.get("phone"));
        Email.setText(profile.get("Email"));
        Log.d("track","reach");
        Geocoder geocoder=new Geocoder(getActivity());
        try {
            List<Address> add=geocoder.getFromLocation(Double.valueOf(profile.get("lat")),Double.valueOf(profile.get("lon")),1);
            Address.setText(add.get(0).getAddressLine(0));
        } catch (Exception e) {
            Log.d("Exception",e.getMessage());
        }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidEmail(Email.getText().toString())) {
                    Email.setError("Enter Valid Email Address");
                } else if (PhoneNumber.getText().toString().length() < 10 || PhoneNumber.getText().toString().length() > 10) {
                    PhoneNumber.setError("Enter Valid Phone Number");
                }else if(!isValidPhonenumber(PhoneNumber.getText().toString())){
                    PhoneNumber.setError("Enter Valid PhoneNumber");
                }
               else if(!isValidAddress(Address.getText().toString())){
                    Address.setError("Enter Proper Address");
                    Address.setText("");
                }
                else {
                    //save data using api
                    uploadData();
                }
            }
        });
        // Inflate the layout for this fragment
        return v;
    }

    private void uploadData() {
        Log.d("data",data.get("id")+Username.getText().toString()+PhoneNumber.getText().toString()+Email.getText().toString()+
                String.valueOf(Latitude)+String.valueOf(Longitude));
        StringRequest stringRequest = new StringRequest(Request.Method.POST,uploadURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("response",response);
                Toast.makeText(getContext(),  "Profile Updated", Toast.LENGTH_LONG).show();
                sessionManager.setprofile(Username.getText().toString(),PhoneNumber.getText().toString()
                                ,Email.getText().toString(),String.valueOf(Latitude),String.valueOf(Longitude));
                Intent intent=new Intent(getContext(),MainActivity.class);
                intent.putExtra("Details",garagelist);
                getContext().startActivity(intent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Profile updation unsuccesful" + " " + error , Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id",data.get("id"));
                params.put("name",Username.getText().toString());
                params.put("email", Email.getText().toString());
                params.put("phone",PhoneNumber.getText().toString());
                params.put("password", "");
                params.put("latitude",String.valueOf(Latitude));
                params.put("longitude",String.valueOf(Longitude));
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    private boolean isValidAddress(String addressname) {

        if(addressname!=null && !addressname.isEmpty()) {
            Geocoder geocoder = new Geocoder(getActivity());
            try {
                List<android.location.Address> addresses = geocoder.getFromLocationName(addressname, 1);
                if (addresses != null) {

                     Latitude = addresses.get(0).getLatitude();
                     Longitude = addresses.get(0).getLongitude();
                     sessionManager.putPerAddress(String.valueOf(Latitude),String.valueOf(Longitude));


//           Toast.makeText(RegisterActivity.this, "Latitude"+location.getLatitude()+"Longtitude"+location.getLongitude(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
            }
            return true;
        }
        else{
            return false;
        }
    }


    public boolean isValidEmail(String s1){
        String Email_Pattern="^[A-Z a-z 0-9]+\\@+[A-Z a-z 0-9]+\\.+[A-Z a-z 0-9]{2,}$";
        Pattern p=Pattern.compile(Email_Pattern);
        Matcher m=p.matcher(s1);
        return m.matches();
    }
    public  boolean isValidPhonenumber(String s){
        return android.util.Patterns.PHONE.matcher(s).matches();
    }

}
