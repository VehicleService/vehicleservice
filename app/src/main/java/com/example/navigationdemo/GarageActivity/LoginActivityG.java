package com.example.navigationdemo.GarageActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.navigationdemo.MySingleton;
import com.example.navigationdemo.R;
import com.example.navigationdemo.Utils.SessionManager1;
import com.example.navigationdemo.activity.LoginActivity;
import com.example.navigationdemo.activity.SelectionActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.navigationdemo.activity.LoginActivity.handleSSLHandshake;

public class LoginActivityG extends AppCompatActivity {

    Button submit;
    TextView register,forgetpassword;
    EditText Email,PassWord;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    SessionManager1 sessionManager1;
    FirebaseAuth auth;
    String email,pass,instanceId;
    String uploadURL = "http://cas.mindhackers.org/vehicle-service-booking/public/api/garagelogin";
    String id,sessionid,email1,sid1,sid2,sid3,service1,service2,service3,vid1,vid2,vid3,vehicle1,vehicle2,vehicle3;
    JSONArray array1,array;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_g);
       // handleSSLHandshake();
         /*
        if(sessionManager.readStatus()){
            Intent intent = new Intent(this, SelectionActivity.class);
            startActivity(intent);
            finish();
        }
*/
        setTitle("Login");
        submit=(Button)findViewById(R.id.btnSubmit);
        register=(TextView)findViewById(R.id.txtLink_to_Register);
        forgetpassword=(TextView)findViewById(R.id.txtNewUser);
        Email=(EditText)findViewById(R.id.etxtEmail);
        PassWord=(EditText)findViewById(R.id.etxtPassword);
        firebaseDatabase=FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference("GarageDetails");
        sessionManager1=new SessionManager1(LoginActivityG.this);
        instanceId=sessionManager1.getId();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivityG.this,RegisterActivityG.class);
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
                uploadData();}
//
//                reference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        boolean isExists=false;
//                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
//                            if(Email.getText().toString().equalsIgnoreCase(snapshot.child("email").getValue().toString())){
//                                Toast.makeText(LoginActivityG.this, "Successfull", Toast.LENGTH_SHORT).show();
//                                isExists=true;
//                                sessionManager1.writeStatus(true);
//                                sessionManager1.putData(snapshot.getKey(),snapshot.child("username").getValue().toString(),
//                                        snapshot.child("password").getValue().toString());
//                                HashMap<String,String> userData=sessionManager1.getData();
//                                String instanceId=userData.get("InstanceId");
//                                reference.child(snapshot.getKey()).child("instanceId").setValue(instanceId);
//                                FirebaseAuth.getInstance().signInWithEmailAndPassword(Email.getText().toString(),PassWord.getText().toString()).addOnCompleteListener(LoginActivityG.this,new OnCompleteListener<AuthResult>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<AuthResult> task) {
//                                        if(task.isSuccessful()){
//                                            Intent i=new Intent(LoginActivityG.this,Main2Activity.class);
//                                            startActivity(i);}
//                                        else {
//                                            Toast.makeText(LoginActivityG.this, "Failed", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//
//                                });
//
//
//
//
//
//
//
//
////                                Log.d("Key",snapshot.getKey());
////                                sessionManager1=new SessionManager1(LoginActivityG.this);
////                                sessionManager1.putData(snapshot.getKey(),snapshot.child("username").getValue().toString()
////                                        ,snapshot.child("password").getValue().toString());
//
//                            }
//
//                        }if(!isExists){
//                            Toast.makeText(LoginActivityG.this, "Login Failed", Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//
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
                AlertDialog.Builder alert=new AlertDialog.Builder(LoginActivityG.this);
                alert.setTitle("Email");
                alert.setMessage("Enter email address to send password");
                final EditText input=new EditText(LoginActivityG.this);
                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(params);
                alert.setView(input);
                alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                    if(input.getText().toString().equalsIgnoreCase(snapshot.child("email").getValue().toString())){
                                        String to=input.getText().toString();
                                        auth=FirebaseAuth.getInstance();
                                        auth.sendPasswordResetEmail(to).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(LoginActivityG.this, "Email sent", Toast.LENGTH_SHORT).show();
                                                }
                                                else{
                                                    Toast.makeText(LoginActivityG.this, "Failed", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

//                                    String TO[]={to};
//                                    String CC[]={""};
//                                    Intent email=new Intent(Intent.ACTION_SEND);
//                                    email.setData(Uri.parse("mailto:"));
//                                    email.setType("text/plain");
//                                    email.putExtra(Intent.EXTRA_EMAIL,TO);
//                                    email.putExtra(Intent.EXTRA_SUBJECT,"Password");
//                                    email.putExtra(Intent.EXTRA_TEXT,snapshot.child("password").getValue().toString());
//
//                                    try{
//                                        startActivity(Intent.createChooser(email,"send mail..."));
//                                        finish();
//                                    }catch (Exception e){
//
//                                        Toast.makeText(LoginActivityG.this, "No email client installed", Toast.LENGTH_SHORT).show();
//                                    }

//                                        BackgroundMail.newBuilder(LoginActivityG.this)
//                                                .withUsername("aptulsibhai1974@gmail.com")
//                                                .withPassword("17bellavista")
//                                                .withMailto(to)
//                                                .withType(BackgroundMail.TYPE_PLAIN)
//                                                .withSubject("Password")
//                                                .withBody(snapshot.child("password").getValue().toString())
//                                                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
//                                                    @Override
//                                                    public void onSuccess() {
//                                                        Toast.makeText(LoginActivityG.this, "Mail Sent", Toast.LENGTH_SHORT).show();
//                                                    }
//                                                }).withOnFailCallback(new BackgroundMail.OnFailCallback() {
//                                            @Override
//                                            public void onFail() {
//                                                Toast.makeText(LoginActivityG.this, "failed", Toast.LENGTH_SHORT).show();
//                                            }
//                                        }).send();

                                    }
                                    else{
                                        Toast.makeText(LoginActivityG.this, "Email is not registered", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
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


    private void uploadData() {
        email = Email.getText().toString();
        pass = PassWord.getText().toString();
        Log.d("e-p", email + pass);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, uploadURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals(Integer.toString(-1))){
                    try{
                        JSONObject jsonObject=new JSONObject(response);
                        email1=jsonObject.getString("email");
                        id=String.valueOf(jsonObject.getInt("id"));
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
                        JSONObject data5=array.getJSONObject(2);
                        vid3=String.valueOf(data5.getInt("id"));
                        vehicle3=data5.getString("vehicle_type");
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

                        JSONObject data6=array1.getJSONObject(2);
                        sid3=String.valueOf(data6.getInt("id"));
                        service3=data6.getString("service_type");
                        sessionManager1.putapidata(id,email1,sessionid,sid1,service1,sid2,service2,sid3,service3,vid1,vehicle1,vid2,vehicle2,vid3,vehicle3);
                        Toast.makeText(LoginActivityG.this, email1+id+sessionid+array+array1, Toast.LENGTH_SHORT).show();

                    }
                    catch (Exception e){
                        Toast.makeText(LoginActivityG.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    Intent i=new Intent(LoginActivityG.this,Main2Activity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(LoginActivityG.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(LoginActivityG.this, response+"Login successfull", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), " " + error, Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", pass);
                params.put("instance_id",instanceId);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
