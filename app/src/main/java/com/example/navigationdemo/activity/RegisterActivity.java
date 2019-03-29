package com.example.navigationdemo.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import com.example.navigationdemo.Pojo.User;
import com.example.navigationdemo.R;
import com.example.navigationdemo.Utils.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class RegisterActivity extends AppCompatActivity {
    Button submit;
    EditText Username,PhoneNumber,Email,Password,Confirmpassword,Address;
    FirebaseDatabase instance;
    DatabaseReference reference,city,state,country,area;
    String addressname;
    String cityid;
    String stateid;
    String countryid;
    String Areakey;

    String userName = "";
    String email = "";
    String urlUpload = "http://cas.mindhackers.org/vehicle-service-booking/public/api/userregisteration";
    String pass = "";
    String phone = "";
    String address = "";
    String instanceID = "";
    Double latitude = 0.00;
    Double longitude = 0.00;

    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        submit=(Button)findViewById(R.id.btnSubmit);
        Username=(EditText)findViewById(R.id.etxtUserName);
        PhoneNumber=(EditText)findViewById(R.id.etxtPhoneNumber);
        Email=(EditText)findViewById(R.id.etxtEmail);
        Password=(EditText)findViewById(R.id.etxtPassword);
        Confirmpassword=(EditText)findViewById(R.id.etxtConfirmpassword);
        Address=(EditText)findViewById(R.id.etxtAddress);

       // handleSSLHandshake();

//        if(getSupportActionBar()!=null){
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
        sessionManager = new SessionManager(RegisterActivity.this);
        instanceID =  sessionManager.getId();





        setTitle("Register");
        instance=FirebaseDatabase.getInstance();
        city=instance.getReference("City");
        state=instance.getReference("State");
        country=instance.getReference("Country");
        area=instance.getReference("Area");
        reference=instance.getReference("UserDetails");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Username.getText().toString().isEmpty()){
                    Username.setError("Enter User Name");
                }else if(Email.getText().toString().isEmpty()){
                    Email.setError("Enter Email Address");
                }else if(!isValidEmail(Email.getText().toString())){
                    Email.setError("Enter Valid Email Address");
                }else if(PhoneNumber.getText().toString().isEmpty()){
                    PhoneNumber.setError("Enter Phone Number");
                }
                else if(PhoneNumber.getText().toString().length()<10||PhoneNumber.getText().toString().length()>10){
                    PhoneNumber.setError("Enter Valid Phone Number");
                }else if(!isValidPhonenumber(PhoneNumber.getText().toString())){
                    PhoneNumber.setError("Enter Valid PhoneNumber");
                }
                else if(!isValidPassword(Password.getText().toString())){
                    Password.setError("Enter PassWord");
                }else if(Confirmpassword.getText().toString().isEmpty()){
                    Confirmpassword.setError("ReEnter Password");
                }
                else if(!isValidConfirmPassword(Confirmpassword.getText().toString())){
                    Confirmpassword.setError("Password Should Match With Above PassWord");
                    Password.setText("");
                }
                else if(!isValidAddress(Address.getText().toString())){
                    Address.setError("Enter Proper Address");
                    Address.setText("");
                }
                else{
//                    reference.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            boolean isExists=false;
//                            for (DataSnapshot snapshot:dataSnapshot.getChildren()){
//                                if(Email.getText().toString().equalsIgnoreCase(snapshot.child("email").getValue().toString())){
//                                    isExists=true;
//                                    Toast.makeText(RegisterActivity.this, "Email Exists", Toast.LENGTH_SHORT).show();
//
//                                }
//
//                            }
//                            if(!isExists)
                                setUserdata();
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });

                }
            }
        });


    }

    private boolean isValidAddress(String addressname) {

        if(addressname!=null && !addressname.isEmpty()){
            Geocoder geocoder=new Geocoder(RegisterActivity.this);
            try{
                List<Address> addresses = geocoder.getFromLocationName(addressname, 1);
                if(addresses!=null){

                    latitude = addresses.get(0).getLatitude();
                    longitude = addresses.get(0).getLongitude();

                    final String areaname1=addresses.get(0).getAddressLine(0);
                    final String city1=addresses.get(0).getLocality();
                    final String state1=addresses.get(0).getAdminArea();
                    final String country1=addresses.get(0).getCountryName();

//           Toast.makeText(RegisterActivity.this, "Latitude"+location.getLatitude()+"Longtitude"+location.getLongitude(), Toast.LENGTH_SHORT).show();
                    city.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(final DataSnapshot snapshot:dataSnapshot.getChildren()){
                                if(snapshot.child("city").getValue().toString().equalsIgnoreCase(city1)){
                                    cityid=snapshot.getKey();
                                    state.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for(DataSnapshot snapshot1:dataSnapshot.getChildren()){
                                                if(snapshot1.child("state").getValue().toString().equalsIgnoreCase(state1)){
                                                    stateid=snapshot1.getKey();
                                                    country.addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            for(DataSnapshot snapshot2:dataSnapshot.getChildren()){
                                                                if(snapshot2.child("country").getValue().toString().equalsIgnoreCase(country1)){
                                                                    countryid=snapshot2.getKey();
                                                                     Areakey=area.push().getKey();
                                                                    Area a=new Area(Address.getText().toString(),areaname1,cityid,stateid,countryid);
                                                                    area.child(Areakey).setValue(a);
                                                                }
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    });
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    return true;
                }
                else {
                    Toast.makeText(RegisterActivity.this, "address is null", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            catch (Exception e){
                Toast.makeText(RegisterActivity.this, ""+e, Toast.LENGTH_SHORT).show();
            }
        return true;
        }
        else {
            return false;
        }
    }

    public void setUserdata() {
        uploadData();
        String key=reference.push().getKey();
        userName = Username.getText().toString();
        pass = Password.getText().toString();
        email = Email.getText().toString();
        phone = PhoneNumber.getText().toString();

        User user=new User(userName,phone ,email,
                pass,"","","","","",Areakey);

        reference.child(key).setValue(user);
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(Email.getText().toString(),Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Toast.makeText(RegisterActivity.this, "Authentication done", Toast.LENGTH_SHORT).show();
            }
        });

        reference.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User d = dataSnapshot.getValue(User.class);
                if (d != null)
                    Log.e("Status", d.username);
//                Intent i=new Intent(RegisterActivity.this,LoginActivity.class);
//                startActivity(i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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

    public void uploadData(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpload, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contentEquals("error_email_exist")){
                    Toast.makeText(RegisterActivity.this, "Email Exists", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(getApplicationContext(), response + "Registration successful!", Toast.LENGTH_LONG).show();
                    sessionManager.putPerAddress(String.valueOf(latitude),String.valueOf(longitude));
                    Intent i=new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(i);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Registration unsuccesful" + " " + error , Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", userName);
                params.put("email",email);
                params.put("phone", phone);
                params.put("password", pass);
                params.put("instance_id", instanceID);
                params.put("latitude",String.valueOf(latitude));
                params.put("longitude",String.valueOf(longitude));
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    @SuppressLint("TrulyRandom")
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }

}
