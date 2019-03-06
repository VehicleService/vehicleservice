package com.example.navigationdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.navigationdemo.R;
import com.example.navigationdemo.Utils.SessionManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SelectionActivity extends AppCompatActivity {
    Button submit;
    RadioGroup vehicletype,serviceType;
    RadioButton vtype,stype;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference referencev,references;
    int i,j;
    String vkey,skey;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        setTitle("Vehicle Service Booking");
        submit=(Button)findViewById(R.id.btnSubmit);
        vehicletype=(RadioGroup)findViewById(R.id.rgVehicleType);
        serviceType=(RadioGroup)findViewById(R.id.rgServiceType);
        firebaseDatabase=FirebaseDatabase.getInstance();
        referencev=firebaseDatabase.getReference("VehicleType");
        references=firebaseDatabase.getReference("ServiceType");
        sessionManager=new SessionManager(SelectionActivity.this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               i=vehicletype.getCheckedRadioButtonId();
               j=serviceType.getCheckedRadioButtonId();
               if(i==-1 || j==-1){
                   Toast.makeText(SelectionActivity.this, "Select any option", Toast.LENGTH_SHORT).show();
               }
               else
               {
                   vtype=(RadioButton)findViewById(i);
                   stype=(RadioButton)findViewById(j);
                    referencev.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        if(vtype.getText().toString().equalsIgnoreCase(snapshot.child("type").getValue().toString())){
                            vkey=snapshot.getKey();
                        }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    references.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                if(stype.getText().toString().equalsIgnoreCase(snapshot.child("type").getValue().toString())){
                                    skey=snapshot.getKey();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    if(vkey!=null&&skey!=null){
                        Toast.makeText(SelectionActivity.this, "data"+vkey+""+skey, Toast.LENGTH_SHORT).show();
                        sessionManager.putKeys(vkey,skey);

                   Intent i=new Intent(SelectionActivity.this,MainActivity.class);
                   startActivity(i);
               }}
            }
        });
    }
}
