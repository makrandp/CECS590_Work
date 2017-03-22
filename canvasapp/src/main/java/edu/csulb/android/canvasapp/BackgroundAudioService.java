package edu.csulb.android.canvasapp;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Mak on 3/13/2017.
 */

public class BackgroundAudioService extends Service {

    private MediaPlayer mediaPlayer;

    public BackgroundAudioService() {
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        // Create MediaPlayer object, to play your song.
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.eraser);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        // Play song.
        mediaPlayer.start();

        return START_STICKY;
    }

    // Destroy
    @Override
    public void onDestroy() {
        // Release the resources
        mediaPlayer.release();
        super.onDestroy();
    }


}
