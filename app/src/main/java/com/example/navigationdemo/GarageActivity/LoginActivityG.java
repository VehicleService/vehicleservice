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

import com.example.navigationdemo.R;
import com.example.navigationdemo.Utils.SessionManager1;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivityG extends AppCompatActivity {

    Button submit;
    TextView register,forgetpassword;
    EditText Email,PassWord;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    SessionManager1 sessionManager1;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_g);
        submit=(Button)findViewById(R.id.btnSubmit);
        register=(TextView)findViewById(R.id.txtLink_to_Register);
        forgetpassword=(TextView)findViewById(R.id.txtNewUser);
        Email=(EditText)findViewById(R.id.etxtEmail);
        PassWord=(EditText)findViewById(R.id.etxtPassword);
        firebaseDatabase=FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference("GarageDetails");

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
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean isExists=false;
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            if(Email.getText().toString().equalsIgnoreCase(snapshot.child("email").getValue().toString())){
                                Toast.makeText(LoginActivityG.this, "Successfull", Toast.LENGTH_SHORT).show();
                                isExists=true;
                                sessionManager1 =new SessionManager1(LoginActivityG.this);
                                sessionManager1.putData(snapshot.getKey(),snapshot.child("username").getValue().toString(),
                                        snapshot.child("password").getValue().toString());

                                FirebaseAuth.getInstance().signInWithEmailAndPassword(Email.getText().toString(),PassWord.getText().toString()).addOnCompleteListener(LoginActivityG.this,new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            Intent i=new Intent(LoginActivityG.this,MainActivityG.class);
                                            startActivity(i);}
                                        else {
                                            Toast.makeText(LoginActivityG.this, "Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                });








//                                Log.d("Key",snapshot.getKey());
//                                sessionManager1=new SessionManager1(LoginActivityG.this);
//                                sessionManager1.putData(snapshot.getKey(),snapshot.child("username").getValue().toString()
//                                        ,snapshot.child("password").getValue().toString());

                            }

                        }if(!isExists){
                            Toast.makeText(LoginActivityG.this, "Login Failed", Toast.LENGTH_SHORT).show();

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
}
