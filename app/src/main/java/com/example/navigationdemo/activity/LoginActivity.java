package com.example.navigationdemo.activity;

import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.navigationdemo.R;
import com.example.navigationdemo.Utils.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    Button submit;
    TextView register,forgetpassword;
    EditText Email,PassWord;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    SessionManager sessionManager;
    FirebaseAuth auth;
    FirebaseUser user;
    //SessionManager sessionManager;
   // HashMap<String,String> user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);


        submit=(Button)findViewById(R.id.btnSubmit);
        register=(TextView)findViewById(R.id.txtLink_to_Register);
        forgetpassword=(TextView)findViewById(R.id.txtNewUser);
        Email=(EditText)findViewById(R.id.etxtEmail);
        PassWord=(EditText)findViewById(R.id.etxtPassword);
        firebaseDatabase=FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference("UserDetails");

        setTitle("Vehicle Service Booking");
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
               reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean isExists=false;
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            if(Email.getText().toString().equalsIgnoreCase(snapshot.child("email").getValue().toString())){
                                Toast.makeText(LoginActivity.this, "Successfull", Toast.LENGTH_SHORT).show();
                                isExists=true;
                                Log.d("Key",snapshot.getKey());
                                sessionManager=new SessionManager(LoginActivity.this);
                                sessionManager.putData(snapshot.getKey(),snapshot.child("username").getValue().toString()
                                        ,snapshot.child("password").getValue().toString());
                                HashMap<String,String> userData=sessionManager.getData();
                                String instanceId=userData.get("InstanceId");
                                reference.child(snapshot.getKey()).child("instanceId").setValue(instanceId);
                                FirebaseAuth.getInstance().signInWithEmailAndPassword(Email.getText().toString(),PassWord.getText().toString()).addOnCompleteListener(LoginActivity.this,new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful()){
                                        Intent i=new Intent(LoginActivity.this,SelectionActivity.class);
                                        startActivity(i);}
                                    else {
                                                Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                            }
                                    }

                                });
//
//                                      FirebaseUser user=  FirebaseAuth.getInstance().getCurrentUser();
//                                  AuthCredential credential=EmailAuthCredential.isSignInWithEmailLink(Email.getText().toString());
//                                  credential.


                            }

                        }if(!isExists){
                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("", "onCancelled: "+databaseError.getDetails());
                    }
                });
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
                                                Toast.makeText(LoginActivity.this, "Email sent", Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
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
//                                        Toast.makeText(LoginActivity.this, "No email client installed", Toast.LENGTH_SHORT).show();
//                                    }
//
//                                    BackgroundMail.newBuilder(LoginActivity.this)
//                                            .withUsername("aptulsibhai1974@gmail.com")
//                                            .withPassword("17bellavista")
//                                            .withMailto(to)
//                                            .withType(BackgroundMail.TYPE_PLAIN)
//                                            .withSubject("Password")
//                                            .withBody(snapshot.child("password").getValue().toString())
//                                            .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
//                                                @Override
//                                                public void onSuccess() {
//                                                    Toast.makeText(LoginActivity.this, "Mail Sent", Toast.LENGTH_SHORT).show();
//                                                }
//                                            }).withOnFailCallback(new BackgroundMail.OnFailCallback() {
//                                        @Override
//                                        public void onFail() {
//                                            Toast.makeText(LoginActivity.this, "failed", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }).send();

                                }else{
                                    Toast.makeText(LoginActivity.this, "Email is not registered", Toast.LENGTH_SHORT).show();
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
}
