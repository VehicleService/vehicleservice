package com.example.navigationdemo.Fragments;


import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.navigationdemo.Pojo.Area;
import com.example.navigationdemo.R;
import com.example.navigationdemo.Utils.SessionManager;
import com.example.navigationdemo.activity.RegisterActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class Editprofileuser extends Fragment {

    Button submit;
    EditText Username,PhoneNumber,Email,Password,Confirmpassword,Address;
    FirebaseDatabase instance;
    DatabaseReference reference;
    SessionManager sessionManager;

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
        Password=(EditText)v.findViewById(R.id.etxtPassword);
        Confirmpassword=(EditText)v.findViewById(R.id.etxtConfirmpassword);
        Address=(EditText)v.findViewById(R.id.etxtAddress);
        instance=FirebaseDatabase.getInstance();
        reference=instance.getReference("GarageDetails");

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
                else if (!isValidPassword(Password.getText().toString())) {
                    Password.setError("Enter PassWord");
                } else if (Confirmpassword.getText().toString().isEmpty()) {
                    Confirmpassword.setError("ReEnter Password");
                } else if (!isValidConfirmPassword(Confirmpassword.getText().toString())) {
                    Confirmpassword.setError("Password Should Match With Above PassWord");
                    Password.setText("");
                } else if(!isValidAddress(Address.getText().toString())){
                    Address.setError("Enter Proper Address");
                    Address.setText("");
                }
                else {
                    //save data using api
                }
            }
        });
        // Inflate the layout for this fragment
        return v;
    }

    private boolean isValidAddress(String addressname) {

        if(addressname!=null && !addressname.isEmpty()) {
            Geocoder geocoder = new Geocoder(getActivity());
            try {
                List<android.location.Address> addresses = geocoder.getFromLocationName(addressname, 1);
                if (addresses != null) {

                    final Double Latitude = addresses.get(0).getLatitude();
                    final Double Longitude = addresses.get(0).getLongitude();
                    final String areaname1 = addresses.get(0).getAddressLine(0);
                    final String city1 = addresses.get(0).getLocality();
                    final String state1 = addresses.get(0).getAdminArea();
                    final String country1 = addresses.get(0).getCountryName();

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
    public  boolean isValidPassword(String s2){
        if(s2!=null &&s2.length()>6){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean isValidConfirmPassword(String s){
        String s1=Password.getText().toString();
        if(s1.equalsIgnoreCase(s)){return  true;}
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
