package com.example.navigationdemo.GarageActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.navigationdemo.BuildConfig;
import com.example.navigationdemo.Importantclasses.MySingleton;
import com.example.navigationdemo.Pojo.Garage;
import com.example.navigationdemo.R;
import com.example.navigationdemo.Utils.SessionManager1;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivityG extends AppCompatActivity {


    Button submit;
    EditText Username,PhoneNumber,Email,Password,Confirmpassword,Gumastano;
    FirebaseDatabase instance;
    DatabaseReference reference;

    String LastUpdateTime;
    final int RequestCheck = 100;
    // bunch of location related apis
    FusedLocationProviderClient FusedLocationClient;
    com.google.android.gms.location.SettingsClient SettingsClient;
    com.google.android.gms.location.LocationRequest LocationRequest;
    com.google.android.gms.location.LocationSettingsRequest LocationSettingsRequest;
    com.google.android.gms.location.LocationCallback LocationCallback;
    Location CurrentLocation;
    // EditText latlan;
    // boolean flag to toggle the ui
    private boolean RequestingLocationUpdates;
    String uploadURL = "http://cas.mindhackers.org/vehicle-service-booking/public/api/garageregisteration";

    public double lat;
    public double lan;
    public List<Location> info;
    String key;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 101;



    String userName = "";
    String email = "";
    String pass = "";
    String phone = "";
    String instanceId="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_g);
        setTitle("Register");
//        if(getSupportActionBar()!=null){
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
        FusedLocationClient=LocationServices.getFusedLocationProviderClient(RegisterActivityG.this);
        SettingsClient=LocationServices.getSettingsClient(RegisterActivityG.this);
        LocationCallback=new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                CurrentLocation=locationResult.getLastLocation();
//                info=locationResult.getLocations();
//                    for (Location loc :
//                            info) {
//                        Log.d("data","Lat"+loc.getLatitude()+"Lan"+loc.getLongitude());
//                        Toast.makeText(RegisterActivityG.this, "Lat"+info.get(0)+"Lan"+info.get(0), Toast.LENGTH_SHORT).show();
//                    }

                if(CurrentLocation!=null){

                    lat=CurrentLocation.getLatitude();
                    lan=CurrentLocation.getLongitude();
                }

                LastUpdateTime=DateFormat.getTimeInstance().format(new Date());
                // UIupdate();
                // Toast.makeText(RegisterActivityG.this, "Lat:"+CurrentLocation.getLatitude()+"Lon:"+CurrentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
            }
        };
        RequestingLocationUpdates=false;
//        LocationRequest.setPriority(com.google.android.gms.location.LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        LocationRequest= LocationRequest.create();
        LocationRequest.setInterval(10000);
        LocationRequest.setFastestInterval(5000);
        LocationRequest.setFastestInterval(LocationRequest.PRIORITY_HIGH_ACCURACY);
        com.google.android.gms.location.LocationSettingsRequest.Builder builder=new LocationSettingsRequest.Builder();
        builder.addLocationRequest(LocationRequest);
        LocationSettingsRequest=builder.build();

        Dexter.withActivity(RegisterActivityG.this).withPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        RequestingLocationUpdates = true;
                        startLocationUpdates();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

        submit=(Button)findViewById(R.id.btnSubmit);
        Username=(EditText)findViewById(R.id.etxtUserName);
        PhoneNumber=(EditText)findViewById(R.id.etxtPhoneNumber);
        Email=(EditText)findViewById(R.id.etxtEmail);
        Password=(EditText)findViewById(R.id.etxtPassword);
        Confirmpassword=(EditText)findViewById(R.id.etxtConfirmpassword);
       // instance=FirebaseDatabase.getInstance();
        Gumastano=(EditText)findViewById(R.id.gumastano);
        //reference=instance.getReference("GarageDetails");

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                SessionManager1 sessionManager1=new SessionManager1(RegisterActivityG.this);
                instanceId=sessionManager1.getId();
                if (Username.getText().toString().isEmpty()) {
                    Username.setError("Enter User Name");
                } else if (Email.getText().toString().isEmpty()) {
                    Email.setError("Enter Email Address");
                } else if (!isValidEmail(Email.getText().toString())) {
                    Email.setError("Enter Valid Email Address");
                } else if (PhoneNumber.getText().toString().isEmpty()) {
                    PhoneNumber.setError("Enter Phone Number");
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
                    }
                    else if(Gumastano.getText().toString().length()!=12){
                    Gumastano.setError("Enter Valid Aadhar Card Number");
                    Gumastano.setText("");

                }
                        else {
//                        reference.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                boolean isExists = false;
//                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                    if (Email.getText().toString().equalsIgnoreCase(snapshot.child("email").getValue().toString())) {
//                                        isExists = true;
//                                        Toast.makeText(RegisterActivityG.this, "Email Exists", Toast.LENGTH_SHORT).show();
//
//                                    }
//
//                                }
//                                if (!isExists){
//
                                   // setUserdata();
                                    uploadData();
//
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
                    }
            }
        });
    }
    // public void UIupdate(){

    //   if(CurrentLocation!=null){
//            key=reference.push().getKey();
//            Garage user=new Garage(Username.getText().toString(),PhoneNumber.getText().toString(),Email.getText().toString(),Password.getText().toString(),"","");
//
//            reference.child(key).setValue(user);
//
//            Log.d("Success","Lat"+lat+"Lan"+lan);
//            reference.child(key).child("lat").setValue(lat);
//            reference.child(key).child("lon").setValue(lan);
//
//
//            reference.child(key).addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    Garage d = dataSnapshot.getValue(Garage.class);
//                    if (d != null)
//                        Log.e("Status", d.username);
//                    Intent i=new Intent(RegisterActivityG.this,LoginActivity1.class);
//                    startActivity(i);
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });

    //     }
    //}
//    public void setUserdata() {
//        key=reference.push().getKey();
//        Garage user=new Garage(Username.getText().toString(),PhoneNumber.getText().toString(),Email.getText().toString(),Password.getText().toString(),"","","",lat,lan);
//
//        reference.child(key).setValue(user);
//        FirebaseAuth.getInstance().createUserWithEmailAndPassword(Email.getText().toString(),Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//              //  Toast.makeText(RegisterActivityG.this, "Authentication done", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        Log.d("Success","Lat"+lat+"Lan"+lan);
//        //reference.child(key).child("lat").setValue(lat);
//        // reference.child(key).child("lon").setValue(lan);
//
//
//        reference.child(key).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Garage d = dataSnapshot.getValue(Garage.class);
//                if (d != null) {
//                    // Log.e("Status", d.username);
////                    Intent i=new Intent(RegisterActivityG.this,LoginActivityG.class);
////                    startActivity(i);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });



        // UIupdate();
//        Toast.makeText(RegisterActivityG.this, "Lat:"+CurrentLocation.getLatitude()+"Lon:"+CurrentLocation.getLongitude(), Toast.LENGTH_SHORT).show();

//        if (savedInstanceState != null) {
//
//            if (savedInstanceState.containsKey("is_requesting_updates")) {
//                RequestingLocationUpdates = savedInstanceState.getBoolean("is_requesting_updates");
//            }
//
//            if (savedInstanceState.containsKey("last_known_location")) {
//                CurrentLocation = savedInstanceState.getParcelable("last_known_location");
//            }
//
//            if (savedInstanceState.containsKey("last_updated_on")) {
//                LastUpdateTime = savedInstanceState.getString("last_updated_on");
//            }
//        }
//
//        public void onSaveInstanceState(Bundle savedInstanceState) {
//            super.onSaveInstanceState(savedInstanceState);
//            savedInstanceState.putBoolean("is_requesting_updates", RequestingLocationUpdates);
//            savedInstanceState.putParcelable("last_known_location", CurrentLocation);
//            savedInstanceState.putString("last_updated_on", LastUpdateTime);
//
//        }


    //}


    private void startLocationUpdates() {
        SettingsClient.checkLocationSettings(LocationSettingsRequest)
                .addOnSuccessListener(RegisterActivityG.this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        //Toast.makeText(RegisterActivityG.this, "Start Location Update", Toast.LENGTH_SHORT).show();
                        //noinspection permissioncheck

                        FusedLocationClient.requestLocationUpdates(LocationRequest, LocationCallback, null);
                        // Toast.makeText(RegisterActivityG.this, "Completed", Toast.LENGTH_SHORT).show();
                        // Toast.makeText(RegisterActivityG.this, "Lat:"+CurrentLocation.getLatitude()+"Lon:"+CurrentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                        // UIupdate();

                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                int statuscode = ((ApiException) e).getStatusCode();
                switch (statuscode) {
                    case LocationSettingsStatusCodes
                            .RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the
                            // result in onActivityResult().
                            ResolvableApiException rae = (ResolvableApiException) e;
                            rae.startResolutionForResult(RegisterActivityG.this, RequestCheck);
                        } catch (IntentSender.SendIntentException sie) {
                            Toast.makeText(RegisterActivityG.this, "PendingIntent unable to execute request", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Toast.makeText(RegisterActivityG.this, "Location settings are inadequate, and cannot be \" +\n" +
                                "fixed here. Fix in Settings.\";", Toast.LENGTH_SHORT).show();
                }
                // Toast.makeText(RegisterActivityG.this, "Lat:"+CurrentLocation.getLatitude()+"Lon:"+CurrentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                //  UIupdate();
            }
        });

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RequestCheck:
                switch (resultCode) {
                    case Activity
                            .RESULT_OK:
                       // Toast.makeText(RegisterActivityG.this, "User agreed to make required location settings changes", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                       // Toast.makeText(RegisterActivityG.this, "User  not agreed to make required location settings changes", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;

        }
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
    public void onResume() {
        super.onResume();
        if (RequestingLocationUpdates && checkPermissions()) {
            startLocationUpdates();
        }
        // UIupdate();
        //        Toast.makeText(RegisterActivityG.this, "Lat:"+CurrentLocation.getLatitude()+"Lon:"+CurrentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
    }

    public boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(RegisterActivityG.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    public void onPause() {
        super.onPause();
        if (RequestingLocationUpdates) {
            stopLoactionUpdates();
        }
    }

    private void stopLoactionUpdates() {
        FusedLocationClient.removeLocationUpdates(LocationCallback)
                .addOnCompleteListener(RegisterActivityG.this, new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                      //  Toast.makeText(RegisterActivityG.this, "Location Update Stopped", Toast.LENGTH_SHORT).show();

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

        userName = Username.getText().toString();
        email=Email.getText().toString();
        pass=Password.getText().toString();
        phone=PhoneNumber.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, uploadURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response",response);
                if (response.contentEquals("error_email_exist")){
                    Toast.makeText(RegisterActivityG.this, "Email Exists", Toast.LENGTH_SHORT).show();

                }
                else if(response.equals("INVALID_GARAGE_NO")){
                    Toast.makeText(RegisterActivityG.this, "Enter Valid Aadhar Card Number", Toast.LENGTH_SHORT).show();

                }
                else {
                   // Toast.makeText(getApplicationContext(), response + "Registration successful!", Toast.LENGTH_LONG).show();
                    Intent i=new Intent(RegisterActivityG.this,LoginActivityG.class);
                    startActivity(i);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivityG.this, "" + error, Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("name",userName);
                params.put("email",email);
                params.put("phone",phone);
                params.put("password",pass);
                params.put("latitude",String.valueOf(lat));
                params.put("longitude",String.valueOf(lan));
                params.put("instance_id",instanceId);
                params.put("garage_no","iaj1aXo3od80oGp4oAHy");
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

}

