package com.example.navigationdemo.Fragments;


import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;

/**
 * A simple {@link Fragment} subclass.
 */
public class Feedback extends Fragment {


    RatingBar rt;
    Button submit,pay;
    RelativeLayout feedback,payment;
    EditText comments;
    TextView amount;
    CardInputWidget cardInputWidget;
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
        payment=(RelativeLayout)v.findViewById(R.id.payment);
        pay=(Button)v.findViewById(R.id.btnpay);
        comments=(EditText)v.findViewById(R.id.etxtcomments);
        amount=(TextView)v.findViewById(R.id.etxtamount);
        cardInputWidget=(CardInputWidget)v.findViewById(R.id.card_input_widget);
        amount.setText("1000");
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Card card=cardInputWidget.getCard();
                if(card==null){
                    Log.d("Card","Empty");
                    return;
                }
                card.setCurrency("INR");
                Stripe stripe=new Stripe(getActivity(),"pk_test_TYooMQauvdEDq54NiTphI7jx");
                stripe.createToken(card, new TokenCallback() {
                    @Override
                    public void onError(Exception error) {
                        Log.d("Card",error.getMessage());
                        Toast.makeText(getActivity(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onSuccess(Token token) {

                        Log.d("Token",token.getId()+"Currency"+card.getCurrency());
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });








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
