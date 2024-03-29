package com.example.signupsignin;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.signupsignin.Utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public
class MyFirebaseCloudMessaging extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseCloudMessaging.class.getSimpleName();
    MediaPlayer mp;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e(TAG, "onMessageReceived: 0");
        mp = MediaPlayer.create(this, Settings.System.DEFAULT_NOTIFICATION_URI);

        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "onMessageReceived: ttt");

            String title = remoteMessage.getData().get("title");
            String message = remoteMessage.getData().get("message");

            // Create an explicit intent for an Activity in your app
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE);

            // Build the notification
            // for new version
            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Notification notification = new Notification.Builder(this)
                        .setContentText(title)
//                            .setLargeIcon(bitmap)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.ic_baseline_message_24)
                        .setSubText(message)
                        .setChannelId("channel_id")
                        .build();
                Log.e(TAG, "onMessageReceived: newer");
                nm.createNotificationChannel(new NotificationChannel("channel_id", "My app", NotificationManager.IMPORTANCE_HIGH));
                nm.notify(0, notification);
                mp.start();
                Utils.vibrate(getApplicationContext());
            } else {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                        .setSmallIcon(R.drawable.ic_baseline_message_24)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);
                Log.e(TAG, "onMessageReceived: older");
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                mp.start();
                Utils.vibrate(getApplicationContext());

                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                notificationManager.notify(0, builder.build());
            }
}

    }



    @Override
    public
    void onNewToken(@NonNull String token) {
        updateToken(token);

        Log.d(TAG, "Refreshed token1: " + token);
        super.onNewToken(token);
    }
    private void updateToken(String token) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.getUid());
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            databaseReference.updateChildren(map);
        }

    }


}
