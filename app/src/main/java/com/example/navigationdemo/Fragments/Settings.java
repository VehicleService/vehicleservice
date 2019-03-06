package com.example.navigationdemo.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.navigationdemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Settings extends Fragment {
    CheckBox chkYes,chkNo;
    Button btnSubmit;
    public Settings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_settings, container, false);
        chkYes=(CheckBox) v.findViewById(R.id.chkYes);
        chkNo=(CheckBox)v.findViewById(R.id.chkNo);
        btnSubmit=(Button)v.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chkYes.isChecked()){Toast.makeText(getActivity(), "Yes", Toast.LENGTH_SHORT).show();}
                else if (chkNo.isChecked()){Toast.makeText(getActivity(),"N0",Toast.LENGTH_SHORT).show();}
                else {Toast.makeText(getActivity(),"Nothing is Selected",Toast.LENGTH_SHORT).show();}

            }
        });

      /*  if(chkYes.isChecked()){
            Toast.makeText(getActivity(), "Yes", Toast.LENGTH_SHORT).show();
        }
        else if (chkNo.isChecked()){
            Toast.makeText(getActivity(),"N0",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getActivity(),"Nothing is Selected",Toast.LENGTH_SHORT).show();
        }*/
        return v;
    }

}
