package com.example.ridhirustagi.cs478project5client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ridhirustagi.cs478KeyCommon.MusicGenerator;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class PlayerClient extends AppCompatActivity {

    protected static final String TAG = "MusicServiceClient";

    // reference object of the interface in aidl file
    public static MusicGenerator mMusicGeneratorService;

    //listview object for displaying clip logs
    private ListView lv = null;

    ArrayList<String> log = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter ;

    // edittext object for entering clip number
    protected EditText clipField;

    private boolean mIsBound = false;


    String input;
    String isPlayed = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_client);

        // initialize the layout elements
        clipField = (EditText) findViewById(R.id.editText2);
        lv = (ListView) findViewById(R.id.listview);

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.log_list, log);
        lv.setAdapter(arrayAdapter);

        final Button btnPlay = (Button) findViewById(R.id.button4);
        final Button btnResume = (Button) findViewById(R.id.button7);
        final Button btnStop = (Button) findViewById(R.id.button6);
        final Button btnPause = (Button) findViewById(R.id.button5);


        //click listener for Resume button
        btnResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    if (mIsBound) {


                        if (clipField.getText().toString().equals("")) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Enter a valid clip number", Toast.LENGTH_SHORT);
                            toast.show();

                        }
                        else if(Integer.parseInt(clipField.getText().toString()) > 4 || Integer.parseInt(clipField.getText().toString()) < 0)

                        {
                            Toast toast = Toast.makeText(getApplicationContext(), "Enter a valid clip number", Toast.LENGTH_SHORT);
                            toast.show();

                        }

                        else {

                            log.add("Resume Clip " + clipField.getText().toString());
                            mMusicGeneratorService.getResumeSong();
                            arrayAdapter.notifyDataSetChanged();
                            btnResume.setEnabled(false);

                            if (isPlayed.equals("yes")) {
                                clipField.setEnabled(false);
                                btnPlay.setEnabled(false);
                                btnStop.setEnabled(true);
                                btnPause.setEnabled(true);
                            } else {
                                clipField.setEnabled(true);
                            }
                        }
                    } else {
                        Log.i(TAG, "The music service was not bound in client!");
                    }

                } catch (RemoteException e) {

                    Log.e(TAG, e.toString());

                }
            }


        });



         //click listener for Play button
        btnPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {

                    if (mIsBound) {

                        if (clipField.getText().toString().equals("")) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Enter a valid clip number", Toast.LENGTH_SHORT);
                            toast.show();

                        }
                        else if(Integer.parseInt(clipField.getText().toString()) > 4 || Integer.parseInt(clipField.getText().toString()) < 0)

                           {
                                Toast toast = Toast.makeText(getApplicationContext(), "Enter a valid clip number", Toast.LENGTH_SHORT);
                                toast.show();

                            }



                        else{

                            input = "clip" + clipField.getText().toString();

                            log.add("Play Clip " + clipField.getText().toString());
                            mMusicGeneratorService.getPlaySong(input);
                            arrayAdapter.notifyDataSetChanged();
                            clipField.setEnabled(false);
                            btnResume.setEnabled(false);
                            btnPlay.setEnabled(false);
                            btnPause.setEnabled(true);
                            btnStop.setEnabled(true);


                            isPlayed = "yes";

                        }

                    } else {
                        Log.i(TAG, "The music service was not bound in client!");
                    }

                } catch (RemoteException e) {

                    Log.e(TAG, e.toString());

                }
            }
        });

        //click listener for Stop button
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (mIsBound) {

                        if (clipField.getText().toString().equals("")) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Enter a valid clip number", Toast.LENGTH_SHORT);
                            toast.show();

                        } else if (Integer.parseInt(clipField.getText().toString()) > 4 || Integer.parseInt(clipField.getText().toString()) < 0)

                        {
                            Toast toast = Toast.makeText(getApplicationContext(), "Enter a valid clip number", Toast.LENGTH_SHORT);
                            toast.show();

                        }
                    else {
                            log.add("Stop Clip " + clipField.getText().toString());
                            mMusicGeneratorService.getStopSong();
                            arrayAdapter.notifyDataSetChanged();
                            clipField.setEnabled(true);
                            btnPlay.setEnabled(true);
                            btnResume.setEnabled(false);
                            btnStop.setEnabled(false);
                            btnPause.setEnabled(false);
                        }

                    } else {
                        Log.i(TAG, "The music service was not bound in client!");
                    }
                } catch (RemoteException e) {
                    Log.e(TAG, e.toString());
                }

            }
        });



      // click listener for Pause button
        btnPause.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                try {

                    if (mIsBound) {

                        if (clipField.getText().toString().equals("")) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Enter a valid clip number", Toast.LENGTH_SHORT);
                            toast.show();

                        }
                        else if(Integer.parseInt(clipField.getText().toString()) > 4 || Integer.parseInt(clipField.getText().toString()) < 0)

                        {
                            Toast toast = Toast.makeText(getApplicationContext(), "Enter a valid clip number", Toast.LENGTH_SHORT);
                            toast.show();

                        }
                        else {
                            log.add("Pause Clip " + clipField.getText().toString());
                            mMusicGeneratorService.getPauseSong();
                            arrayAdapter.notifyDataSetChanged();
                            clipField.setEnabled(true);
                            btnPause.setEnabled(false);
                            btnPlay.setEnabled(true);
                            btnResume.setEnabled(true);
                            btnStop.setEnabled(false);
                        }
                    } else {
                        Log.i(TAG, "The music service was not bound in client!");
                    }

                } catch (RemoteException e) {

                    Log.e(TAG, e.toString());

                }
            }


        });

    }

// method to create a connection and set value of bool mISBound
    private final ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder iservice) {

            mMusicGeneratorService = MusicGenerator.Stub.asInterface(iservice);

            mIsBound = true;

        }

        public void onServiceDisconnected(ComponentName className) {

            mMusicGeneratorService = null;

            mIsBound = false;

        }
    };



     @Override
    public void onStart() {
        super.onStart();

    }

// method to bind service
    @Override
    protected void onResume() {
        super.onResume();

        if (!mIsBound) {

            boolean b = false;
            Intent i = new Intent(MusicGenerator.class.getName());

            ResolveInfo info = getPackageManager().resolveService(i, Context.BIND_AUTO_CREATE);
            i.setComponent(new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name));

            b = bindService(i, this.mConnection, Context.BIND_AUTO_CREATE);
            if (b) {
                Log.i(TAG, "bindService() succeeded!");
            } else {
                Log.i(TAG, "bindService() failed!");
            }

        }
    }

    @Override
    protected void onPause() {

        super.onPause();
    }


    @Override
    public void onStop() {
        super.onStop();

    }

    // Unbind from Service
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mIsBound) {
            try {
                mMusicGeneratorService.getStopSong();
            }catch(Exception e){}

            unbindService(this.mConnection);
        }
    }

}
