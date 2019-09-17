package com.example.mytablayout.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.mytablayout.adapter.MainFragmentPagerAdapter;
import com.example.mytablayout.R;
import com.example.mytablayout.fragment.FavoriteFragment;
import com.example.mytablayout.fragment.MovieFragment;
import com.example.mytablayout.fragment.TvFragment;
import com.example.mytablayout.notification.NotificationHelper;

import static com.example.mytablayout.Utils.KEY_DAILY_REMINDER;
import static com.example.mytablayout.Utils.KEY_RELEASE_REMINDER;
import static com.example.mytablayout.Utils.STATE_DAILY_REMINDER;
import static com.example.mytablayout.Utils.STATE_RELEASE_REMINDER;

public class MainActivity extends AppCompatActivity {
    private Fragment selectedFragmen = new MovieFragment();
    String fragment = "movie";
    boolean setStateDailyReminder, setStateReleaseReminder;
    private SharedPreferences spDailyReminder, spReleaseReminder;
    public static final String KEY_FRAGMENT = "fragment";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        NotificationHelper notificationHelper = new NotificationHelper();
        setSharedPreferences();


        if (savedInstanceState == null){

            loadFragment(selectedFragmen);

        }
        else {

            selectedFragmen = getSupportFragmentManager().getFragment(savedInstanceState,KEY_FRAGMENT);
            loadFragment(selectedFragmen);
        }



    }

    private void setSharedPreferences() {
        spDailyReminder = getSharedPreferences(KEY_DAILY_REMINDER, MODE_PRIVATE);
        setStateDailyReminder = spDailyReminder.getBoolean(STATE_DAILY_REMINDER, false);

        spReleaseReminder = getSharedPreferences(KEY_RELEASE_REMINDER, MODE_PRIVATE);
        setStateReleaseReminder = spReleaseReminder.getBoolean(STATE_RELEASE_REMINDER, false);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener(){

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.movie_menu :
                    selectedFragmen = new MovieFragment();
                    fragment = "movie";
                    loadFragment(selectedFragmen);
                    return true;
                case R.id.tv_menu :
                    selectedFragmen = new TvFragment();
                    fragment = "tv";
                    loadFragment(selectedFragmen);
                    return true;
                case R.id.favorite_menu :
                    selectedFragmen = new FavoriteFragment();
                    fragment = "favorite";
                    loadFragment(selectedFragmen);
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState,KEY_FRAGMENT,selectedFragmen);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment,fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();

            return true;
        }
        else {
            return false;
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        if (item.getItemId() == R.id.search_menu_activity){
            if (fragment.equals("movie")){
                item.setEnabled(true);
                Intent intent = new Intent(this, SearchMovieActivity.class);
                startActivity(intent);
            }
            if (fragment.equals("tv")){
                item.setEnabled(true);
                Intent intent = new Intent(this, SearchTvActivity.class);
                startActivity(intent);
            }
            if (fragment.equals("favorite")){
                item.setEnabled(false);
            }


        }
        if (item.getItemId() == R.id.setings_rimember){
            Intent intent = new Intent(this, SetingsRemimberActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }



}
