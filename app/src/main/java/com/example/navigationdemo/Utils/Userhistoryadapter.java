package com.example.navigationdemo.Utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.navigationdemo.Pojo.UserHistory;
import com.example.navigationdemo.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Userhistoryadapter extends RecyclerView.Adapter<Userhistoryadapter.MyviewHolder> {

    List<UserHistory> userHistoryList;
    public class MyviewHolder extends RecyclerView.ViewHolder{
        TextView appname,time,username,data;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            appname=(TextView)itemView.findViewById(R.id.Appname);
            time=(TextView)itemView.findViewById(R.id.Time);
            username=(TextView)itemView.findViewById(R.id.Username);
            data=(TextView)itemView.findViewById(R.id.Data);

        }
    }

    public Userhistoryadapter(List<UserHistory> userHistoryList) {
        this.userHistoryList = userHistoryList;
    }

    @NonNull
    @Override
    public Userhistoryadapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_first_list,viewGroup,false);
        return new MyviewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull Userhistoryadapter.MyviewHolder myviewHolder, int i) {
        UserHistory user=userHistoryList.get(i);
        myviewHolder.appname.setText("Vehicle Service Booking");
        myviewHolder.time.setText(new SimpleDateFormat("HH:mm:ss a dd/MM/yyyy").format(Calendar.getInstance().getTime()));
        myviewHolder.username.setText(user.getUsername());
        myviewHolder.data.setText(user.getData());
    }

    @Override
    public int getItemCount() {
        return userHistoryList.size();
    }
}
