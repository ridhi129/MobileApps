package com.example.ridhirustagi.cs478project3app3;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RestaurantListFragment extends ListFragment {

    private static final String TAG = "RestroListFragment";
    private RestroListSelectionListener mListener = null;
    private int mCurrIdx = -1;
    static final String old_position2 = "old";
    Integer mOldPosition2 = null;

    // Callback interface that allows this Fragment to notify the RestaurantDisplayActivity when
    // user clicks on a restaurant list Item
    public interface RestroListSelectionListener {
        public void onRestroListSelection(int index);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {

            // Set the RestroListSelectionListener for communicating with the RestaurantDisplayActivity
            mListener = (RestroListSelectionListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedState) {
        View newView =  super.onCreateView(inflater, container, savedState) ;
        if (savedState != null) {
            int oldPosition = savedState.getInt(old_position2) ;
            Log.i(TAG, "OLD_POSITION = " + oldPosition) ;
            mOldPosition2 = oldPosition ;
        }
        return newView ;

    }



    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

        // Set the list choice mode to allow only one selection at a time
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Set the list adapter for the ListView

        setListAdapter(new ArrayAdapter<String>(getActivity(),
                R.layout.fragment_restro_list, RestaurantDisplayActivity.mRestaurantArray));

        // If an item has been selected, set its checked state
        if (-1 != mCurrIdx)
            getListView().setItemChecked(mCurrIdx, true);
    }

    @Override
    public void onStart() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStart()");
        super.onStart();
        if (mOldPosition2 != null) {
            int oldPosition = mOldPosition2 ;
            getListView().setSelection(oldPosition) ;
            // Inform the RestaurantDisplayActivity that the item in position oldPosition was selected
            mListener.onRestroListSelection(oldPosition);
        }
    }

    // Called when the user selects an item from the restaurant list
    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {

        if (mCurrIdx != pos) {
            mCurrIdx = pos;
            mListener.onRestroListSelection(pos);
        }
        // Indicates the selected item has been checked
        getListView().setItemChecked(mCurrIdx,true);



    }

    public void onDestroy() {
        Log.i(TAG, getClass().getSimpleName() + ":onDestroy()");
        super.onDestroy();
    }
    }

