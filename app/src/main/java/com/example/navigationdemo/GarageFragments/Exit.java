package com.example.navigationdemo.GarageFragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.navigationdemo.GarageActivity.LoginActivityG;
import com.example.navigationdemo.GarageActivity.Main2Activity;
import com.example.navigationdemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Exit extends Fragment {


    Button exit,back;
    public Exit() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_exit, container, false);


        exit=(Button)v.findViewById(R.id.btnexit);
        back=(Button)v.findViewById(R.id.btnbackagain);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Your account will be deleted in 1 hour", Toast.LENGTH_SHORT).show();
                try {
                    Exit.this.wait(300);
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
                startActivity(i);
            }
        });
        // Inflate the layout for this fragment
        return v;
    }

}
