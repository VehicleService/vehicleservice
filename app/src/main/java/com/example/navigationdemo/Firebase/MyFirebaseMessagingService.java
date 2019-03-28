package com.example.navigationdemo.Firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.navigationdemo.GarageActivity.UserdetailsActivity;
import com.example.navigationdemo.Pojo.Notificationdetails;
import com.example.navigationdemo.R;
import com.example.navigationdemo.Utils.SessionManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    Intent intent;
    int NOTIFICATION_ID = 101;
    String NOTIFICATION_CHANNEL_ID = "com.example.navigationdemo";
    String NOTIFICATION_CHANNEL_NAME = "NavigationDemo";
    Notificationdetails notificationdetails;
    String[] data;
    JSONObject jsonObject;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        super.onMessageReceived(remoteMessage);
        Log.d("Remote Message", remoteMessage.getFrom());

        if (remoteMessage == null) {
            return;
        }
        if (remoteMessage.getData().size() > 0) {
            Log.d("Data", remoteMessage.getData().toString());


                jsonObject = new JSONObject(remoteMessage.getData());




        }
        if (remoteMessage.getNotification() != null) {
            Log.d("NotificationBody", remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }

    }

    private void handledataMessage(JSONObject jsonObject) {
        Log.d("Notification",""+jsonObject.toString());

        try {
            notificationdetails=new Notificationdetails(jsonObject.getString("user_id"),jsonObject.getString("name"),jsonObject.getString("phone"),
                    jsonObject.getString("service_type"),jsonObject.getString("vehicle_type"),jsonObject.getString("latitude"),
                    jsonObject.getString("longitude"));
            Log.d("notificationdetails",jsonObject.getString("name")+jsonObject.getString("phone")+
                    jsonObject.getString("service_type")+jsonObject.getString("vehicle_type")+jsonObject.getString("latitude")+
                    jsonObject.getString("longitude"));
            intent=new Intent(this,UserdetailsActivity.class);
            intent.putExtra("notificationdetails",notificationdetails);
            Log.d("Called","yes");
        } catch (Exception e) {
           Log.d("Exception",e.getMessage());
        }
    }


    private void handleNotification(String body) {
        data=body.split(" ");
        Log.d("notarr",data[0]);
        Log.d("notarr1",data[1]);
        Log.d("notarr2",data[2]);
        Log.d("notarr3",data[3]);
        Log.d("notarr4",data[4]);
        Log.d("notarr5",data[5]);
        Log.d("notarr6",data[6]);
        Log.d("notarr7",data[7]);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);


        builder.setSmallIcon(R.mipmap.ic_launcher_foreground)
                .setAutoCancel(true)
                .setContentText(body);

        NotificationManager notificationManager = (NotificationManager) getApplication().getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            int importance=NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel=new NotificationChannel(NOTIFICATION_CHANNEL_ID,NOTIFICATION_CHANNEL_NAME,importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        if(data[2].equalsIgnoreCase("request")) {
            handledataMessage(jsonObject);
            PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,0);
                builder.setContentIntent(pendingIntent)
                    .setContentTitle("Garage");

//                .setContentIntent(resultPendingIntent)
//                .setSound(alarmSound)
//                .setStyle(inboxStyle)
//                .setWhen(getTimeMilliSec(timeStamp))
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))

            Log.d("send","success");
        }
        else {
            builder.setContentTitle("User");

//                .setContentIntent(resultPendingIntent)
//                .setSound(alarmSound)
//                .setStyle(inboxStyle)
//                .setWhen(getTimeMilliSec(timeStamp))
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))


            Log.d("send1","Success1");
        }

        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }
}
