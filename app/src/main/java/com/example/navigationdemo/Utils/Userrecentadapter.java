package com.example.navigationdemo.Utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.navigationdemo.Pojo.UserRecent;
import com.example.navigationdemo.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Userrecentadapter extends RecyclerView.Adapter<Userrecentadapter.myViewHolder> {
    List<UserRecent> userRecent;
    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView appname,time,username,data;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
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
    public void onBindViewHolder(@NonNull Userrecentadapter.myViewHolder myViewHolder, int i) {
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
