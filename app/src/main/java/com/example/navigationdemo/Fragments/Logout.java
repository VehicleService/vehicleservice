package com.example.navigationdemo.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.navigationdemo.R;
import com.example.navigationdemo.Utils.SessionManager;
import com.example.navigationdemo.activity.LoginActivity;
import com.example.navigationdemo.activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class Logout extends Fragment {


    public Logout() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_logout, container, false);

        // Inflate the layout for this fragment
        return v;
    }

}
