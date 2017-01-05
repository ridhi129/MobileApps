package com.example.ridhirustagi.cs478project5;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

// aidl interface package
import com.example.ridhirustagi.cs478KeyCommon.MusicGenerator;



public class AudioServer extends Service {

    protected static final String TAG = "AudioServer";

    // defining instance of Android built in media player
    private MediaPlayer mPlayer = new MediaPlayer();

    int flag =0;
    String curr = "empty";

    private final MusicGenerator.Stub mBinder = new MusicGenerator.Stub() {

        // service method to play a song
        public void getPlaySong(String clipNum) {

            // boolean to reset player when a playing song is paused
            if(flag == 1){
                flag =0;
                mPlayer.reset();
            }

            // clip number entered by user
            curr = clipNum;

            // get path for the clip number entered by user
            String songFile = "android.resource://" + getApplicationContext().getPackageName() + "/raw/" + clipNum;

            try {

                mPlayer.setDataSource(getApplicationContext(), Uri.parse(songFile));
            } catch (Exception e) {

                //catch e
            }

            try {

                mPlayer.prepare();
            } catch (Exception e) {
                //catch e
            }

            if (null != mPlayer) {

                if (mPlayer.isPlaying()) {

                    // Rewind to beginning of song
                    mPlayer.seekTo(0);

                } else {


                          mPlayer.seekTo(0);
                        // Start playing song
                        mPlayer.start();

                }
                Log.i(TAG, "The music service was bound in the server!");
            } else {
                Log.i(TAG, "The music service was not bound in the server!");
            }


        }

        //service method to pause a song
        public void getPauseSong() {

            if (null != mPlayer) {

                if (mPlayer.isPlaying()) {


                    // pause playing song
                    mPlayer.pause();
                    flag =1;

                }

                    Log.i(TAG, "The music service was bound in the server!");
            } else {

                    Log.i(TAG, "The music service was not bound in the server!");
                }


        }

        //service method to resume a paused song
        public void getResumeSong() {

            if (null != mPlayer) {



                if (mPlayer.isPlaying()) {

                //do nothing

                } else {

                    // Start playing the paused song
                    mPlayer.start();

                }

                Log.i(TAG, "The music service was bound in the server!");
            } else {
                Log.i(TAG, "The music service was not bound in the server!");
            }
        }

        // service method to stop song
        public void getStopSong() {

            if (null != mPlayer) {

                if (mPlayer.isPlaying()) {

                    // Rewind to beginning of song
                    mPlayer.reset();
                    mPlayer.stop();

                }
                Log.i(TAG, "The music service was stopped in the server!");
            } else{
                Log.i(TAG, "The music service was not sucessfully stopped in the server!");

                    }
        }




    };


    // return IBinder object
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }
}
