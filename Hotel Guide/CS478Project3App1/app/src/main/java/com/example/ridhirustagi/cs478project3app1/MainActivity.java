package com.example.ridhirustagi.cs478project3app1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button buttonHotel;
    private Button buttonRest;


    //Defining two intents one each for hotel and restaurants
    private static final String TOAST_INTENT1 = "com.example.ridhirustagi.cs478project3app1.showToastHotel";
    private static final String TOAST_INTENT2 = "com.example.ridhirustagi.cs478project3app1.showToastRestaurant";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // getting references of the layout elements
        buttonHotel = (Button) findViewById(R.id.button1);
         buttonRest = (Button) findViewById(R.id.button2);
        TextView tvHotel = (TextView) findViewById(R.id.textView2);
        TextView tvRest = (TextView) findViewById(R.id.textView3);

        //setting text of the textviews and buttons
        buttonHotel.setText("Explore Hotels");
        buttonRest.setText("Explore Restaurants");
        tvHotel.setText("To explore the various hotels in the city of Chicago, click the below button.");
        tvRest.setText("To explore the various restaurants in the city of Chicago, click the below button.");

        // Clicking on the buttons will send an ordered broadcast to App2 and App3
        buttonHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(TOAST_INTENT1);
                sendOrderedBroadcast(intent1,null);
            }
        });

        buttonRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(TOAST_INTENT2);
                sendOrderedBroadcast(intent2,null);
            }
        });



    }
}
