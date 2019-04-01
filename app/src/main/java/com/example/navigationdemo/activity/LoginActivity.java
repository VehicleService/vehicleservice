package com.example.navigationdemo.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.navigationdemo.Importantclasses.MySingleton;
import com.example.navigationdemo.R;
import com.example.navigationdemo.Utils.SessionManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class LoginActivity extends AppCompatActivity {
    Button submit;
    TextView register,forgetpassword;
    String email = "";
    String pass = "";
    EditText Email,PassWord;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    SessionManager sessionManager;
    FirebaseAuth auth;
    FirebaseUser user;
    SharedPreferences sharedPreferences;
    String instanceID = "";
    String uploadURL = "http://cas.mindhackers.org/vehicle-service-booking/public/api/userlogin";
    String forgeturl="http://cas.mindhackers.org/vehicle-service-booking/public/api/forgotpassword";
    //SessionManager1 sessionManager;
   // HashMap<String,String> user;
    String email1,sessionid,sid1,sid2,service1,service2,vid1,vid2,vehicle1,vehicle2,email2;
    JSONArray array,array1;
    int id;
    String refreshToken,name,phone,latitude,longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        //handleSSLHandshake();

        sessionManager = new SessionManager(LoginActivity.this);

        if(sessionManager.readStatus()){
            Intent intent = new Intent(this, SelectionActivity.class);
            startActivity(intent);
            finish();
        }



        refreshToken=FirebaseInstanceId.getInstance().getToken();
    //    Log.d("Token",refreshToken);
        instanceID =refreshToken;
        sessionManager.putId(refreshToken);



        submit=(Button)findViewById(R.id.btnSubmit);
        register=(TextView)findViewById(R.id.txtLink_to_Register);
        forgetpassword=(TextView)findViewById(R.id.txtNewUser);
        Email=(EditText)findViewById(R.id.etxtEmail);
        PassWord=(EditText)findViewById(R.id.etxtPassword);
       // firebaseDatabase=FirebaseDatabase.getInstance();
        //reference=firebaseDatabase.getReference("UserDetails");

        setTitle("Login");
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Email.getText().toString().isEmpty()){
                    Email.setError("Enter Email Address");
                }else if(!isValidEmail(Email.getText().toString())){
                    Email.setError("Enter Valid Email Address");
                } else if(!isValidPassword(PassWord.getText().toString())){
                    PassWord.setError("Enter PassWord");
                }else{

                    uploadData();
                }
//           //    reference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        boolean isExists=false;
//                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
//                            if(Email.getText().toString().equalsIgnoreCase(snapshot.child("email").getValue().toString())){
//                             //   Toast.makeText(LoginActivity.this, "Successfull", Toast.LENGTH_SHORT).show();
//                                isExists=true;
//                                Log.d("Key",snapshot.getKey());
                              // sessionManager.writeStatus(true);
//                                sessionManager.putData(snapshot.getKey(),snapshot.child("username").getValue().toString()
//                                        ,snapshot.child("password").getValue().toString());
//                                HashMap<String,String> userData=sessionManager.getData();
//                                String instanceId=userData.get("InstanceId");
//                                reference.child(snapshot.getKey()).child("instanceId").setValue(instanceId);
//                                email = Email.getText().toString();
//                                pass = PassWord.getText().toString();
//                                FirebaseAuth.getInstance().signInWithEmailAndPassword(email , pass).addOnCompleteListener(LoginActivity.this,new OnCompleteListener<AuthResult>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<AuthResult> task) {
//                                            if(task.isSuccessful()) {
//                                            }
//                                    else {
//                                                Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
//                                            }
//                                    }
//
//                                });
                                         //   Intent i=new Intent(LoginActivity.this,SelectionActivity.class);
                                         //   startActivity(i);
//
//                                      FirebaseUser user=  FirebaseAuth.getInstance().getCurrentUser();
//                                  AuthCredential credential=EmailAuthCredential.isSignInWithEmailLink(Email.getText().toString());
//                                  credential.


                         //   }

                        //if(!isExists){
                           // Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();

                       // }
                  //  }

//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                        Log.d("", "onCancelled: "+databaseError.getDetails());
//                    }
//                });

            }
        });
    forgetpassword.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder alert=new AlertDialog.Builder(LoginActivity.this);
            alert.setTitle("Email");
            alert.setMessage("Enter email address to send password");
            final EditText input=new EditText(LoginActivity.this);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(params);
            alert.setView(input);
            alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    email2=input.getText().toString();
                       forgotpassword();

                }
            });
            alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                }
            });
            alert.show();
        }
    });


    }

    private void forgotpassword() {
        //String Url=appendToUrl(forgeturl,getParams());
        email1=Email.getText().toString();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, uploadURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response",response);
              //  Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){protected HashMap<String, String> getParams(){
            HashMap<String, String> params = new HashMap<>();
            params.put("email",email2);

            return params;}

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }


//    public static String appendToUrl(String url, HashMap<String, String> parameters) {
//        try {
//            URI uri = new URI(url);
//            String query = uri.getQuery();
//            StringBuilder builder = new StringBuilder();
//
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

    public  boolean isValidPassword(String s2){
        if(s2!=null &&s2.length()>6){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean isValidEmail(String s1){
        String Email_Pattern="^[A-Z a-z 0-9]+\\@+[A-Z a-z 0-9]+\\.+[A-Z a-z 0-9]{2,}$";
        Pattern p=Pattern.compile(Email_Pattern);
        Matcher m=p.matcher(s1);
        return m.matches();
    }

    public void uploadData(){

        email = Email.getText().toString();
        pass = PassWord.getText().toString();
        Log.d("e-p", email + pass);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, uploadURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              //  Log.d("TAG", "onResponse: "+response);
                Log.d("response",response);
                if (!response.equals(Integer.toString(-1))){
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        name=jsonObject.getString("name");
                        phone=jsonObject.getString("phone");
                        latitude=jsonObject.getString("latitude");
                        longitude=jsonObject.getString("longitude");

                        email1=jsonObject.getString("email");
                        id=jsonObject.getInt("id");
                        sessionid=jsonObject.getString("session_id");
                         array=jsonObject.getJSONArray("vehicle_data");
                        for(int i=0;i<array.length()-1;i++){
                            JSONObject data=array.getJSONObject(i);



                        }
                        JSONObject data=array.getJSONObject(0);
                        vid1=String.valueOf(data.getInt("id"));
                        vehicle1=data.getString("vehicle_type");
                        JSONObject data1=array.getJSONObject(1);
                        vid2=String.valueOf(data1.getInt("id"));
                        vehicle2=data1.getString("vehicle_type");
                        array1=jsonObject.getJSONArray("service_data");
                        for(int j=0;j<array1.length()-1;j++){
                            JSONObject data4=array1.getJSONObject(j);
                             }
                        JSONObject data2=array1.getJSONObject(0);
                        sid1=String.valueOf(data2.getInt("id"));
                        service1=data2.getString("service_type");
                        JSONObject data3=array1.getJSONObject(1);
                        sid2=String.valueOf(data3.getInt("id"));
                        service2=data3.getString("service_type");
                        sessionManager.putapidata(String.valueOf(id),email1,sessionid,sid1,service1,sid2,service2,vid1,vehicle1,vid2,vehicle2);
                        sessionManager.writeStatus(true);
                        sessionManager.setprofile(name,phone,email1,latitude,longitude);
                      //  Toast.makeText(LoginActivity.this, email1+id+sessionid+array+array1, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                   // Toast.makeText(getApplicationContext(), email + pass +  response, Toast.LENGTH_LONG).show();
                    Intent i=new Intent(LoginActivity.this,SelectionActivity.class);
                   startActivity(i);
                    finish();
                 //   sessionManager.writeStatus(true);
                }else{
                    Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "invalid credentials", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse: "+error.getMessage());
                Toast.makeText(getApplicationContext(), " " + error, Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", pass);
                params.put("instance_id", instanceID);
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
