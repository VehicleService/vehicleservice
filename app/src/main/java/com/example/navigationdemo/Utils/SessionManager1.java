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
    public String getId(){
        return sharedPreferences.getString("InstanceId",null);
    }
    public HashMap<String,String> getData(){
        HashMap<String,String> data=new HashMap<String,String>();
        data.put("Name",sharedPreferences.getString("Name",null));
        data.put("Password",sharedPreferences.getString("Password",null));
        data.put("Key",sharedPreferences.getString("Key",null));

        return data;
    }
    public void putapidata(String id,String email,String sessionid,String sid1,String service1,String sid2,String service2,String sid3,String service3,
                           String vid1,String vehicle1,String vid2,String vehicle2,String vid3,String vehicle3){
        editor.putString("id",id);
        editor.putString("email",email);
        editor.putString("sessionid",sessionid);
        editor.putString(service1,sid1);
        editor.putString(service2,sid2);
        editor.putString(service3+"s",sid3);
        editor.putString(vehicle1,vid1);
        editor.putString(vehicle2,vid2);
        editor.putString(vehicle3+"v",vid3);
        editor.commit();
    }

    public boolean readStatus(){
        boolean status=false;
        status=sharedPreferences.getBoolean("Status",false);
        return status;
    }
    public void writeStatus(boolean status){
        editor.putBoolean("Status",true);
        editor.commit();
    }
}
