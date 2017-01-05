package com.example.ridhirustagi.cs478project3app2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by ridhirustagi on 10/20/16.
 */

public class RestaurantReceiver extends BroadcastReceiver {

    public void onReceive(Context arg0, Intent arg1) {
        // on receiving the broadcast, creates a toast message, indicating restaurant button was selected
        Toast.makeText(arg0, "Get ready to explore restaurants!",
                Toast.LENGTH_LONG).show() ;

    }
}
