package com.wb.largestfans.application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.util.Log;


import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.RequestHandler;
import com.wb.largestfans.retrofit.apimodel.PhotoAlbum;

import java.io.IOException;
import java.util.List;

public class RoyalStagApplication extends Application {


    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public String getSaveProfilePic() {
        return saveProfilePic;
    }

    public void setSaveProfilePic(String saveProfilePic) {
        this.saveProfilePic = saveProfilePic;
    }

    public List<PhotoAlbum> getDailyPhotoUrlList() {
        return dailyPhotoUrlList;
    }

    public void setDailyPhotoUrlList(List<PhotoAlbum> dailyPhotoUrlList) {
        this.dailyPhotoUrlList = dailyPhotoUrlList;
    }

    private List<PhotoAlbum> dailyPhotoUrlList;
    private String saveProfilePic;

    /**
     * @author vimleshn September 06, 2017
     *         KingChenApplication.java
     * @description this method is use to create activiy .
     */

    public void onCreate() {
        super.onCreate();
        try {
            context = getApplicationContext();


        } catch (Exception e) {
            Log.e(RoyalStagApplication.class.getName(), "" + e);
        }

        Picasso picasso = new Picasso.Builder(this).addRequestHandler(
                new AssetVideoRequestHandler()).build();
        Picasso.setSingletonInstance(picasso);
    }

    /**
     * @author vimleshn November 02, 2016 2016
     * KingChenApplication.java
     * @description below method is used to create a instance of application class
     */

    public static RoyalStagApplication getInstance() {
        return (RoyalStagApplication) context.getApplicationContext();

    }

    /**
     * Method for getting application context throughout the application scope
     * @return Application context
     */

    public static Context getAppContext() {
        return context;
    }
    private class AssetVideoRequestHandler extends RequestHandler {

        @Override
        public boolean canHandleRequest(Request data) {
            return "asset".equals(data.uri.getScheme());
        }

        @Override
        public Result load(Request request, int networkPolicy) throws IOException {
            // ExoPlayer accepts uris in the form "asset:///path/to/video.mp4",
            // but AssetManager only needs the relative path "path/to/video.mp4"
            String assetPath = request.uri.toString().replaceFirst("asset:///", "");

            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            AssetFileDescriptor afd = getAssets().openFd(assetPath);
            mmr.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            Bitmap bitmap = mmr.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
            return new Result(bitmap, Picasso.LoadedFrom.DISK);
        }
    }
}