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
        TextView appname,time,username,data;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            context=itemView.getContext();
            appname=(TextView)itemView.findViewById(R.id.rAppname);
            time=(TextView)itemView.findViewById(R.id.rTime);
            username=(TextView)itemView.findViewById(R.id.rUsername);
            data=(TextView)itemView.findViewById(R.id.rData);
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
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), ""+myViewHolder.username.getText(), Toast.LENGTH_SHORT).show();
                Intent i=new Intent(v.getContext(),TrackUserActivity.class);
                context.startActivity(i);
            }
        });
        UserRecent u=userRecent.get(i);
        myViewHolder.appname.setText("Vehicle Service Booking");
        myViewHolder.time.setText(new SimpleDateFormat("HH:mm:ss a dd/MM/yyyy").format(Calendar.getInstance().getTime()));
        myViewHolder.username.setText(u.getUsername());
        myViewHolder.data.setText(u.getData());
    }

    @Override
    public int getItemCount() {
        return userRecent.size();
    }
}
