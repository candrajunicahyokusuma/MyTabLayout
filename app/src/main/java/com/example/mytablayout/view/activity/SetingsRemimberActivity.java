package com.example.mytablayout.view.activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.mytablayout.R;
import com.example.mytablayout.model.movie.FilmResponse;
import com.example.mytablayout.model.movie.ResultsItem;
import com.example.mytablayout.notification.NotificationHelper;
import com.example.mytablayout.service.MovieService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mytablayout.Utils.KEY_DAILY_REMINDER;
import static com.example.mytablayout.Utils.KEY_RELEASE_REMINDER;
import static com.example.mytablayout.Utils.STATE_DAILY_REMINDER;
import static com.example.mytablayout.Utils.STATE_RELEASE_REMINDER;

public class SetingsRemimberActivity extends AppCompatActivity {
private Switch switchRelease, switchDaily;
    private SharedPreferences spDailyReminder, spReleaseReminder;
    private SharedPreferences.Editor editorDailyReminder, editorReleaseReminder;

    boolean setStateDailyReminder, setStateReleaseReminder;
    private List<ResultsItem> listMovie = new ArrayList<>();
    private List<ResultsItem> movie = new ArrayList<>();
    private MovieService movieService;
    NotificationHelper notificationHelper= new NotificationHelper();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setings_remimber);
        switchRelease = findViewById(R.id.switch_release_reminder);
        switchDaily = findViewById(R.id.switch_daily_reminder);



        switchDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editorDailyReminder = spDailyReminder.edit();
                if (isChecked){
                    editorDailyReminder.putBoolean(STATE_DAILY_REMINDER, true);
                    editorDailyReminder.apply();
                    switchDaily.setText("On");
                    NotificationHelper.scheduleDailyRepeatingRTCNotification(getApplicationContext(),"11","12");
                    NotificationHelper.enableBootReceiver(getApplicationContext());
                }
                else{
                    editorDailyReminder.putBoolean(STATE_DAILY_REMINDER, false);
                    editorDailyReminder.apply();
                    switchDaily.setText("Off");
                    NotificationHelper.cancelAlarmDailyRTC();
                    NotificationHelper.disableBootReceiver(getApplicationContext());
                }

            }
        });

        if (switchDaily.isChecked()){
            switchDaily.setText("On");
            NotificationHelper.scheduleDailyRepeatingRTCNotification(getApplicationContext(),"8","0");
            NotificationHelper.enableBootReceiver(getApplicationContext());
        }
        else
        {
            switchDaily.setText("Off");
            NotificationHelper.cancelAlarmDailyRTC();
            NotificationHelper.disableBootReceiver(getApplicationContext());
        }
        movie = setMovieRelease();

        switchRelease.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)  {
                editorReleaseReminder = spReleaseReminder.edit();
                if (isChecked){
                    editorReleaseReminder.putBoolean(STATE_RELEASE_REMINDER, true);
                    editorReleaseReminder.apply();
                    switchRelease.setText("On");

                    notificationHelper.scheduleReleaseRepeatingRTCNotification(getApplicationContext(),movie);
                    NotificationHelper.enableBootReceiver(getApplicationContext());
                }
                else{
                    editorReleaseReminder.putBoolean(STATE_RELEASE_REMINDER, false);
                    editorReleaseReminder.apply();
                    switchRelease.setText("Off");
                    NotificationHelper.cancelAlarmReleaseRTC();
                    NotificationHelper.disableBootReceiver(getApplicationContext());
                }
            }
        });


        setSharedPreferences();

        if (switchRelease.isChecked()){
            switchRelease.setText("On");
            notificationHelper.scheduleReleaseRepeatingRTCNotification(getApplicationContext(),movie);
            NotificationHelper.enableBootReceiver(getApplicationContext());
        }
        else{
            switchRelease.setText("Off");
            NotificationHelper.cancelAlarmReleaseRTC();
            NotificationHelper.disableBootReceiver(getApplicationContext());
        }
    }
    public List<ResultsItem> setMovieRelease(){
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        final String dateNow = simpleDateFormat.format(date);
        if (movieService == null){
            movieService = new MovieService();
        }
        movieService.getFilmApi().getFilmsRelease(dateNow, dateNow).enqueue(new Callback<FilmResponse>() {
            @Override
            public void onResponse(Call<FilmResponse> call, Response<FilmResponse> response) {
                if (response.isSuccessful()){
                    listMovie.clear();
                    listMovie.addAll(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<FilmResponse> call, Throwable t) {

            }
        });


        return listMovie;


    }

    private void setSharedPreferences() {
        spDailyReminder = getSharedPreferences(KEY_DAILY_REMINDER, MODE_PRIVATE);
        setStateDailyReminder = spDailyReminder.getBoolean(STATE_DAILY_REMINDER, false);
        switchDaily.setChecked(setStateDailyReminder);
        spReleaseReminder = getSharedPreferences(KEY_RELEASE_REMINDER, MODE_PRIVATE);
        setStateReleaseReminder = spReleaseReminder.getBoolean(STATE_RELEASE_REMINDER, false);
        switchRelease.setChecked(setStateReleaseReminder);
    }
}
