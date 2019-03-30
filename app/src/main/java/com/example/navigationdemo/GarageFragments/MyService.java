package com.example.navigationdemo.GarageFragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.navigationdemo.GarageActivity.Main2Activity;
import com.example.navigationdemo.Importantclasses.MySingleton;
import com.example.navigationdemo.R;
import com.example.navigationdemo.Utils.SessionManager1;
import com.example.navigationdemo.activity.LoginActivity;
import com.example.navigationdemo.activity.RegisterActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyService extends Fragment {

    EditText tworeg,fourreg,twoeme,foureme;
    Button ok,cancel,submit;
    RadioGroup serviceg,vehicleg;
    RelativeLayout service,cost;
    RadioButton servicer,vehicelr;
    String urlUpload="http://cas.mindhackers.org/vehicle-service-booking/public/api/registerservice";
    int i,j;
    String service_id,vehicle_id,two_regular,four_regular,two_eme,four_eme;
    HashMap<String,String> data=new HashMap<>();
    SessionManager1 sessionManager1;
    public MyService() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.fragment_my, container,false);
        tworeg=(EditText) view.findViewById(R.id.etxttr);
        twoeme=(EditText)view.findViewById(R.id.etxttef);
        fourreg=(EditText)view.findViewById(R.id.etxtfr);
        foureme=(EditText)view.findViewById(R.id.etxtfef);
        serviceg=(RadioGroup)view.findViewById(R.id.rgServiceType);
        vehicleg=(RadioGroup)view.findViewById(R.id.rgVehicleType);
        service=(RelativeLayout)view.findViewById(R.id.relativeSelect);
        cost=(RelativeLayout)view.findViewById(R.id.relativeCost);
        ok=(Button)view.findViewById(R.id.btnOk);
        cancel=(Button)view.findViewById(R.id.btnback);
        submit=(Button)view.findViewById(R.id.btnSubmit);
        sessionManager1=new SessionManager1(getContext());
        data=sessionManager1.getapidata();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=serviceg.getCheckedRadioButtonId();
                j=vehicleg.getCheckedRadioButtonId();
                if (i==-1||j==-1){
                    Toast.makeText(getActivity(), "Select Types", Toast.LENGTH_SHORT).show();
                }
                else {
                    servicer=(RadioButton)service.findViewById(i);
                    vehicelr=(RadioButton)service.findViewById(j);
                    if (servicer.getText().toString().equalsIgnoreCase("Regular")){
                        service_id=data.get("regular");
                    }
                    else if (servicer.getText().toString().equalsIgnoreCase("Emergency")){
                        service_id=data.get("emergency");
                    }
                    else if (servicer.getText().toString().equalsIgnoreCase("Both")){
                        service_id=data.get("boths");
                    }
                    if (vehicelr.getText().toString().equalsIgnoreCase("TwoWheeler")){
                        vehicle_id=data.get("two-wheeler");
                    }
                    else if (vehicelr.getText().toString().equalsIgnoreCase("FourWheeler")){
                        vehicle_id=data.get("four-wheeler");
                    }
                    else if (vehicelr.getText().toString().equalsIgnoreCase("Both")){
                        vehicle_id=data.get("bothv");
                    }
                   // Toast.makeText(getActivity(), service_id+""+vehicle_id, Toast.LENGTH_SHORT).show();
                    service.setVisibility(View.INVISIBLE);
                    cost.setVisibility(View.VISIBLE);
                }
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("reached","yes");
                four_eme=foureme.getText().toString();
               four_regular=fourreg.getText().toString();
               two_regular=tworeg.getText().toString();
                two_eme=twoeme.getText().toString();
                if (vehicle_id.equalsIgnoreCase("1") && service_id.equalsIgnoreCase("1")){
                    Log.d("reach","yes");
                    if (two_regular.isEmpty()) {
                        Toast.makeText(getActivity(), "Enter Two-Wheeler Regular Cost", Toast.LENGTH_SHORT).show();
                        Log.d("reach","no");
                    }
                    else {
                        Log.d("reach","obvious");
                        uploadData();
                    }
                }
                else if (vehicle_id.equalsIgnoreCase("1") && service_id.equalsIgnoreCase("2")){
                    if (two_eme.isEmpty()){
                        Toast.makeText(getActivity(), "Enter Two-Wheeler Emergency Cost", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        uploadData();
                    }
                }
                else if (vehicle_id.equalsIgnoreCase("1") && service_id.equalsIgnoreCase("3")){
                    if (two_regular.isEmpty() && two_eme.isEmpty()){
                        Toast.makeText(getActivity(), "Enter Two-Wheeler Costs", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        uploadData();
                    }
                }
                else if (vehicle_id.equalsIgnoreCase("2") && service_id.equalsIgnoreCase("1")){
                    if (four_regular.isEmpty()){
                        Toast.makeText(getActivity(), "Enter Four-Wheeler Regular Cost", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        uploadData();
                    }
                }
                else if (vehicle_id.equalsIgnoreCase("2") && service_id.equalsIgnoreCase("2")){
                    if (four_eme.isEmpty()){
                        Toast.makeText(getActivity(), "Enter Four-Wheeler Emergency Cost", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        uploadData();
                    }
                }
                else if (vehicle_id.equalsIgnoreCase("2") && service_id.equalsIgnoreCase("3")){
                    if (four_regular.isEmpty()&&four_eme.isEmpty()){
                        Toast.makeText(getActivity(), "Enter Four-Wheeler Costs", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (vehicle_id.equalsIgnoreCase("3") && service_id.equalsIgnoreCase("1")){
                    if (two_regular.isEmpty()&&four_regular.isEmpty()){
                        Toast.makeText(getActivity(), "Enter Regular Costs", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        uploadData();
                    }
                }
                else if (vehicle_id.equalsIgnoreCase("3") && service_id.equalsIgnoreCase("2")){
                    if (two_eme.isEmpty()&&four_eme.isEmpty()){
                        Toast.makeText(getActivity(), "Enter Emergency Costs", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        uploadData();
                    }
                }
                else if (vehicle_id.equalsIgnoreCase("3") && service_id.equalsIgnoreCase("3")){
                    if (two_regular.isEmpty()&&two_eme.isEmpty()&&
                            four_regular.isEmpty()&&four_eme.isEmpty()){
                        Toast.makeText(getActivity(), "Enter Costs", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        uploadData();
                    }
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cost.setVisibility(View.INVISIBLE);
                service.setVisibility(View.VISIBLE);
            }
        });

//        HashMap<String, String> params = new HashMap<>();
//        params.put("garage_id","id");
//        params.put("service_id","service_id");
//        params.put("vehicle_id","vehicle_id");
//        params.put("two_regular","two_cost");
//        params.put("four_regular","four_cost");
//        params.put("two_eme","two_eme");
//        params.put("four_eme","four_eme");
//        return params;

        // Inflate the layout for this fragment
        return view;
    }

    private void uploadData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpload, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response",response);
              //  Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                Intent i=new Intent(getContext(),Main2Activity.class);
                startActivity(i);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Registration unsuccesful" + " " + error , Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected HashMap<String, String> getParams() throws AuthFailureError {

        HashMap<String, String> params = new HashMap<>();
        params.put("garage_id",data.get("id"));
        params.put("service_id",service_id);
        params.put("vehicle_id",vehicle_id);
        params.put("two_regular",two_regular);
        params.put("four_regular",four_regular);
        params.put("two_eme",two_eme);
        params.put("four_eme",four_eme);
        return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }


}
