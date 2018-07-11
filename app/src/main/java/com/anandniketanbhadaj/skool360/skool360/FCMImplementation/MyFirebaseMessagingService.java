package com.anandniketanbhadaj.skool360.skool360.FCMImplementation;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Activities.LoginActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Belal on 5/27/2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private String screen = "";
    private static Context ctx;
    private static int notifyID = 1;
    String data;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        ctx = this;

        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        Log.d(TAG, "Notification Message Title: " + remoteMessage.getNotification().getTitle());
        Log.d(TAG, "Notification Message Data: " + remoteMessage.getData().toString());
        Log.d(TAG, "Notification Message icon:" + remoteMessage.getNotification().getIcon());
        Log.d(TAG, "Notification Message Notification: " + remoteMessage.getNotification());
        Map<String, String> params = remoteMessage.getData();
        JSONObject object = new JSONObject(params);
        try {
            data = object.getString("type").toString();
            Log.d(TAG, "Megha" + data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("JSON_OBJECT", object.toString());
        sendNotification(remoteMessage);//remoteMessage.getNotification().getBody());
    }

    //This method is only generating push notification
    //It is same as we did in earlier posts 
    private void sendNotification(RemoteMessage remoteMessage) {
        notifyID = (int) (System.currentTimeMillis() & 0xfffffff);

        Intent notificationIntent = new Intent().setClass(ctx, LoginActivity.class);
        notificationIntent.putExtra("fromNotification", data);
        notificationIntent.putExtra("message", remoteMessage.getNotification().getBody());
        notificationIntent.putExtra("cometonotification","true");
        Log.d("Messsagetype", remoteMessage.getNotification().getBody());

        notificationIntent.setAction(String.valueOf(notifyID));
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingNotificationIntent =
                PendingIntent.getActivity(ctx, notifyID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification noti = new NotificationCompat.Builder(ctx)
                .setSmallIcon(R.drawable.ic_launcher)
                .setTicker(remoteMessage.getNotification().getBody())
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Skool 360")//Bhadaj
                .setContentText(remoteMessage.getNotification().getBody())//remoteMessage.getNotification().getBody()
                .setContentIntent(pendingNotificationIntent)
                .setAutoCancel(true).build();

        noti.flags |= Notification.FLAG_ONLY_ALERT_ONCE;
        noti.flags |= Notification.FLAG_AUTO_CANCEL;
        // Play default notification sound
        noti.defaults |= Notification.DEFAULT_SOUND;
        noti.defaults |= Notification.DEFAULT_LIGHTS;

        // Vibrate if vibrate is enabled
        noti.defaults |= Notification.DEFAULT_VIBRATE;
        //Show the notification
        notificationManager.notify(notifyID, noti);
        //Integer.valueOf(push_message_id)

        /*Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentText(messageBody)
                .setContentTitle("Skool 360 Shilaj")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingNotificationIntent);
 
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
 
        notificationManager.notify(0, notificationBuilder.build());*/
    }
}