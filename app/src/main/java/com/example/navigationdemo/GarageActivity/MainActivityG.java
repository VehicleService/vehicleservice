package com.example.navigationdemo.GarageActivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.navigationdemo.Pojo.NotificationStatus;
import com.example.navigationdemo.Pojo.NotificationType;
import com.example.navigationdemo.Utils.SessionManager1;
import com.example.navigationdemo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivityG extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference referencev,references,instance;
    String key,vehicle,service,vtypekey,stypekey;
    RadioGroup vehicleType,serviceType;
    RadioButton vtype,stype;
    Button submit;
    int i;
    int j;
    HashMap<String,String> data;
    SessionManager1 sessionManager1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_g);
        submit=(Button)findViewById(R.id.btnSubmit);
        vehicleType=(RadioGroup)findViewById(R.id.rgVehicleType);
        serviceType=(RadioGroup)findViewById(R.id.rgServiceType);
        firebaseDatabase=FirebaseDatabase.getInstance();

        referencev=firebaseDatabase.getReference("VehicleType");
        references=firebaseDatabase.getReference("ServiceType");
        instance=firebaseDatabase.getReference("GarageDetails");
        sessionManager1 =new SessionManager1(MainActivityG.this);
        data= sessionManager1.getData();
        key=data.get("Key");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=vehicleType.getCheckedRadioButtonId();
                j=serviceType.getCheckedRadioButtonId();
                if(i==-1 || j==-1){
                    Toast.makeText(MainActivityG.this, "Select Types", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    vtype = (RadioButton) findViewById(i);
                    stype = (RadioButton) findViewById(j);
                    vehicle = vtype.getText().toString();
                    service = stype.getText().toString();

                    referencev.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                if (vehicle.equalsIgnoreCase(snapshot.child("type").getValue().toString())) {
                                    vtypekey = snapshot.getKey();
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
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                if (service.equalsIgnoreCase(snapshot.child("type").getValue().toString())) {
                                    stypekey = snapshot.getKey();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });}
                if(vtypekey!=null && stypekey!=null){
                    instance.child(key).child("VehicleTypeId").setValue(vtypekey);
                    instance.child(key).child("ServiceTypeId").setValue(stypekey);




                }

            }
        });


    }
}
