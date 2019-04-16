package com.example.navigationdemo.GarageFragments;


import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.navigationdemo.GarageActivity.LoginActivityG;
import com.example.navigationdemo.GarageActivity.Main2Activity;
import com.example.navigationdemo.Importantclasses.MySingleton;
import com.example.navigationdemo.R;
import com.example.navigationdemo.Utils.SessionManager1;
import com.example.navigationdemo.activity.LoginActivity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.DELETE;
import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;

/**
 * A simple {@link Fragment} subclass.
 */
public class Exit extends Fragment {

    String uploadUrl="http://cas.mindhackers.org/vehicle-service-booking/public/api/deletegarage";

    Button exit,back;
    SessionManager1 sessionManager1;
    HashMap<String,String> data;
    public Exit() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_exit, container, false);

        sessionManager1=new SessionManager1(getActivity());
        exit=(Button)v.findViewById(R.id.btnexit);
        back=(Button)v.findViewById(R.id.btnbackagain);
        data=sessionManager1.getapidata();
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(getActivity(), "Your account will be deleted in 1 hour", Toast.LENGTH_SHORT).show();
                try {
                    uploadData();
                  //  Exit.this.wait(300);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
//                Intent i=new Intent(getActivity(),LoginActivityG.class);
//                   startActivity(i);

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),Main2Activity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                getActivity().finish();
            }
        });
        // Inflate the layout for this fragment
        return v;
    }

    private void uploadData() {
       // String Url=appendToUrl(uploadUrl,getParams());
        Log.d("id",data.get("id"));
        StringRequest stringRequest=new StringRequest(Request.Method.POST,uploadUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response",response);
                if (response.equalsIgnoreCase("sucess")){
                Intent i=new Intent(getActivity(),LoginActivityG.class);
                startActivity(i);
                }
             //   Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response",error.getMessage());
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){ protected HashMap<String, String> getParams(){
            HashMap<String, String> params = new HashMap<>();
            params.put("id",data.get("id"));

            return params;}};
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

//
//    public static String appendToUrl(String url, HashMap<String, String> parameters) {
//        try {
//            URI uri = new URI(url);
//            String query = uri.getQuery();
//            StringBuilder builder = new StringBuilder();
//            if (query != null)
//                builder.append(query);
//
//            for (Map.Entry<String, String> entry : parameters.entrySet()) {
//                String keyValueParam = entry.getKey() + "=" + entry.getValue();
//                if (!builder.toString().isEmpty())
//                    builder.append("&");
//
//                builder.append(keyValueParam);
//            }
//
//            URI newUri = new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), builder.toString(), uri.getFragment());
//            return newUri.toString();
//
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        return url;
//    }
}
