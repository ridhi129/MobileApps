package com.example.ridhirustagi.cs478project2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ListActivity;
import android.widget.ArrayAdapter;
import android.content.Intent;
import android.widget.ListView;
import android.app.Activity;

import java.util.ArrayList;

// activity to display the dealers information
public class DealerDetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_details);

        // getting the intent with the array containing the dealer information.
        Intent i = getIntent();
        ListView lv = (ListView)findViewById(R.id.listview);
        ArrayList<String> list = new ArrayList<String>();
        list = i.getStringArrayListExtra("Dealer");
        // converting the Arraylist returned by intent to a string array
        String[] lv_arr = (String[]) list.toArray(new String[list.size()]);
        //using ArrayAdapter to display the listview
        lv.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item,lv_arr));







    }
}
