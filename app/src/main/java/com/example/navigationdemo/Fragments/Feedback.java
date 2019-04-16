package com.example.navigationdemo.Fragments;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.navigationdemo.Importantclasses.MySingleton;
import com.example.navigationdemo.R;
import com.example.navigationdemo.Utils.SessionManager;
import com.example.navigationdemo.activity.BillActivity;
import com.example.navigationdemo.activity.CommentActivity;
import com.example.navigationdemo.activity.LoginActivity;
import com.example.navigationdemo.activity.MainActivity;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;

import java.util.HashMap;

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

    String token1;
    SessionManager sessionManager;
    HashMap<String,String> data,id;

    String uploadUrl="http://cas.mindhackers.org/vehicle-service-booking/public/api/payment";
    public Feedback() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_feedback, container, false);
//        rt = (RatingBar) v.findViewById(R.id.ratingbar);

//        submit = (Button) v.findViewById(R.id.btnSubmit);
        feedback = (RelativeLayout) v.findViewById(R.id.relativefeedback);
        payment = (RelativeLayout) v.findViewById(R.id.payment);
        pay = (Button) v.findViewById(R.id.btnpay);
//        comments = (EditText) v.findViewById(R.id.etxtcomments);
        amount = (TextView) v.findViewById(R.id.etxtamount);
        cardInputWidget = (CardInputWidget) v.findViewById(R.id.card_input_widget);

        sessionManager = new SessionManager(getContext());
        data = sessionManager.getcost();
        id = sessionManager.getapidata();
        //if (data.get("coststatus").equalsIgnoreCase("true")){
        amount.setText(data.get("cost"));
    //}
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
                        Toast.makeText(getActivity(), "Payment Failed", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onSuccess(Token token) {
                        token1=token.getId();

                        Log.d("Token",token.getId()+"Currency"+card.getCurrency());
                        Toast.makeText(getActivity(), "Payment Completed", Toast.LENGTH_SHORT).show();
                        uploadData();
                    }
                });
            }
        });








//        LayerDrawable stars=(LayerDrawable)rt.getProgressDrawable();
//        stars.getDrawable(2).setColorFilter(Color.YELLOW,PorterDuff.Mode.SRC_ATOP);
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TextView rate=(TextView)feedback.findViewById(R.id.txtrating);
//                rate.setText("Your Ratings:"+String.valueOf(rt.getRating()));
//               // Toast.makeText(getActivity(), ""+comments.getText().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
        return v;
    }

    private void uploadData() {
        Log.d("request",data.get("garageid")+id.get("id")+data.get("cost")+token1+data.get("coststatus"));
        //String Url=appendToUrl(upload,getParams());
        StringRequest stringRequest=new StringRequest(Request.Method.POST,uploadUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("data",response+"done");
                Intent i=new Intent(getActivity(),CommentActivity.class);
                startActivity(i);
//                AlertDialog.Builder alert=new AlertDialog.Builder(getActivity());
//                alert.setTitle("Feedback");
//                alert.setMessage("Comments");
//                final EditText input=new EditText(getActivity());
//                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//                input.setLayoutParams(params);
//                RatingBar ratingBar=new RatingBar(getActivity());
//                ratingBar.setLayoutParams(params);
//                alert.setView(ratingBar);
//              //  alert.setView(input);
//                alert.show();

                //  Toast.makeText(BillActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("error",""+error.getMessage());
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }){ public HashMap<String, String> getParams() {
            HashMap<String, String> params = new HashMap<>();
            params.put("garage_id",data.get("garageid"));
            params.put("user_id",id.get("id"));
            params.put("amount",data.get("cost")+"00");
            params.put("currency","INR");
            params.put("token",token1);
            return params;
        }};
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);

    }

}
