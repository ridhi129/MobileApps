package com.example.ridhirustagi.cs478project3app3;

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
import com.example.ridhirustagi.cs478project3app3.RestaurantListFragment.RestroListSelectionListener;

public class RestaurantDisplayActivity extends AppCompatActivity implements RestroListSelectionListener {


    public static String[] mRestaurantArray;
    // defining restaurant image array
    public static Integer[] mRestaurantImageArray = {R.drawable.girlgoat, R.drawable.mitchell,
            R.drawable.berghoff, R.drawable.momotaro,
            R.drawable.purplepig, R.drawable.grace};
    // creating fragment class objects
    private final RestaurantImageFragment mRestaurantImageFragment = new RestaurantImageFragment();
    private final RestaurantListFragment mRestaurantListFragment = new RestaurantListFragment();

    private FragmentManager mFragmentManager;
    // reference to the frame layouts
    private FrameLayout mRestroFrameLayout, mRestroImageFrameLayout;
    //used for setting width and height of the 2 framelayouts
    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;

    int mShownIndex2  = -1;
    static String old_item2 = "old";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

// getting reference to the restaurant list array
        mRestaurantArray = getResources().getStringArray(R.array.Restaurants);
        setContentView(R.layout.activity_restaurant_display);

        // setting the action bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar) ;
        setSupportActionBar(myToolbar);

        // getting refernce to the frame layouts in the layout file
        mRestroFrameLayout = (FrameLayout) findViewById(R.id.restro_frame);
        mRestroImageFrameLayout = (FrameLayout) findViewById(R.id.restroimage_frame);

        // setting the restaurant list fragment programmatically
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager
                .beginTransaction();

        fragmentTransaction.replace(R.id.restro_frame, mRestaurantListFragment);

        fragmentTransaction.commit();

        // keeping track of the last selected list item
        if (savedInstanceState != null) {
            mShownIndex2 = savedInstanceState.getInt(old_item2) ;
        }

        // calling setlayout() to handle the framelayouts across device orientation
        mFragmentManager.addOnBackStackChangedListener(new android.support.v4.app.FragmentManager.OnBackStackChangedListener() {
            public void onBackStackChanged() {
                setLayout();
            }
        });


    }

    public void onStart() {
        super.onStart() ;
        if (mShownIndex2 >= 0) {
            mRestaurantImageFragment.showImageAtIndex(mShownIndex2);
            mRestaurantListFragment.setSelection(mShownIndex2);
            mRestaurantListFragment.getListView().setItemChecked(mShownIndex2,true);
        }
    }

    private void setLayout() {
  // setting the frame layouts dynamically as per the change in device orientation

        // in landscape mode
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            if (!mRestaurantImageFragment.isAdded()) {

                // Make the RestaurantListFragment occupy the entire layout
                mRestroFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        MATCH_PARENT, MATCH_PARENT));
                mRestroImageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT));
            } else {

                // Make the RestaurantListLayout take 1/3 of the layout's width
                mRestroFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,MATCH_PARENT,1f));

                // Make the RestaurantImageLayout take 2/3's of the layout's width
                mRestroImageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT, 2f));
            }
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            if (!mRestaurantImageFragment.isAdded()) {

                // Make the RestaurantListFragment occupy the entire layout
                mRestroFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        MATCH_PARENT, MATCH_PARENT));
                mRestroImageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT));
            } else {
                // Make the RestaurantImageFragment occupy the entire layout
                mRestroFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT));
                mRestroImageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT,
                        MATCH_PARENT));
            }
        }

    }


     //This method is used to create the options menu in the action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }


     //This method is called on selecting an item in the options menu
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){


        //for hotel
        if(menuItem.getItemId() == R.id.hotel){
            Intent hIntent = new Intent(this,HotelDisplayActivity.class);
            startActivity(hIntent);

        }
        //for restaurant
        else if(menuItem.getItemId() == R.id.restro) {
            Intent rIntent = new Intent(this, RestaurantDisplayActivity.class);
            startActivity(rIntent);
        }

        return true;
    }

    // defining the interface method to handle the clicking of the list items
    public void onRestroListSelection(int index) {

        if (!mRestaurantImageFragment.isAdded()) {

            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.restroimage_frame, mRestaurantImageFragment);

            // Add this FragmentTransaction to the backstack
            fragmentTransaction.addToBackStack(null);

            // Commit the FragmentTransaction
            fragmentTransaction.commit();

            // Force Android to execute the committed FragmentTransaction
            mFragmentManager.executePendingTransactions();
        }

        if (mRestaurantImageFragment.getShownIndex() != index) {

            // Tell the RestaurantImageFragment to show the image at position index
            mRestaurantImageFragment.showImageAtIndex(index);
            mShownIndex2 = index ;

        }

    }

    public void onSaveInstanceState(Bundle outState) {
        if (mShownIndex2 >= 0) {
            outState.putInt(old_item2, mShownIndex2) ;
        }
    }

}
