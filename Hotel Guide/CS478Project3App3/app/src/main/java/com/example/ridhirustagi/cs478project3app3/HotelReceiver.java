package com.example.ridhirustagi.cs478project3app3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by ridhirustagi on 10/20/16.
 */

public class HotelReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent arg1) {
        // receives the broadcast after App2(as App2 receiver has higher priority than App3 receiver)
        // creates an intent to start the hotel display activity
        Intent intent = new Intent(context,HotelDisplayActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);


    }
}
