package com.example.navigationdemo.Firebase;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.navigationdemo.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    int NOTIFICATION_ID = 101;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        super.onMessageReceived(remoteMessage);
        Log.d("Remote Message", remoteMessage.getFrom());

        if (remoteMessage == null) {
            return;
        }
        if (remoteMessage.getNotification() != null) {
            Log.d("NotificationBody", remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }
        if (remoteMessage.getData().size() > 0) {
            Log.d("Data", remoteMessage.getData().toString());
            try {
                JSONObject jsonObject = new JSONObject(remoteMessage.getData().toString());
                handledataMessage(jsonObject);
            } catch (Exception e) {
                Log.d("Error", e.getMessage());
            }
        }
    }

    private void handledataMessage(JSONObject jsonObject) {
        Log.d("Notification",""+jsonObject.toString());
    }


    private void handleNotification(String body) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Notification notification = builder
                .setSmallIcon(R.mipmap.ic_launcher_foreground)
                .setAutoCancel(true)
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
}
