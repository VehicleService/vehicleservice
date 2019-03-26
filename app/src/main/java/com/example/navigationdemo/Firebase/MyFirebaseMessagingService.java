package com.example.navigationdemo.Firebase;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.navigationdemo.GarageActivity.UserdetailsActivity;
import com.example.navigationdemo.Pojo.Notificationdetails;
import com.example.navigationdemo.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    Intent intent;
    int NOTIFICATION_ID = 101;
    Notificationdetails notificationdetails;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        super.onMessageReceived(remoteMessage);
        Log.d("Remote Message", remoteMessage.getFrom());

        if (remoteMessage == null) {
            return;
        }
        if (remoteMessage.getData().size() > 0) {
            Log.d("Data", remoteMessage.getData().toString());
            try {
                JSONObject jsonObject = new JSONObject(remoteMessage.getData());
                handledataMessage(jsonObject);
            } catch (Exception e) {
                Log.d("Error", e.getMessage());
            }
        }
        if (remoteMessage.getNotification() != null) {
            Log.d("NotificationBody", remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }

    }

    private void handledataMessage(JSONObject jsonObject) {
        Log.d("Notification",""+jsonObject.toString());
        try {
            notificationdetails=new Notificationdetails(jsonObject.getString("name"),jsonObject.getString("phone"),
                    jsonObject.getString("service_type"),jsonObject.getString("vehicle_type"),jsonObject.getString("latitude"),
                    jsonObject.getString("longitude"));
            Log.d("notificationdetails",jsonObject.getString("name")+jsonObject.getString("phone")+
                    jsonObject.getString("service_type")+jsonObject.getString("vehicle_type")+jsonObject.getString("latitude")+
                    jsonObject.getString("longitude"));
            intent=new Intent(this,UserdetailsActivity.class);
            intent.putExtra("notificationdetails",notificationdetails);
        } catch (Exception e) {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void handleNotification(String body) {
        String[] data=body.split(" ");
        Log.d("notarr",data[0]);
        Log.d("notarr1",data[1]);
        Log.d("notarr2",data[2]);
        Log.d("notarr3",data[3]);
        Log.d("notarr4",data[4]);
        Log.d("notarr5",data[5]);
        Log.d("notarr6",data[6]);
        Log.d("notarr7",data[7]);



        if(data[2].equalsIgnoreCase("request")) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

            PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,0);
            Notification notification = builder
                    .setSmallIcon(R.mipmap.ic_launcher_foreground)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setContentText(body)
                    .setContentTitle("Garage")
                    .build();


//                .setContentIntent(resultPendingIntent)
//                .setSound(alarmSound)
//                .setStyle(inboxStyle)
//                .setWhen(getTimeMilliSec(timeStamp))
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))

            NotificationManager notificationManager = (NotificationManager) getApplication().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFICATION_ID, notification);
        }
        else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            Notification notification = builder
                    .setSmallIcon(R.mipmap.ic_launcher_foreground)
                    .setAutoCancel(true)
                    .setContentText(body)
                    .setContentTitle("User")
                    .build();


//                .setContentIntent(resultPendingIntent)
//                .setSound(alarmSound)
//                .setStyle(inboxStyle)
//                .setWhen(getTimeMilliSec(timeStamp))
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))

            NotificationManager notificationManager = (NotificationManager) getApplication().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFICATION_ID, notification);

        }

    }
}
