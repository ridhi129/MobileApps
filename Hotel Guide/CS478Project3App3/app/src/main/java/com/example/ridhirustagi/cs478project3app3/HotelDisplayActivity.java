package com.example.ridhirustagi.cs478project3app3;

import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.ridhirustagi.cs478project3app3.HotelListFragment.ListSelectionListener;

import java.lang.reflect.Array;

public class HotelDisplayActivity extends AppCompatActivity implements ListSelectionListener {


    public static String[] mHotelArray;
    // defining hotel image array
    public static Integer[] mHotelImageArray = {R.drawable.blake, R.drawable.congress,
            R.drawable.hilton, R.drawable.mariott,
            R.drawable.palomar, R.drawable.rennaisance};

     // creating fragment class objects
    private final HotelImageFragment mHotelImageFragment = new HotelImageFragment();
    private final HotelListFragment mHotelListFragment = new HotelListFragment();
    private FragmentManager mFragmentManager;
    // reference to the frame layouts
    private FrameLayout mHotelFrameLayout, mHotelImageFrameLayout;

    //used for setting width and height of the 2 frames
    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;

    int mShownIndex  = -1;
    static String old_item = "old";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // getting reference to the hotel list array
        mHotelArray = getResources().getStringArray(R.array.Hotels);
        setContentView(R.layout.activity_hotel_display);
        // setting the action bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar) ;
        setSupportActionBar(myToolbar);
        // getting refernce to the frame layouts in the layout file
        mHotelFrameLayout = (FrameLayout) findViewById(R.id.hotel_frame);
        mHotelImageFrameLayout = (FrameLayout) findViewById(R.id.image_frame);

        // setting the hotel list fragment programmatically
        mFragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = mFragmentManager
                .beginTransaction();

        fragmentTransaction.replace(R.id.hotel_frame, mHotelListFragment);

        fragmentTransaction.commit();

        // keeping track of the last selected list item
        if (savedInstanceState != null) {
            mShownIndex = savedInstanceState.getInt(old_item) ;
        }

        // calling setlayout() to handle the framelayouts across device orientation
        mFragmentManager.addOnBackStackChangedListener(new android.support.v4.app.FragmentManager.OnBackStackChangedListener() {
            public void onBackStackChanged() {
                setLayout();
            }
        });



    }



    private void setLayout() {

       // setting the frame layouts dynamically as per the change in device orientation

        // in landscape mode
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            if (!mHotelImageFragment.isAdded()) {

                // Make the HotelListFragment occupy the entire layout
                mHotelFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        MATCH_PARENT, MATCH_PARENT));
                mHotelImageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT));
            } else {

                // Make the HotelListLayout take 1/3 of the layout's width
                mHotelFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,MATCH_PARENT,1f));

                // Make the HotelImageLayout take 2/3's of the layout's width
                mHotelImageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT, 2f));
            }
            // in potrait mode
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            if (!mHotelImageFragment.isAdded()) {

                // Make the HotelListFragment occupy the entire layout
                mHotelFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        MATCH_PARENT, MATCH_PARENT));
                mHotelImageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT));
            } else {
                // Make the HotelImageFragment occupy the entire layout, hide the HotelListFragment
                mHotelFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT));
                mHotelImageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT,
                        MATCH_PARENT));
            }
        }

    }

    public void onStart() {
        super.onStart() ;
        if (mShownIndex >= 0) {
            mHotelImageFragment.showImageAtIndex(mShownIndex);
            mHotelListFragment.setSelection(mShownIndex);
            mHotelListFragment.getListView().setItemChecked(mShownIndex,true);
        }
    }

     //This method is used to create the options menu in the action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }


     // This method is called on selecting an item in the options menu
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){


        //for hotels
        if(menuItem.getItemId() == R.id.hotel){

            Intent hIntent = new Intent(this,HotelDisplayActivity.class);
            startActivity(hIntent);

        }
        //for restaurants
        else if(menuItem.getItemId() == R.id.restro) {

            Intent rIntent = new Intent(this, RestaurantDisplayActivity.class);
            startActivity(rIntent);
        }

        return true;
    }

// defining the interface method to handle the click of list items
    public void onListSelection(int index) {

        if (!mHotelImageFragment.isAdded()) {

            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
              // adding the HotelImageFragment
             fragmentTransaction.replace(R.id.image_frame,mHotelImageFragment);

            // Add this FragmentTransaction to the backstack
            fragmentTransaction.addToBackStack(null);

            // Commit the FragmentTransaction
            fragmentTransaction.commit();

            // Force Android to execute the committed FragmentTransaction
            mFragmentManager.executePendingTransactions();
        }

        if (mHotelImageFragment.getShownIndex() != index) {

            // Tell the HotelImageFragment to show the image at position index
            mHotelImageFragment.showImageAtIndex(index);
            mShownIndex = index ;

        }


    }
 // retaining the index of the selected item
    public void onSaveInstanceState(Bundle outState) {
        if (mShownIndex >= 0) {
            outState.putInt(old_item, mShownIndex) ;
        }
    }



}


