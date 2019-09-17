package com.example.mytablayout.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.mytablayout.model.movie.ResultsItem;
import com.example.mytablayout.view.activity.SetingsRemimberActivity;

import java.util.ArrayList;
import java.util.List;

public class AlarmBootReceiver extends BroadcastReceiver {
    private List<ResultsItem> listMovie = new ArrayList<>();
    NotificationHelper notificationHelper = new NotificationHelper();
    SetingsRemimberActivity setingsRemimberActivity = new SetingsRemimberActivity();
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
            NotificationHelper.scheduleDailyRepeatingElapsedNotification(context);
            notificationHelper.scheduleReleaseRepeatingElapsedNotification(context, setingsRemimberActivity.setMovieRelease());
        }
    }
}
