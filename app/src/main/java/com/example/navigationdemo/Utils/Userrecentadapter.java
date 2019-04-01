package com.example.navigationdemo.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navigationdemo.GarageActivity.TrackUserActivity;
import com.example.navigationdemo.Pojo.UserRecent;
import com.example.navigationdemo.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Userrecentadapter extends RecyclerView.Adapter<Userrecentadapter.myViewHolder> {
    List<UserRecent> userRecent;
    Context context;
    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView time,username,data,phone;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            context=itemView.getContext();

            time=(TextView)itemView.findViewById(R.id.rTime);
            username=(TextView)itemView.findViewById(R.id.rUsername);
            data=(TextView)itemView.findViewById(R.id.rData);
            phone=(TextView)itemView.findViewById(R.id.phone);
        }
    }

    public Userrecentadapter(List<UserRecent> userRecent) {
        this.userRecent = userRecent;
    }

    @NonNull
    @Override
    public Userrecentadapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.userrecent,viewGroup,false);
        return new myViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull final Userrecentadapter.myViewHolder myViewHolder, int i) {
       final UserRecent u=userRecent.get(i);
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(v.getContext(), ""+myViewHolder.username.getText()+""+u.getLatitude()+u.getLongitude(), Toast.LENGTH_SHORT).show();
                Intent i=new Intent(v.getContext(),TrackUserActivity.class);
                i.putExtra("lat",u.getLatitude());
                i.putExtra("lon",u.getLongitude());
                context.startActivity(i);
            }
        });

        myViewHolder.username.setText(u.getUsername());
        myViewHolder.time.setText(u.getTime());
        myViewHolder.data.setText(u.getData());
        myViewHolder.phone.setText(u.getPhone());
    }

    @Override
    public int getItemCount() {
        return userRecent.size();
    }
}
