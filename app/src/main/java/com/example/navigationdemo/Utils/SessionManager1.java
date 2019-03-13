package com.example.navigationdemo.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager1 {
    Context context;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    public SessionManager1(Context context){
        this.context=context;
        sharedPreferences=context.getSharedPreferences("RegisteredData",0);
        editor=sharedPreferences.edit();
    }
    public void putData(String key,String name,String password){
        editor.putString("Key",key);
        editor.putString("Name",name);
        editor.putString("Password",password);
        editor.commit();
    }
    public void putId(String InstanceId){
        editor.putString("InstanceId",InstanceId);
        editor.commit();
    }
    public HashMap<String,String> getData(){
        HashMap<String,String> data=new HashMap<String,String>();
        data.put("Name",sharedPreferences.getString("Name",null));
        data.put("Password",sharedPreferences.getString("Password",null));
        data.put("Key",sharedPreferences.getString("Key",null));
        data.put("InstanceId",sharedPreferences.getString("InstanceId",null));
        return data;
    }
}
