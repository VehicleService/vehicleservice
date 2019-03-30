package com.example.navigationdemo.GarageActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.navigationdemo.BuildConfig;
import com.example.navigationdemo.R;
import com.example.navigationdemo.activity.PolylineActivity;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.DateFormat;
import java.util.Date;

public class TrackUserActivity extends FragmentActivity implements OnMapReadyCallback {


    String LastUpdateTime;
    final int RequestCheck = 100;

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
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 101;
    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_user);


        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        FusedLocationClient = LocationServices.getFusedLocationProviderClient(TrackUserActivity.this);
        SettingsClient = LocationServices.getSettingsClient(TrackUserActivity.this);
        LocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                CurrentLocation = locationResult.getLastLocation();

                LastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                UIupdate();
                // Toast.makeText(getActivity(), "Lat:"+CurrentLocation.getLatitude()+"Lon:"+CurrentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
            }
        };
        RequestingLocationUpdates = false;
//        LocationRequest.setPriority(com.google.android.gms.location.LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        LocationRequest = new LocationRequest();
        LocationRequest.setInterval(50000);
        com.google.android.gms.location.LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(LocationRequest);
        LocationSettingsRequest = builder.build();
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
        Dexter.withActivity(TrackUserActivity.this).withPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
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


    }

    private void startLocationUpdates() {
        SettingsClient.checkLocationSettings(LocationSettingsRequest)
                .addOnSuccessListener(TrackUserActivity.this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Toast.makeText(TrackUserActivity.this, "Start Location Update", Toast.LENGTH_SHORT).show();
                        //noinspection permissioncheck
                        FusedLocationClient.requestLocationUpdates(LocationRequest, LocationCallback, Looper.myLooper());
                        // Toast.makeText(getActivity(), "Lat:"+CurrentLocation.getLatitude()+"Lon:"+CurrentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                        UIupdate();
                    }
                }).addOnFailureListener(TrackUserActivity.this, new OnFailureListener() {
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
                            rae.startResolutionForResult(TrackUserActivity.this, RequestCheck);
                        } catch (IntentSender.SendIntentException sie) {
                            Toast.makeText(TrackUserActivity.this, "PendingIntent unable to execute request", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Toast.makeText(TrackUserActivity.this, "Location settings are inadequate, and cannot be \" +\n" +
                                "fixed here. Fix in Settings.\";", Toast.LENGTH_SHORT).show();
                }
                // Toast.makeText(getActivity(), "Lat:"+CurrentLocation.getLatitude()+"Lon:"+CurrentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                UIupdate();
            }
        });


    }

    private void UIupdate() {
        if (CurrentLocation != null)

        {
            // latlan.setText("Lat"+CurrentLocation.getLatitude()+"Lon"+CurrentLocation.getLongitude());
            lat = CurrentLocation.getLatitude();
            lan = CurrentLocation.getLongitude();
//            reference.child(key).child("lat").setValue(lat);
//            reference.child(key).child("lon").setValue(lan);
            LatLng user = new LatLng(lat, lan);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user, 12));
        }

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RequestCheck:
                switch (resultCode) {
                    case Activity
                            .RESULT_OK:
                        Toast.makeText(TrackUserActivity.this, "User agreed to make required location settings changes", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(TrackUserActivity.this, "User  not agreed to make required location settings changes", Toast.LENGTH_SHORT).show();
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
        int permissionState = ActivityCompat.checkSelfPermission(TrackUserActivity.this,
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
                .addOnCompleteListener(TrackUserActivity.this, new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(TrackUserActivity.this, "Location Update Stopped", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(TrackUserActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            setUpMap();

        } else {

            ActivityCompat.requestPermissions(TrackUserActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);

        }
    }
    private void setUpMap() {
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setUpMap();
                }
        }
    }
}

