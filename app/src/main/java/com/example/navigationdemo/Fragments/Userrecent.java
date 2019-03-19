package com.example.navigationdemo.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.navigationdemo.Pojo.UserRecent;
import com.example.navigationdemo.R;
import com.example.navigationdemo.Utils.Userrecentadapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Userrecent extends Fragment {
    
    RecyclerView recyclerView;
    List<UserRecent> userRecent=new ArrayList<>();
    Userrecentadapter adapter;
    

    public Userrecent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_userrecent, container, false);
        recyclerView=(RecyclerView)v.findViewById(R.id.recycleUserRecent);
        adapter=new Userrecentadapter(userRecent);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        
        prepareData();
        
        // Inflate the layout for this fragment
        return v;
    }

    private void prepareData() {
        UserRecent h1=new UserRecent("h1","1","Arpita","Your vehicle is serviced by garage1");
        userRecent.add(h1);
        UserRecent h2=new UserRecent("h2","1","Arpita","Your vehicle is serviced by garage1");
        userRecent.add(h2);
        UserRecent h3=new UserRecent("h3","1","Arpita","Your vehicle is serviced by garage1");
        userRecent.add(h3);
        UserRecent h4=new UserRecent("h4","1","Arpita","Your vehicle is serviced by garage1");
        userRecent.add(h4);
        UserRecent h5=new UserRecent("h5","1","Arpita","Your vehicle is serviced by garage1");
        userRecent.add(h5);
        UserRecent h6=new UserRecent("h6","1","Arpita","Your vehicle is serviced by garage1");
        userRecent.add(h6);
        UserRecent h7=new UserRecent("h7","1","Arpita","Your vehicle is serviced by garage1");
        userRecent.add(h7);
        UserRecent h8=new UserRecent("h8","1","Arpita","Your vehicle is serviced by garage1");
        userRecent.add(h8);
        UserRecent h9=new UserRecent("h9","1","Arpita","Your vehicle is serviced by garage1");
        userRecent.add(h9);
        UserRecent h10=new UserRecent("h10","1","Arpita","Your vehicle is serviced by garage1");
        userRecent.add(h10);
    }

}
