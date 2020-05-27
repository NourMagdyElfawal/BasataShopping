package com.souqmaftoh.basatashopping.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.souqmaftoh.basatashopping.MainActivity;
import com.souqmaftoh.basatashopping.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class MyFirebaseMessagingService  extends FirebaseMessagingService {
    private NotificationChannel mChannel;
    private NotificationManager notifManager;
    String title,massage,ad_key;

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.e("newToken", token);
        getSharedPreferences("_", MODE_PRIVATE).edit().putString("fb", token).apply();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
    if(remoteMessage.getNotification()!=null) {
         title = remoteMessage.getNotification().getTitle();
         massage = remoteMessage.getNotification().getBody();
        Log.e("DATA",remoteMessage.getData().toString());

        try {
            Map<String, String> params = remoteMessage.getData();
            JSONObject object = new JSONObject(params);
            Log.e("JSON OBJECT", object.toString());

                 ad_key = object.getString("ad_key");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //rest of the code

    }

        if (notifManager == null) {
            notifManager = (NotificationManager) getSystemService
                    (Context.NOTIFICATION_SERVICE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder builder;
            Intent intent = new Intent(this, MainActivity.class);
            if(ad_key!=null&&!ad_key.isEmpty()){
                intent.putExtra("ad_key",ad_key);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            if (mChannel == null) {
                mChannel = new NotificationChannel
                        ("0", title, importance);
                // Sets whether notifications posted to this channel should display notification lights
                mChannel.enableLights(true);
                // Sets whether notification posted to this channel should vibrate.
                mChannel.enableVibration(true);
                // Sets the notification light color for notifications posted to this channel
                mChannel.setLightColor(Color.CYAN);
                // Sets whether notifications posted to this channel appear on the lockscreen or not
                mChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

                mChannel.setDescription(massage);
                mChannel.enableVibration(true);
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(this, "0");

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(this, 1251, intent, PendingIntent.FLAG_ONE_SHOT);
            builder.setContentTitle(title)
                    .setSmallIcon(R.drawable.ic_envelope) // required
                    .setContentText(massage)  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
//                    .setLargeIcon(BitmapFactory.decodeResource
//                            (getResources(), R.drawable.logo))
                    .setContentIntent(pendingIntent)
                    .setSound(RingtoneManager.getDefaultUri
                            (RingtoneManager.TYPE_NOTIFICATION));
            Notification notification = builder.build();
            notifManager.notify(0, notification);
        } else {


            Intent intent = new Intent(this, MainActivity.class);
            if(ad_key!=null&&!ad_key.isEmpty()){
                intent.putExtra("ad_key",ad_key);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "M_CH_ID");
            builder.setContentTitle(title);
            builder.setContentText(massage);
            builder.setSmallIcon(R.drawable.ic_envelope);
//            builder.setLargeIcon(BitmapFactory.decodeResource
//                    (getResources(), R.drawable.logo));
            builder.setAutoCancel(true);
            builder.setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.notify(0, builder.build());
            }
            // super.onMessageReceived(remoteMessage);
        }
    }
    public static String getToken(Context context) {
        return context.getSharedPreferences("_", MODE_PRIVATE).getString("fb", "empty");
    }
//TODO Finally, you are able to use a static method from your Service MyFirebaseMessagingService.getToken(Context);
}
