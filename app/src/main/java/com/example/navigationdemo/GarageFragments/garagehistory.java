package com.example.navigationdemo.GarageFragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.navigationdemo.GarageActivity.LoginActivityG;
import com.example.navigationdemo.GarageActivity.RegisterActivityG;
import com.example.navigationdemo.Importantclasses.MySingleton;
import com.example.navigationdemo.Pojo.Historyresponse;
import com.example.navigationdemo.Pojo.UserRecent;
import com.example.navigationdemo.R;
import com.example.navigationdemo.Utils.SessionManager1;
import com.example.navigationdemo.Utils.Userrecentadapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class garagehistory extends Fragment {

    RecyclerView recyclerView;
    List<UserRecent> recent=new ArrayList<>();
    Userrecentadapter adapter;

    String uploadUrl="http://cas.mindhackers.org/vehicle-service-booking/public/api/garagehistory";

    HashMap<String,String> id;
    SessionManager1 sessionManager1;

    Historyresponse historyresponse;
    ArrayList<Historyresponse> history=new ArrayList<>();
    public garagehistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_garagehistory, container, false);

        sessionManager1=new SessionManager1(getActivity());
        id=sessionManager1.getapidata();
        Log.d("id",id.get("id"));


        recyclerView=(RecyclerView)v.findViewById(R.id.recycleGarageHistory);
       // uploadData();
        adapter=new Userrecentadapter(recent);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

      //  Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();

      // prepareData();
        uploadData();

        // Inflate the layout for this fragment
        return v;
    }

    private void uploadData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,uploadUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response",response);
                if (!response.equals(Integer.toString(-1))){
                    try {
                        JSONArray data=new JSONArray(response);
                        Log.d("data",data.toString());
                        JSONObject object=data.getJSONObject(0);
                        Historyresponse recentdata=new Historyresponse(String.valueOf(object.getInt("id")),String.valueOf("user_id"),
                                object.getString("vehicle_type"),object.getString("service_type"),object.getString("user_name"),
                                object.getString("user_phone"),object.getString("notification_type"),object.getString("created_at"),
                               object.getString("latitude"),object.getString("longitude"));
                        for (int i=1;i<data.length();i++){
                            JSONObject jsonObject=data.getJSONObject(i);
                             historyresponse=new Historyresponse(String.valueOf(jsonObject.getInt("id")),String.valueOf("user_id"),
                                    jsonObject.getString("vehicle_type"),jsonObject.getString("service_type"),jsonObject.getString("user_name"),
                                    jsonObject.getString("user_phone"),jsonObject.getString("notification_type"),jsonObject.getString("created_at"),
                                    jsonObject.getString("latitude"),jsonObject.getString("longitude"));
                            history.add(historyresponse);
                            UserRecent userRecent=new UserRecent("Vehicle Service Booking",historyresponse.getCreatedAt(),historyresponse.getUsername(),historyresponse.getServicetype()+" service of "+
                        historyresponse.getVehicletype()+" completed ",historyresponse.getPhone(),String.valueOf(historyresponse.getLatitude()),String.valueOf(historyresponse.getLongitude()));
                        recent.add(userRecent);
                          //  prepareData();

                        }
//                        UserRecent userRecent=new UserRecent("Vehicle Service Booking",historyresponse.getUsername(),historyresponse.getCreatedAt(),historyresponse.getServicetype()+"of"+
//                        historyresponse.getVehicletype()+"is completed",historyresponse.getPhone(),String.valueOf(historyresponse.getLatitude()),String.valueOf(historyresponse.getLongitude()));
////                        recent.add(userRecent);
//                        UserRecent user=new UserRecent("sndsakc","nskn","hdfvd","ddjkscnD","sdJN","scbJK","sbc");
//                        recent.add(user);
                        recyclerView.setAdapter(adapter);
//        Log.d("name",historyresponse.getUsername()+historyresponse.getCreatedAt()+historyresponse.getVehicletype()+historyresponse.getServicetype()
//        +historyresponse.getLatitude()+historyresponse.getLongitude());
                        Log.d("Trace","done");


                    } catch (Exception e) {
                       Log.d("Exception",e.getMessage());
                    }

                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "" + error, Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("garage_id",id.get("id"));

                return params;
            }
        };
        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);

    }

//    private void prepareData() {
//        uploadData();
//       // for (int i=0;i<history.size();i++){
////            Historyresponse historyresponse1=history.get(0);
////
////            UserRecent userRecent =new UserRecent("Vehicle Service Booking",historyresponse1.getUsername(),historyresponse1.getCreatedAt(),historyresponse1.getServicetype()+"of"+
////                    historyresponse1.getVehicletype()+"is completed",historyresponse1.getPhone(),String.valueOf(historyresponse1.getLatitude()),String.valueOf(historyresponse1.getLongitude()));
////            recent.add(userRecent);
//       // }
//
////        UserRecent userRecent=new UserRecent("Vehicle Service Booking",historyresponse.getUsername(),historyresponse.getCreatedAt(),historyresponse.getServicetype()+"of"+
////                historyresponse.getVehicletype()+"is completed",historyresponse.getPhone(),String.valueOf(historyresponse.getLatitude()),String.valueOf(historyresponse.getLongitude()));
////        recent.add(userRecent);
//
//        UserRecent user=new UserRecent("sndsakc","nskn","hdfvd","ddjkscnD","sdJN","scbJK","sbc");
//            recent.add(user);
////        Log.d("name",historyresponse.getUsername()+historyresponse.getCreatedAt()+historyresponse.getVehicletype()+historyresponse.getServicetype()
////        +historyresponse.getLatitude()+historyresponse.getLongitude());
//        Log.d("Trace","done");
////        UserRecent h1=new UserRecent("h1","1","Arpita","Your vehicle is serviced by garage1");
////        recent.add(h1);
////        UserRecent h2=new UserRecent("h2","1","Arpita1","Your vehicle is serviced by garage1");
////        recent.add(h2);
////        UserRecent h3=new UserRecent("h3","1","Arpita2","Your vehicle is serviced by garage1");
////        recent.add(h3);
////        UserRecent h4=new UserRecent("h4","1","Arpita3","Your vehicle is serviced by garage1");
////        recent.add(h4);
////        UserRecent h5=new UserRecent("h1","1","Arpita4","Your vehicle is serviced by garage1");
////        recent.add(h5);
////        UserRecent h6=new UserRecent("h1","1","Arpita5","Your vehicle is serviced by garage1");
////        recent.add(h6);
////        UserRecent h7=new UserRecent("h1","1","Arpita6","Your vehicle is serviced by garage1");
////        recent.add(h7);
////        UserRecent h8=new UserRecent("h1","1","Arpita7","Your vehicle is serviced by garage1");
////        recent.add(h8);
////        UserRecent h9=new UserRecent("h1","1","Arpita8","Your vehicle is serviced by garage1");
////        recent.add(h9);
////        UserRecent h10=new UserRecent("h1","1","Arpita9","Your vehicle is serviced by garage1");
////        recent.add(h10);
//    }

}
