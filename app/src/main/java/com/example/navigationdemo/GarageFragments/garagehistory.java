package com.example.navigationdemo.GarageFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.navigationdemo.Pojo.UserRecent;
import com.example.navigationdemo.R;
import com.example.navigationdemo.Utils.Userrecentadapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class garagehistory extends Fragment {

    RecyclerView recyclerView;
    List<UserRecent> recent=new ArrayList<>();
    Userrecentadapter adapter;

    public garagehistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_garagehistory, container, false);

        recyclerView=(RecyclerView)v.findViewById(R.id.recycleGarageHistory);
        adapter=new Userrecentadapter(recent);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
      //  Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
        prepareData();
        // Inflate the layout for this fragment
        return v;
    }

    private void prepareData() {

        UserRecent h1=new UserRecent("h1","1","Arpita","Your vehicle is serviced by garage1");
        recent.add(h1);
        UserRecent h2=new UserRecent("h2","1","Arpita1","Your vehicle is serviced by garage1");
        recent.add(h2);
        UserRecent h3=new UserRecent("h3","1","Arpita2","Your vehicle is serviced by garage1");
        recent.add(h3);
        UserRecent h4=new UserRecent("h4","1","Arpita3","Your vehicle is serviced by garage1");
        recent.add(h4);
        UserRecent h5=new UserRecent("h1","1","Arpita4","Your vehicle is serviced by garage1");
        recent.add(h5);
        UserRecent h6=new UserRecent("h1","1","Arpita5","Your vehicle is serviced by garage1");
        recent.add(h6);
        UserRecent h7=new UserRecent("h1","1","Arpita6","Your vehicle is serviced by garage1");
        recent.add(h7);
        UserRecent h8=new UserRecent("h1","1","Arpita7","Your vehicle is serviced by garage1");
        recent.add(h8);
        UserRecent h9=new UserRecent("h1","1","Arpita8","Your vehicle is serviced by garage1");
        recent.add(h9);
        UserRecent h10=new UserRecent("h1","1","Arpita9","Your vehicle is serviced by garage1");
        recent.add(h10);
    }

}
