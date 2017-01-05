package com.example.ridhirustagi.cs478project3app3;

import android.app.Activity;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HotelListFragment extends ListFragment {

    private static final String TAG = "HotelListFragment";
    private ListSelectionListener mListener = null;
    private int mCurrIdx = -1;
    static final String old_position = "old";
    Integer mOldPosition = null;

    // Callback interface that allows this Fragment to notify the HotelDisplayActivity when
    // user clicks on a hotel list Item
    public interface ListSelectionListener {
        public void onListSelection(int index);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {

            // Set the ListSelectionListener for communicating with the HotelDisplayActivity
            mListener = (ListSelectionListener) activity;

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
        // checks the position of the previously selected list item and returns the view with the selected item whenever fragment is displayed
        if (savedState != null) {
            int oldPosition = savedState.getInt(old_position) ;
            Log.i(TAG, "OLD_POSITION = " + oldPosition) ;
            mOldPosition = oldPosition ;
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
                R.layout.activity_hotel_list_fragment, HotelDisplayActivity.mHotelArray));

        // If an item has been selected, set its checked state
        if (-1 != mCurrIdx)
            getListView().setItemChecked(mCurrIdx, true);
    }

    @Override
    public void onStart() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStart()");
        super.onStart();
        if (mOldPosition != null) {
            int oldPosition = mOldPosition ;
            getListView().setSelection(oldPosition) ;
            // Inform the HotelDisplayActivity that the item in position oldPosition was selected
            mListener.onListSelection(oldPosition);
        }
    }

    // Called when the user selects an item from the List
    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {

        if (mCurrIdx != pos) {
            mCurrIdx = pos;
            mListener.onListSelection(pos);
        }
        // Indicates the selected item has been checked

        getListView().setItemChecked(mCurrIdx,true);


    }

    public void onDestroy() {
        Log.i(TAG, getClass().getSimpleName() + ":onDestroy()");
        super.onDestroy();
    }
    }

