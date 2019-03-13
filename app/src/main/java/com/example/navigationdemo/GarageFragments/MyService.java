package com.example.navigationdemo.GarageFragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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

import com.example.navigationdemo.GarageActivity.Main2Activity;
import com.example.navigationdemo.R;
import com.example.navigationdemo.Utils.SessionManager1;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyService extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference referencev,references,instance;
    String key,vehicle,service,vtypekey,stypekey;
    RadioGroup vehicleType,serviceType;
    RadioButton vtype,stype;
    Button submit,ok,back;
    int i;
    int j;
    int flag;
    HashMap<String,String> data;
    SessionManager1 sessionManager1;
    RelativeLayout select,cost;
    EditText twor,fourr,twoe1,twoe2,twoe3,twoe4,foure1,foure2,foure3,foure4;
    public MyService() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.fragment_my, container,false);
        flag=0;
        submit=(Button)view.findViewById(R.id.btnSubmit);
        vehicleType=(RadioGroup)view.findViewById(R.id.rgVehicleType);
        serviceType=(RadioGroup)view.findViewById(R.id.rgServiceType);
        firebaseDatabase=FirebaseDatabase.getInstance();
        // refr=firebaseDatabase.getReference("Range");
        select=(RelativeLayout)view.findViewById(R.id.relativeSelect);
        cost=(RelativeLayout)view.findViewById(R.id.relativeCost);
        referencev=firebaseDatabase.getReference("VehicleType");
        references=firebaseDatabase.getReference("ServiceType");
        instance=firebaseDatabase.getReference("GarageDetails");
        sessionManager1 =new SessionManager1(getActivity());
        data= sessionManager1.getData();
        key=data.get("Key");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               flag=flag+1;
                i=vehicleType.getCheckedRadioButtonId();
                j=serviceType.getCheckedRadioButtonId();
                if(i==-1 && j==-1){
                    Toast.makeText(getActivity(), "Select Types", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    vtype=(RadioButton)select.findViewById(i);
                    stype=(RadioButton)select.findViewById(j);

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
                    });
                    if(vtypekey!=null && stypekey!=null) {
                        instance.child(key).child("VehicleTypeId").setValue(vtypekey);
                        instance.child(key).child("ServiceTypeId").setValue(stypekey);
//                    DateFormat format1=new SimpleDateFormat("HH:mm:ss a dd/MM/yyyy");
//                    Range r=new Range("20",""+format1.format(Calendar.getInstance().getTime()));
//                    String key=refr.push().getKey();
//                    refr.child(key).setValue(r);
//                    if(vehicle.equalsIgnoreCase("TwoWheeler")&&service.equalsIgnoreCase("Regular")){
//                        final EditText price=new EditText(getActivity());
//                        price.setHint("Enter regular service cost for two wheeler");
//                        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
//                        params.setMargins(20,20,20,20);
//                        price.setLayoutParams(params);
//                    }
//                    else if(vehicle.equalsIgnoreCase("TwoWheeler")&&service.equalsIgnoreCase("Emergancy")){
//                        final EditText price=new EditText(getActivity());
//                        price.setHint("Enter emergancy visit cost for two wheeler");
//                        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
//                        params.setMargins(20,20,20,20);
//                        price.setLayoutParams(params);
//                    }
                        select.setVisibility(View.INVISIBLE);
                        cost.setVisibility(View.VISIBLE);
                        ok=(Button)cost.findViewById(R.id.btnOk);
                        back=(Button)cost.findViewById(R.id.btnback);
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                twor=(EditText)cost.findViewById(R.id.etxttr);
                                fourr=(EditText)cost.findViewById(R.id.etxtfr);
                                twoe1=(EditText)cost.findViewById(R.id.etxttef);
                                twoe2=(EditText)cost.findViewById(R.id.etxttet);
                                twoe3=(EditText)cost.findViewById(R.id.etxtteff);
                                twoe4=(EditText)cost.findViewById(R.id.etxttett);
                                foure1=(EditText)cost.findViewById(R.id.etxtfef);
                                foure2=(EditText)cost.findViewById(R.id.etxtfet);
                                foure3=(EditText)cost.findViewById(R.id.etxtfeff);
                                foure4=(EditText)cost.findViewById(R.id.etxtfett);
                                if(vehicle.equalsIgnoreCase("TwoWheeler")&&service.equalsIgnoreCase("Regular")){
                                    if(!twor.getText().toString().isEmpty()){
                                        Intent i=new Intent(getActivity(),Main2Activity.class);
                                        startActivity(i);}
                                        else{
                                        Toast.makeText(getActivity(), "Enter Cost", Toast.LENGTH_SHORT).show();
                                    }
                                }else if(vehicle.equalsIgnoreCase("TwoWheeler")&&service.equalsIgnoreCase("Emergancy")){
                                    if(!twoe1.getText().toString().isEmpty()&& !twoe2.getText().toString().isEmpty()&& !twoe3.getText().toString().isEmpty()&&! twoe4.getText().toString().isEmpty()){
                                        Intent i=new Intent(getActivity(),Main2Activity.class);
                                        startActivity(i);
                                    } else{
                                        Toast.makeText(getActivity(), "Enter Cost", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else if(vehicle.equalsIgnoreCase("TwoWheeler")&&service.equalsIgnoreCase("Both")){
                                    if(!twor.getText().toString().isEmpty()&& !twoe1.getText().toString().isEmpty()&&! twoe2.getText().toString().isEmpty()&& !twoe3.getText().toString().isEmpty()&&! twoe4.getText().toString().isEmpty()){
                                        Intent i=new Intent(getActivity(),Main2Activity.class);
                                        startActivity(i);
                                    }else{
                                        Toast.makeText(getActivity(), "Enter Cost", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else if(vehicle.equalsIgnoreCase("FourWheeler")&&service.equalsIgnoreCase("Regular")){
                                    if(!fourr.getText().toString().isEmpty()){
                                        Intent i=new Intent(getActivity(),Main2Activity.class);
                                        startActivity(i);}
                                    else{
                                        Toast.makeText(getActivity(), "Enter Cost", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else if(vehicle.equalsIgnoreCase("FourWheeler")&&service.equalsIgnoreCase("Emergancy")){
                                    if(!foure1.getText().toString().isEmpty()&& !foure2.getText().toString().isEmpty()&& !foure3.getText().toString().isEmpty()&& !foure4.getText().toString().isEmpty()){
                                        Intent i=new Intent(getActivity(),Main2Activity.class);
                                        startActivity(i);
                                    } else{
                                        Toast.makeText(getActivity(), "Enter Cost", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else if(vehicle.equalsIgnoreCase("FourWheeler")&&service.equalsIgnoreCase("Both")){
                                    if(!fourr.getText().toString().isEmpty()&&! foure1.getText().toString().isEmpty()&&! foure2.getText().toString().isEmpty()&&! foure3.getText().toString().isEmpty()&& ! foure4.getText().toString().isEmpty()){
                                        Intent i=new Intent(getActivity(),Main2Activity.class);
                                        startActivity(i);
                                    } else{
                                        Toast.makeText(getActivity(), "Enter Cost", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else if(vehicle.equalsIgnoreCase("Both")&&service.equalsIgnoreCase("Regular")){
                                    if(!twor.getText().toString().isEmpty()&&  !fourr.getText().toString().isEmpty()){
                                        Intent i=new Intent(getActivity(),Main2Activity.class);
                                        startActivity(i);
                                    } else{
                                        Toast.makeText(getActivity(), "Enter Cost", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else if(vehicle.equalsIgnoreCase("Both")&&service.equalsIgnoreCase("Emergancy")){
                                    if(!twoe1.getText().toString().isEmpty()&& !twoe2.getText().toString().isEmpty()&& !twoe3.getText().toString().isEmpty()&& !twoe4.getText().toString().isEmpty()&&
                                            !foure1.getText().toString().isEmpty()&&!foure2.getText().toString().isEmpty()&& !foure3.getText().toString().isEmpty()&& !foure4.getText().toString().isEmpty()){
                                        Intent i=new Intent(getActivity(),Main2Activity.class);
                                        startActivity(i);
                                    }else{
                                        Toast.makeText(getActivity(), "Enter Cost", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else if(vehicle.equalsIgnoreCase("Both")&&service.equalsIgnoreCase("Both")){
                                   if(!twor.getText().toString().isEmpty()&& !fourr.getText().toString().isEmpty()&&!twoe1.getText().toString().isEmpty()&& !twoe2.getText().toString().isEmpty()&& !twoe3.getText().toString().isEmpty()&& !twoe4.getText().toString().isEmpty()&&
                                           !foure1.getText().toString().isEmpty()&& !foure2.getText().toString().isEmpty()&& !foure3.getText().toString().isEmpty()&&!foure4.getText().toString().isEmpty()){
                                       Intent i=new Intent(getActivity(),Main2Activity.class);
                                       startActivity(i);
                                   }
                                   else{
                                       Toast.makeText(getActivity(), "Enter Cost", Toast.LENGTH_SHORT).show();
                                   }
                                }else {Toast.makeText(getActivity(), "Enter Cost", Toast.LENGTH_SHORT).show();}
                            }
                        });
                        back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            flag=flag-1;
                                select.setVisibility(View.VISIBLE);
                                cost.setVisibility(View.INVISIBLE);
                            }
                        });
                    }}


            }
        });

        // Inflate the layout for this fragment
        return view;
    }


}
