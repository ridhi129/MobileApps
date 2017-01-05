package com.example.ridhirustagi.cs478project2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.net.Uri;
import android.util.Log;

// activity to display image and text on gridview
public class ImageViewActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the Intent used to start this Activity
        Intent intent = getIntent();


        // Make a new ImageView
        ImageView imageView = new ImageView(getApplicationContext());

        // Get the ID of the image to display and set it as the image for this ImageView
        imageView.setImageResource(intent.getIntExtra(MainActivity.EXTRA_RES_ID, 0));

        setContentView(imageView);


        // setting onclick for the Full size image

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // starting activity BrowserActivity by passing carurl as Intent extra
                    String carurl = getIntent().getStringExtra("Car_URL");
                    Intent intent = new Intent(ImageViewActivity.this, BrowseActivity.class);
                    intent.putExtra("Car_URL", carurl);
                    startActivity(intent);


                }
            });



    }


}
