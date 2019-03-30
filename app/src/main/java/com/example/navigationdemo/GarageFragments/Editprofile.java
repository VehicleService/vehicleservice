package com.example.navigationdemo.GarageFragments;


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
import com.example.navigationdemo.GarageActivity.RegisterActivityG;
import com.example.navigationdemo.Importantclasses.MySingleton;
import com.example.navigationdemo.R;
import com.example.navigationdemo.Utils.SessionManager1;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class Editprofile extends Fragment {

    Button submit;
    EditText Username,PhoneNumber,Email,Password,Confirmpassword;


    HashMap<String,String> data;

    SessionManager1 sessionManager1;


    String uploadURL = "http://cas.mindhackers.org/vehicle-service-booking/public/api/userupdateprofile";




    public Editprofile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_editprofile, container, false);
        submit=(Button)v.findViewById(R.id.btnSubmit);
        Username=(EditText)v.findViewById(R.id.etxtUserName);
        PhoneNumber=(EditText)v.findViewById(R.id.etxtPhoneNumber);
        Email=(EditText)v.findViewById(R.id.etxtEmail);


        sessionManager1=new SessionManager1(getContext());
        data=sessionManager1.getapidata();

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
        Log.d("data",data.get("id")+Username.getText().toString()+PhoneNumber.getText().toString()+Email.getText().toString());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,uploadURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("response",response);
                Toast.makeText(getContext(),  "Profile Updated", Toast.LENGTH_LONG).show();



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Profile Updation unsuccesful" + " " + error , Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("garage_id",data.get("id"));
                params.put("name",Username.getText().toString());
                params.put("email", Email.getText().toString());
                params.put("phone",PhoneNumber.getText().toString());
                params.put("password", "");
                params.put("latitude","");
                params.put("longitude","");
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
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
