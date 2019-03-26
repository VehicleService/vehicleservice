package com.example.navigationdemo.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.List;

public class SessionManager {
    Context context;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    public SessionManager(Context context){
        this.context=context;
        sharedPreferences=context.getSharedPreferences("RegisteredData",Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }
    public void putData(String key,String name,String password){
       editor.putString("Key",key);
        editor.putString("Name",name);
        editor.putString("Password",password);

        editor.commit();
    }
    public  void putId(String instanceId){

        editor.putString("InstanceId",instanceId);

        editor.commit();
    }

    public String getId(){
        return  sharedPreferences.getString("InstanceId", null);
    }

    public void putKeys(String vkey,String skey){
        editor.putString("Vkey",vkey);
        editor.putString("Skey",skey);
        editor.commit();
    }
    public void clear(){
        editor.clear();
        editor.commit();
    }
    public HashMap<String,String> getData(){
        HashMap<String,String> data=new HashMap<String,String>();
        data.put("Name",sharedPreferences.getString("Name",null));
        data.put("Password",sharedPreferences.getString("Password",null));
        data.put("Key",sharedPreferences.getString("Key",null));
        data.put("Vkey",sharedPreferences.getString("Vkey",null));
        data.put("Skey",sharedPreferences.getString("Skey",null));
        data.put("InstanceId",sharedPreferences.getString("InstanceId",null));
        return data;
    }
    public boolean readStatus(){
        boolean status=false;
        status=sharedPreferences.getBoolean("Status",false);
        return status;
    }
    public void writeStatus(Boolean status){
        editor.putBoolean("Status",status);
        editor.commit();
    }
    public HashMap<String,String> getapidata(){
        HashMap<String,String> data=new HashMap<>();
        data.put("two-wheeler",sharedPreferences.getString("two-wheeler",null));
        data.put("four-wheeler",sharedPreferences.getString("four-wheeler",null));
        data.put("regular",sharedPreferences.getString("regular",null));
        data.put("emergency",sharedPreferences.getString("emergency",null));
        data.put("id",sharedPreferences.getString("id",null));
        return data;

    }
    public void setserveh(String serviceid,String vehicleid){
        editor.putString("service_id",serviceid);
        editor.putString("vehicle_id",vehicleid);
        editor.commit();
    }
    public HashMap<String,String> getserveh(){
        HashMap<String,String> data=new HashMap<>();
        data.put("service_id",sharedPreferences.getString("service_id",null));
        data.put("vehicle_id",sharedPreferences.getString("vehicle_id",null));
        return data;
    }
    public void putapidata(String id,String email,String sessionid,String sid1,String service1,String sid2,String service2,
                           String vid1,String vehicle1,String vid2,String vehicle2){
        editor.putString("id",id);
        editor.putString("email",email);
        editor.putString("sessionid",sessionid);
        editor.putString(service1,sid1);
        editor.putString(service2,sid2);

        editor.putString(vehicle1,vid1);
        editor.putString(vehicle2,vid2);

        editor.commit();
    }
}
