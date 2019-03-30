package com.example.navigationdemo.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.navigationdemo.Pojo.UserHistory;
import com.example.navigationdemo.R;
import com.example.navigationdemo.Utils.Userhistoryadapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Userhistory extends Fragment {

    RecyclerView recyclerView;
    List<UserHistory> history=new ArrayList<>();
    Userhistoryadapter adapter;

    public Userhistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_userhistory, container, false);
        recyclerView=(RecyclerView)v.findViewById(R.id.recycleUserHistory);
        adapter=new Userhistoryadapter(history);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
     //   Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
        prepareData();

        // Inflate the layout for this fragment
        return v;
    }

    public void prepareData() {
        UserHistory h1=new UserHistory("h1","1","Arpita","Your vehicle is serviced by garage1");
        history.add(h1);
        UserHistory h2=new UserHistory("h2","1","Arpita1","Your vehicle is serviced by garage1");
        history.add(h2);
        UserHistory h3=new UserHistory("h3","1","Arpita2","Your vehicle is serviced by garage1");
        history.add(h3);
        UserHistory h4=new UserHistory("h4","1","Arpita3","Your vehicle is serviced by garage1");
        history.add(h4);
        UserHistory h5=new UserHistory("h5","1","Arpita4","Your vehicle is serviced by garage1");
        history.add(h5);
        UserHistory h6=new UserHistory("h6","1","Arpita5","Your vehicle is serviced by garage1");
        history.add(h6);
        UserHistory h7=new UserHistory("h7","1","Arpita6","Your vehicle is serviced by garage1");
        history.add(h7);
        UserHistory h8=new UserHistory("h8","1","Arpita7","Your vehicle is serviced by garage1");
        history.add(h8);
        UserHistory h9=new UserHistory("h9","1","Arpita8","Your vehicle is serviced by garage1");
        history.add(h9);
        UserHistory h10=new UserHistory("h10","1","Arpita9","Your vehicle is serviced by garage1");
        history.add(h10);
        }

}
