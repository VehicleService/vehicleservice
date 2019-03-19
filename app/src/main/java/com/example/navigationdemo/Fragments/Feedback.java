package com.example.navigationdemo.Fragments;


import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navigationdemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Feedback extends Fragment {


    RatingBar rt;
    Button submit;
    RelativeLayout feedback;
    EditText comments;
    public Feedback() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_feedback, container, false);
        rt=(RatingBar)v.findViewById(R.id.ratingbar);
        submit=(Button)v.findViewById(R.id.btnSubmit);
        feedback=(RelativeLayout)v.findViewById(R.id.relativefeedback);
        comments=(EditText)v.findViewById(R.id.etxtcomments);
        LayerDrawable stars=(LayerDrawable)rt.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW,PorterDuff.Mode.SRC_ATOP);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView rate=(TextView)feedback.findViewById(R.id.txtrating);
                rate.setText("Your Ratings:"+String.valueOf(rt.getRating()));
                Toast.makeText(getActivity(), ""+comments.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

}
