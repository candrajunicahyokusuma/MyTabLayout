package com.example.mytablayout.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.mytablayout.R;
import com.example.mytablayout.view.activity.MainActivity;


public class ReleaseReceiver extends BroadcastReceiver {
    public static final int NOTIFICATION_ID = 2;
    public static String CHANNEL_ID = "channel_02";
    public static CharSequence CHANNEL_NAME = "dicoding channel 2";
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentToRepeat;
        intentToRepeat = new Intent(context, MainActivity.class);
        int id = intent.getIntExtra(NotificationHelper.ID,0);
        Log.d("nilai",intent.getStringExtra(NotificationHelper.TITLE));
        intentToRepeat.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NotificationHelper.ALARM_TYPE_RTC, intentToRepeat, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder =  new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(pendingIntent)
                .setContentTitle(intent.getStringExtra(NotificationHelper.TITLE))
                .setContentText("Pengingat release")
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (mNotificationManager != null) {
            mNotificationManager.notify(id, notification);
        }

    }
}
