package com.example.navigationdemo.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navigationdemo.Importantclasses.DirectionsJsonParser;
import com.example.navigationdemo.R;
import com.example.navigationdemo.Utils.SessionManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PolylineActivity extends FragmentActivity implements OnMapReadyCallback {


    final int MY_PERMISSIONS_REQUEST_LOCATION=101;
    GoogleMap map;
    TextView tvDistanceDuration;
    List<LatLng> markerpoints;
    SessionManager sessionManager;
    LatLng point,point1;
    HashMap<String,String> locations=new HashMap<>();
    HashMap<String,String> perAddress=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polyline);





        sessionManager=new SessionManager(PolylineActivity.this);
        locations=sessionManager.getLocations();
        perAddress=sessionManager.getPerAddress();
        tvDistanceDuration=(TextView)findViewById(R.id.tv_distance_time);
        markerpoints=new ArrayList<LatLng>();
        SupportMapFragment fm=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        fm.getMapAsync((OnMapReadyCallback) this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        if (ContextCompat.checkSelfPermission(PolylineActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            setUpMap();

        } else {

            ActivityCompat.requestPermissions(PolylineActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);

        }
//        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng point) {
//                if(markerpoints.size()>1){
//                    markerpoints.clear();
//                    map.clear();
//                }
//                markerpoints.add(point);
//                MarkerOptions options=new MarkerOptions();
//                options.position(point);
//                if(markerpoints.size()==1){
//                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
//                }else if(markerpoints.size()==2){
//                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//                }
//                map.addMarker(options);
//                if (markerpoints.size()>=2){
//                    LatLng origin=markerpoints.get(0);
//                    LatLng dest=markerpoints.get(1);
//                    String url=getDirectionsUrl(origin,dest);
//                    DownloadTask downloadTask=new DownloadTask();
//                    downloadTask.execute(url);
//                }
//            }
//        });
        ArrayList<LatLng> points=new ArrayList<>();
        MarkerOptions options=new MarkerOptions();
        if(sessionManager.getService().containsValue("emergency")){
         point=new LatLng(Double.valueOf(locations.get("userlat")),Double.valueOf(locations.get("userlon")));
        point1=new LatLng(Double.valueOf(locations.get("garagelat")),Double.valueOf(locations.get("garagelon")));}
        else {
             point=new LatLng(23.082156,72.4909025);
             point1=new LatLng(Double.valueOf(locations.get("garagelat")),Double.valueOf(locations.get("garagelon")));
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(point,12));
        points.add(0,point);
        points.add(1,point1);
        for (int i=0;i<points.size();i++){
            markerpoints.add(points.get(i));
            options.position(points.get(i));
            map.addMarker(options);

        }


        if (markerpoints.size()>=2){
            LatLng origin=markerpoints.get(0);
            LatLng dest=markerpoints.get(1);
            String url=getDirectionsUrl(origin,dest);
            DownloadTask downloadTask=new DownloadTask();
            downloadTask.execute(url);
        }


    }
    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String key="key=AIzaSyAF9Tk-F3VVrJ6TQ-aozYlO3ZKK2ofhtkc";
        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor+"&"+key;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Error downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
    private void setUpMap() {
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
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

    private class DownloadTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... url) {

            String data="";
            try {
                data=downloadUrl(url[0]);
            } catch (IOException e) {
                Toast.makeText(PolylineActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ParserTask parserTask=new ParserTask();
            parserTask.execute(s);
        }
    }
    private class ParserTask extends AsyncTask<String,Integer,List<List<HashMap<String,String>>>>{

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String,String>>> routes=null;
            try {
                jObject=new JSONObject(jsonData[0]);
                DirectionsJsonParser parser=new DirectionsJsonParser();
                routes=parser.parse(jObject);
            } catch (JSONException e) {
                Toast.makeText(PolylineActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            Log.d("Result",result.toString());
            ArrayList<LatLng> points=null;
            PolylineOptions lineOptions=null;
            MarkerOptions markerOptions=new MarkerOptions();
            String distance="";
            String duration="";
            if (result.size()<1){
                Toast.makeText(PolylineActivity.this, "No Points", Toast.LENGTH_SHORT).show();
                return;
            }

            for(int i=0;i<result.size();i++){
                points=new ArrayList<LatLng>();
                lineOptions=new PolylineOptions();
                List<HashMap<String,String>> path=result.get(i);
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point=path.get(j);
                    if(j==0){
                        distance=(String)point.get("distance");

                    }else if(j==1){
                        duration=(String)point.get("duration");
                    }
                    else {
                        if(point!=null){
                            Double lat=Double.parseDouble(point.get("lat"));
                            Double lng=Double.parseDouble(point.get("lng"));
                            LatLng position=new LatLng(lat,lng);
                            points.add(position);}}
                }
                lineOptions.addAll(points);
                lineOptions.width(2);
                lineOptions.color(Color.RED);
            }
            tvDistanceDuration.setText("Distance"+distance+"Duration"+duration);
            map.addPolyline(lineOptions);
        }
    }
}
