package com.example.navigationdemo.Firebase;


import android.util.Log;

import com.example.navigationdemo.Utils.SessionManager;
import com.example.navigationdemo.Utils.SessionManager1;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

  SessionManager sessionManager;
  SessionManager1 sessionManager1;
    String refreshToken;
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
         refreshToken=FirebaseInstanceId.getInstance().getToken();

        storeIdinPref();
    }

    private void storeIdinPref() {
        sessionManager=new SessionManager(getApplicationContext());
        sessionManager.putId(refreshToken);
        sessionManager1=new SessionManager1(getApplicationContext());
        sessionManager1.putId(refreshToken);

        Log.d("InstanceId",refreshToken);

    }
}
