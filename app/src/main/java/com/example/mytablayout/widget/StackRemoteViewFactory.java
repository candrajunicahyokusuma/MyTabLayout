package com.example.mytablayout.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.mytablayout.R;
import com.example.mytablayout.database.AplicationDatabase;
import com.example.mytablayout.database.MovieDB;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.provider.Settings.System.CONTENT_URI;

public class StackRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private final ArrayList<MovieDB> widgetItem = new ArrayList<>();
    Cursor cursor;
    private final Context mContext;
    AplicationDatabase aplicationDatabase;
    public StackRemoteViewFactory(Context applicationContext) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null){
            cursor.close();
        }
        final long identityToken = Binder.clearCallingIdentity();


            aplicationDatabase = AplicationDatabase.initDatabase(mContext);


        widgetItem.addAll(aplicationDatabase.movieDAO().getMovieDB());
        cursor = mContext.getContentResolver().query(CONTENT_URI, null, null, null, null);
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return widgetItem.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        URL url;
        Bitmap myBitmap = null;
        try {
            url = new URL("https://image.tmdb.org/t/p/w154"+widgetItem.get(position).getPosterPath());

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            myBitmap = BitmapFactory.decodeStream(input);
        } catch (IOException io) {
            Log.e("Salah", io.getMessage());
        }
        //rViews.setImageViewBitmap(R.id.img_widget,myBitmap);
        rViews.setImageViewBitmap(R.id.imageView, myBitmap);

        Bundle extras = new Bundle();
        extras.putInt(FavoriteMTvWidget.EXTRA_ITEM, position);

        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);

        rViews.setOnClickFillInIntent(R.id.imageView, fillIntent);
        return rViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
