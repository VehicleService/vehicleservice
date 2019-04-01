package com.example.navigationdemo.Fragments;


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
import com.example.navigationdemo.Importantclasses.MySingleton;
import com.example.navigationdemo.Pojo.Historyresponse;
import com.example.navigationdemo.Pojo.UserHistory;
import com.example.navigationdemo.Pojo.UserRecent;
import com.example.navigationdemo.R;
import com.example.navigationdemo.Utils.SessionManager;
import com.example.navigationdemo.Utils.Userhistoryadapter;
import com.example.navigationdemo.Utils.Userrecentadapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Userrecent extends Fragment {
    
    RecyclerView recyclerView;
    List<UserHistory> history=new ArrayList<>();
    Userhistoryadapter adapter;

    String uploadUrl="http://cas.mindhackers.org/vehicle-service-booking/public/api/userhistory";

    Historyresponse historyresponse;

    SessionManager sessionManager;
    HashMap<String,String> id;
    public Userrecent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_userrecent, container, false);
        recyclerView=(RecyclerView)v.findViewById(R.id.recycleUserRecent);
        adapter=new Userhistoryadapter(history);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        //prepareData();
        sessionManager=new SessionManager(getActivity());
        id=sessionManager.getapidata();
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
                        Historyresponse historyresponse=new Historyresponse(String.valueOf(object.getInt("id")),String.valueOf("user_id"),
                                object.getString("vehicle_type"),object.getString("service_type"),object.getString("user_name"),
                                object.getString("user_phone"),object.getString("notification_type"),object.getString("created_at"),
                                object.getString("latitude"),object.getString("longitude"));
                        UserHistory h=new UserHistory("Vehicle Service Booking",historyresponse.getUsername(),historyresponse.getCreatedAt(),historyresponse.getServicetype()+"of"+
                                historyresponse.getVehicletype()+"is completed",historyresponse.getPhone(),String.valueOf(historyresponse.getLatitude()),String.valueOf(historyresponse.getLongitude()));
                        history.add(h);
//                        for (int i=1;i<data.length();i++){
//                            JSONObject jsonObject=data.getJSONObject(i);
//                            historyresponse=new Historyresponse(String.valueOf(jsonObject.getInt("id")),String.valueOf("user_id"),
//                                    jsonObject.getString("vehicle_type"),jsonObject.getString("service_type"),jsonObject.getString("user_name"),
//                                    jsonObject.getString("user_phone"),jsonObject.getString("notification_type"),jsonObject.getString("created_at"),
//                                    jsonObject.getString("latitude"),jsonObject.getString("longitude"));
//
//                            UserHistory h=new UserHistory("Vehicle Service Booking",historyresponse.getUsername(),historyresponse.getCreatedAt(),historyresponse.getServicetype()+"of"+
//                                    historyresponse.getVehicletype()+"is completed",historyresponse.getPhone(),String.valueOf(historyresponse.getLatitude()),String.valueOf(historyresponse.getLongitude()));
//                            history.add(h);
//
//                            // prepareData();
//                        }

                        recyclerView.setAdapter(adapter);
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
                params.put("user_id",id.get("id"));

                return params;
            }
        };
        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    private void prepareData() {
//        UserHistory h1=new UserHistory("h1","1","Arpita","Your vehicle is serviced by garage1");
//        history.add(h1);
//        UserHistory h2=new UserHistory("h2","1","Arpita1","Your vehicle is serviced by garage1");
//        history.add(h2);
//        UserHistory h3=new UserHistory("h3","1","Arpita2","Your vehicle is serviced by garage1");
//        history.add(h3);
//        UserHistory h4=new UserHistory("h4","1","Arpita3","Your vehicle is serviced by garage1");
//        history.add(h4);
//        UserHistory h5=new UserHistory("h5","1","Arpita4","Your vehicle is serviced by garage1");
//        history.add(h5);
//        UserHistory h6=new UserHistory("h6","1","Arpita5","Your vehicle is serviced by garage1");
//        history.add(h6);
//        UserHistory h7=new UserHistory("h7","1","Arpita6","Your vehicle is serviced by garage1");
//        history.add(h7);
//        UserHistory h8=new UserHistory("h8","1","Arpita7","Your vehicle is serviced by garage1");
//        history.add(h8);
//        UserHistory h9=new UserHistory("h9","1","Arpita8","Your vehicle is serviced by garage1");
//        history.add(h9);
//        UserHistory h10=new UserHistory("h10","1","Arpita9","Your vehicle is serviced by garage1");
//        history.add(h10);
    }

}
