package com.example.navigationdemo.Firebase;


import android.util.Log;

import com.example.navigationdemo.Utils.SessionManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

  SessionManager sessionManager;
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
        Log.d("InstanceId",refreshToken);

    }
}
