package com.example.lighthouse.model;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.lighthouse.R;
import com.example.lighthouse.ui.affirmations.AffirmationsFragment;

import androidx.core.app.NotificationCompat;


public class NotificationPusher extends BroadcastReceiver {
    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";
    public static final String notification_channel_id = "0425";
    private NotificationCompat.Builder builder;
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //Create the notification channel
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(notification_channel_id, "NOTIFICATION_CHANNEL_NAME", importance);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);

        Intent repeatingIntent = new Intent(context, AffirmationsFragment.class);
        repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(context, 100, repeatingIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
        }else {
            pendingIntent = PendingIntent.getBroadcast(context, 100, repeatingIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        builder = new NotificationCompat.Builder(context, notification_channel_id);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.mipmap.lighthouse_launcher);
        builder.setContentTitle("Friendly Reminder!");
        builder.setContentText(intent.getAction());
        builder.setAutoCancel(true);
        notificationManager.notify(100, builder.build());
//        if (intent.getAction().equals("MY_NOTIFICATION_MESSAGE")) {
//            notificationManager.notify(100, builder.build());
//        }
    }
}
