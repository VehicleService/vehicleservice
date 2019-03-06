package com.example.navigationdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.navigationdemo.Pojo.User;
import com.example.navigationdemo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    Button submit;
    EditText Username,PhoneNumber,Email,Password,Confirmpassword;
    FirebaseDatabase instance;
    DatabaseReference reference;

//    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        submit=(Button)findViewById(R.id.btnSubmit);
        Username=(EditText)findViewById(R.id.etxtUserName);
        PhoneNumber=(EditText)findViewById(R.id.etxtPhoneNumber);
        Email=(EditText)findViewById(R.id.etxtEmail);
        Password=(EditText)findViewById(R.id.etxtPassword);
        Confirmpassword=(EditText)findViewById(R.id.etxtConfirmpassword);
        setTitle("Vehicle Service Booking");
        instance=FirebaseDatabase.getInstance();
        reference=instance.getReference("UserDetails");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Username.getText().toString().isEmpty()){
                    Username.setError("Enter User Name");
                }else if(Email.getText().toString().isEmpty()){
                    Email.setError("Enter Email Address");
                }else if(!isValidEmail(Email.getText().toString())){
                    Email.setError("Enter Valid Email Address");
                }else if(PhoneNumber.getText().toString().isEmpty()){
                    PhoneNumber.setError("Enter Phone Number");
                }
                else if(PhoneNumber.getText().toString().length()<10||PhoneNumber.getText().toString().length()>10){
                    PhoneNumber.setError("Enter Valid Phone Number");
                }else if(!isValidPhonenumber(PhoneNumber.getText().toString())){
                    PhoneNumber.setError("Enter Valid PhoneNumber");
                }
                else if(!isValidPassword(Password.getText().toString())){
                    Password.setError("Enter PassWord");
                }else if(Confirmpassword.getText().toString().isEmpty()){
                    Confirmpassword.setError("ReEnter Password");
                }
                else if(!isValidConfirmPassword(Confirmpassword.getText().toString())){
                    Confirmpassword.setError("Password Should Match With Above PassWord");
                    Password.setText("");
                }else{
//                    sessionManager=new SessionManager(getApplicationContext());
//                    String UserName=Username.getText().toString();
//                    String PassWord=Password.getText().toString();
//                    sessionManager.putData(UserName,PassWord);

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            boolean isExists=false;
                            for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                                if(Email.getText().toString().equalsIgnoreCase(snapshot.child("email").getValue().toString())){
                                    isExists=true;
                                    Toast.makeText(RegisterActivity.this, "Email Exists", Toast.LENGTH_SHORT).show();

                                }

                            }
                            if(!isExists)
                                setUserdata();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });




                }
            }
        });
    }

    public void setUserdata() {
        String key=reference.push().getKey();
        User user=new User(Username.getText().toString(),PhoneNumber.getText().toString(),Email.getText().toString(),Password.getText().toString(),"","","");

        reference.child(key).setValue(user);
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(Email.getText().toString(),Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Toast.makeText(RegisterActivity.this, "Authentication done", Toast.LENGTH_SHORT).show();
            }
        });

        reference.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User d = dataSnapshot.getValue(User.class);
                if (d != null)
                    Log.e("Status", d.username);
                Intent i=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
    public boolean isValidConfirmPassword(String s){
        String s1=Password.getText().toString();
        if(s1.equalsIgnoreCase(s)){return  true;}
        else{
            return false;
        }
    }
    public boolean isValidEmail(String s1){
        String Email_Pattern="^[A-Z a-z 0-9]+\\@+[A-Z a-z 0-9]+\\.+[A-Z a-z 0-9]{2,}$";
        Pattern p=Pattern.compile(Email_Pattern);
        Matcher m=p.matcher(s1);
        return m.matches();
    }
    public  boolean isValidPhonenumber(String s){
        return android.util.Patterns.PHONE.matcher(s).matches();
    }
}
