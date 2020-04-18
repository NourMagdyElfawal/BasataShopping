package com.souqmaftoh.basatashopping.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.souqmaftoh.basatashopping.MainActivity;
import com.souqmaftoh.basatashopping.R;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class MyFirebaseMessagingService  extends FirebaseMessagingService {

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.e("newToken", token);
        getSharedPreferences("_", MODE_PRIVATE).edit().putString("fb", token).apply();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String title= Objects.requireNonNull(remoteMessage.getNotification()).getTitle();
        String massage=remoteMessage.getNotification().getBody();

        Intent intent=new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        builder.setContentTitle(title);
        builder.setContentText(massage);
        builder.setSmallIcon(R.drawable.ic_envelope);
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());
        // super.onMessageReceived(remoteMessage);
    }

    public static String getToken(Context context) {
        return context.getSharedPreferences("_", MODE_PRIVATE).getString("fb", "empty");
    }
//TODO Finally, you are able to use a static method from your Service MyFirebaseMessagingService.getToken(Context);
}
