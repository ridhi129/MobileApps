package com.example.ridhirustagi.cs478project2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.net.Uri;
import android.content.Intent;
import android.app.Activity;

// Activity to open browser
public class BrowseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // calling implicit browser activity with Car url passed from ImageViewActivity or MainActivity
        String carurl = getIntent().getStringExtra("Car_URL");
        Uri uri = Uri.parse(carurl);
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(i);
        finish();

    }




}
