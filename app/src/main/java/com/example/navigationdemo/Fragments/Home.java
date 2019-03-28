package com.example.navigationdemo.Fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navigationdemo.BuildConfig;
import com.example.navigationdemo.Pojo.Nearbygarages;
import com.example.navigationdemo.R;
import com.example.navigationdemo.Utils.SessionManager;
import com.example.navigationdemo.activity.BillActivity;
import com.example.navigationdemo.activity.MainActivity;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment implements OnMapReadyCallback {

    SessionManager sessionManager;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference,garageref,references,referencev;
    HashMap<String,String> data;
    String key,vkey,skey,vgkey,sgkey;
    String LastUpdateTime;
    final int RequestCheck = 100;



    ArrayList<LatLng> markers=new ArrayList<LatLng>();
    LatLng location;
    Geocoder geocoder;
    // bunch of location related apis
    FusedLocationProviderClient FusedLocationClient;
    com.google.android.gms.location.SettingsClient SettingsClient;
    com.google.android.gms.location.LocationRequest LocationRequest;
    com.google.android.gms.location.LocationSettingsRequest LocationSettingsRequest;
    com.google.android.gms.location.LocationCallback LocationCallback;
    Location CurrentLocation;
   // EditText latlan;
    // boolean flag to toggle the ui
    private Boolean RequestingLocationUpdates;

     public double lat;
    public double lan;
    ArrayList<Nearbygarages> nearbygarages=new ArrayList<>();
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 101;
    private GoogleMap mMap;
    Intent intent;


    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_home, container, false);
        Bundle bundle = getArguments();

//        ArrayList<Nearbygarages> nearbygarages= (ArrayList<Nearbygarages>)bundle.getSerializable("Details");
        nearbygarages=(ArrayList<Nearbygarages>)getActivity().getIntent().getSerializableExtra("Details");
        Log.d("near",nearbygarages.get(0).getLatitude()+nearbygarages.get(0).getLongitude());
        Log.d("size", String.valueOf(nearbygarages.size()));

        //Storing data in database
        firebaseDatabase=FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference("UserDetails");
        referencev=firebaseDatabase.getReference("VehicleType");
        references=firebaseDatabase.getReference("ServiceType");
        sessionManager=new SessionManager(getActivity());
        data=sessionManager.getData();
        key=data.get("Key");
        vkey=data.get("Vkey");
        skey=data.get("Skey");
        garageref=firebaseDatabase.getReference("GarageDetails");
        referencev.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    if(snapshot.child("type").getValue().toString().equalsIgnoreCase("Both")){
                        vgkey=snapshot.getKey();
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
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    if(snapshot.child("type").getValue().toString().equalsIgnoreCase("Both")){
                        sgkey=snapshot.getKey();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        garageref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()) {
                    if ((data.child("VehicleTypeId").getValue().toString().equalsIgnoreCase(vkey) || data.child("VehicleTypeId").getValue().toString().equalsIgnoreCase(vgkey)) &&
                            (data.child("ServiceTypeId").getValue().toString().equalsIgnoreCase(skey) || data.child("ServiceTypeId").getValue().toString().equalsIgnoreCase(sgkey))) {


                        Double lat = (Double) data.child("lat").getValue();
                        Double lon = (Double) data.child("lon").getValue();
                        Log.d("Loc", "lat" + lat + "Lon" + lon);
                        location = new LatLng(lat, lon);
                       // markers.add(location);
                      //  Log.d("loc", "" + markers.get(0));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


      //  latlan=(EditText) v.findViewById(R.id.txtLatLan);
        FusedLocationClient=LocationServices.getFusedLocationProviderClient(getActivity());
        SettingsClient=LocationServices.getSettingsClient(getActivity());
        LocationCallback=new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                CurrentLocation=locationResult.getLastLocation();

                LastUpdateTime=DateFormat.getTimeInstance().format(new Date());
                UIupdate();
               // Toast.makeText(getActivity(), "Lat:"+CurrentLocation.getLatitude()+"Lon:"+CurrentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
            }
        };
        RequestingLocationUpdates=false;
//        LocationRequest.setPriority(com.google.android.gms.location.LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        LocationRequest=new LocationRequest();
        LocationRequest.setInterval(50000);
        com.google.android.gms.location.LocationSettingsRequest.Builder builder=new LocationSettingsRequest.Builder();
        builder.addLocationRequest(LocationRequest);
        LocationSettingsRequest=builder.build();
          UIupdate();
//        Toast.makeText(getActivity(), "Lat:"+CurrentLocation.getLatitude()+"Lon:"+CurrentLocation.getLongitude(), Toast.LENGTH_SHORT).show();

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
        Dexter.withActivity(getActivity()).withPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
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






        return v;
    }


    private void startLocationUpdates() {
        SettingsClient.checkLocationSettings(LocationSettingsRequest)
                .addOnSuccessListener(getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Toast.makeText(getActivity(), "Start Location Update", Toast.LENGTH_SHORT).show();
                        //noinspection permissioncheck
                        FusedLocationClient.requestLocationUpdates(LocationRequest, LocationCallback, Looper.myLooper());
                       // Toast.makeText(getActivity(), "Lat:"+CurrentLocation.getLatitude()+"Lon:"+CurrentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                        UIupdate();
                    }
                }).addOnFailureListener(getActivity(), new OnFailureListener() {
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
                            rae.startResolutionForResult(getActivity(), RequestCheck);
                        } catch (IntentSender.SendIntentException sie) {
                            Toast.makeText(getActivity(), "PendingIntent unable to execute request", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Toast.makeText(getActivity(), "Location settings are inadequate, and cannot be \" +\n" +
                                                                        "fixed here. Fix in Settings.\";", Toast.LENGTH_SHORT).show();
                }
               // Toast.makeText(getActivity(), "Lat:"+CurrentLocation.getLatitude()+"Lon:"+CurrentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                UIupdate();
            }
        });

    }
   public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RequestCheck:
                switch (resultCode) {
                    case Activity
                            .RESULT_OK:
                        Toast.makeText(getActivity(), "User agreed to make required location settings changes", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getActivity(), "User  not agreed to make required location settings changes", Toast.LENGTH_SHORT).show();
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
//        UIupdate();
    //        Toast.makeText(getActivity(), "Lat:"+CurrentLocation.getLatitude()+"Lon:"+CurrentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
    }

    public boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION);
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
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getActivity(), "Location Update Stopped", Toast.LENGTH_SHORT).show();

                    }
                });
    }
    public void UIupdate(){
        if(CurrentLocation!=null){
            // latlan.setText("Lat"+CurrentLocation.getLatitude()+"Lon"+CurrentLocation.getLongitude());
            lat=CurrentLocation.getLatitude();
            lan=CurrentLocation.getLongitude();
//            reference.child(key).child("lat").setValue(lat);
//            reference.child(key).child("lon").setValue(lan);
            LatLng user=new LatLng(lat,lan);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user,12));
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    try {
                        geocoder=new Geocoder(getActivity(), Locale.getDefault());
                        intent=new Intent(getActivity(),BillActivity.class);
                        List<Address>  address=geocoder.getFromLocation(marker.getPosition().latitude,marker.getPosition().longitude,1);
                        Log.d("Address",address.get(0).getAddressLine(0));
                        final String Address="Address"+address.get(0).getAddressLine(0)+"\n"+" City"+address.get(0).getLocality()+
                                "\n"+" State"+address.get(0).getAdminArea()+"\n"+" Country"+address.get(0).getCountryName()+
                                "\n"+" PostalCode"+address.get(0).getPostalCode()+"\n"+" KnownName"+address.get(0).getFeatureName();
                        marker.setSnippet(Address);
                        for (int i=0;i<nearbygarages.size();i++){
                            if(nearbygarages.get(i).getLatitude().equalsIgnoreCase(String.valueOf(marker.getPosition().latitude))&&
                                    nearbygarages.get(i).getLongitude().equalsIgnoreCase(String.valueOf(marker.getPosition().longitude))){
                                intent.putExtra("details",nearbygarages.get(i));
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    startActivity(intent);

                    return false;
                }
            });
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    LinearLayout info=new LinearLayout(getContext());
                    info.setOrientation(LinearLayout.VERTICAL);
                    TextView snippet=new TextView(getContext());
                    snippet.setText(marker.getSnippet());
                    info.addView(snippet);
                    return info;
                }
            });
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        for(int i=0;i<nearbygarages.size();i++){
            Double lat=Double.valueOf(nearbygarages.get(i).getLatitude());
            Double lon=Double.valueOf(nearbygarages.get(i).getLongitude());
            Log.d("nearby",lat+""+lon);


            location=new LatLng(lat,lon);
            markers.add(location);
            mMap.addMarker(new MarkerOptions().position(location).title("place"+i));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


//            double lat=CurrentLocation.getLatitude();
//            double lan=CurrentLocation.getLongitude();
//        Log.d("UserLocation",lat+""+lan);
//        LatLng user=new LatLng(lat,lan);
//
////            Log.d("Location:","Lat"+CurrentLocation.getLatitude()+"Lon"+CurrentLocation.getLongitude());
//        mMap.addMarker(new MarkerOptions().position(user).title("User location"));
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            setUpMap();

        } else {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);

        }


    }
    private void setUpMap() {
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setUpMap();
                }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }
}
