package com.example.mytablayout.notification;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.util.Log;

import com.example.mytablayout.R;
import com.example.mytablayout.model.movie.FilmResponse;
import com.example.mytablayout.model.movie.ResultsItem;
import com.example.mytablayout.service.MovieService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationHelper {
    public static int ALARM_TYPE_RTC = 100;
    private static AlarmManager alarmManagerRTCDaily;
    private static PendingIntent alarmIntentRTCDaily;
    public static  final String EXTRA_NAME = "name";
    public static int ALARM_TYPE_ELAPSED = 101;
    private static AlarmManager alarmManagerElapsedDaily;
    private static PendingIntent alarmIntentElapsedDaily;

    private static AlarmManager alarmManagerRTCRelease;
    private static PendingIntent alarmIntentRTCRelease;

    private static AlarmManager alarmManagerElapsedRelease;
    private static PendingIntent alarmIntentElapsedRelease;

    public static final String ID = "id";
    public static final String TITLE = "title";
    private int id;

    public static void scheduleDailyRepeatingRTCNotification(Context context, String hour, String min){

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);

        Intent intent = new Intent(context, DailyReceiver.class);

        alarmIntentRTCDaily = PendingIntent.getBroadcast(context,ALARM_TYPE_RTC, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManagerRTCDaily = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManagerRTCDaily.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis() +200, AlarmManager.INTERVAL_DAY,alarmIntentRTCDaily);

    }



    public void scheduleReleaseRepeatingRTCNotification(Context context, List<ResultsItem> listMovie){

        for (ResultsItem movieItem : listMovie){
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Log.d("contoh",movieItem.getTitle());
            Intent intent = new Intent(context, ReleaseReceiver.class);
            intent.putExtra(ID, id);
            intent.putExtra(TITLE, movieItem.getTitle());
            alarmIntentRTCRelease = PendingIntent.getBroadcast(context,id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManagerRTCRelease = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManagerRTCRelease.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis() +200, AlarmManager.INTERVAL_DAY,alarmIntentRTCRelease);
            id = id+1;
        }
    }
    public void scheduleReleaseRepeatingElapsedNotification(Context context, List<ResultsItem> listMovie){
        for (ResultsItem movieItem : listMovie){
            Log.d("contoh",movieItem.getTitle());
            Intent intent = new Intent(context, ReleaseReceiver.class);
            intent.putExtra(ID, id);
            intent.putExtra(TITLE, movieItem.getTitle());
            alarmIntentElapsedRelease = PendingIntent.getBroadcast(context,id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManagerElapsedRelease = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManagerElapsedRelease.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() +  AlarmManager.INTERVAL_FIFTEEN_MINUTES, AlarmManager.INTERVAL_FIFTEEN_MINUTES, alarmIntentElapsedRelease);
            id = id+1;
        }
    }

    public static void scheduleDailyRepeatingElapsedNotification(Context context){
        Intent intent = new Intent(context, DailyReceiver.class);

        alarmIntentElapsedDaily = PendingIntent.getBroadcast(context, ALARM_TYPE_ELAPSED, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManagerElapsedDaily = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManagerElapsedDaily.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() +  AlarmManager.INTERVAL_FIFTEEN_MINUTES, AlarmManager.INTERVAL_FIFTEEN_MINUTES, alarmIntentElapsedDaily);


    }

    public static void cancelAlarmDailyRTC(){
        if (alarmManagerRTCDaily != null){
            alarmManagerRTCDaily.cancel(alarmIntentRTCDaily);
        }
    }

    public static void cancelAlarmReleaseRTC(){
        if (alarmManagerRTCRelease != null){
            alarmManagerRTCRelease.cancel(alarmIntentRTCRelease);
        }
    }

    public static NotificationManager getNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }


    public static void enableBootReceiver(Context context) {
        ComponentName receiver = new ComponentName(context, AlarmBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    /**
     * Disable boot receiver when user cancels/opt-out from notifications
     */
    public static void disableBootReceiver(Context context) {
        ComponentName receiver = new ComponentName(context, AlarmBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

}
