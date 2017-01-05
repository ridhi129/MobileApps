package com.example.ridhirustagi.cs478project2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;
import java.util.List;
import android.widget.ImageView.ScaleType;




public class CustomGridAdapter extends BaseAdapter {

    private Context rContext;
    public  String[] CarName;
    private List<Integer> CarImageid;


// Implementing a custom adapter for displaying CarName text and Car Images in a GridView
    public CustomGridAdapter(Context c, String[] CarName, List<Integer> CarImageid ) {
        rContext = c;
        this.CarName = CarName;
        this.CarImageid = CarImageid;

    }

    @Override
    public int getCount() {

        return CarName.length;
    }

    @Override
    public Object getItem(int position) {

        return CarImageid.get(position);
    }

    @Override
    public long getItemId(int position) {

        return CarImageid.get(position);
    }


    // Instantiating and binding the grid view with the values of car name and image for a position and returning the populated grid
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View grid;
        // Inflater to inflate the layout with the image and text
        LayoutInflater inflater = (LayoutInflater) rContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(rContext);
            // inflating
            grid = inflater.inflate(R.layout.grid_element, null);
             // binding the image and text views
            TextView textView = (TextView) grid.findViewById(R.id.gText);
            ImageView imageView = (ImageView)grid.findViewById(R.id.gImage);
            textView.setText(CarName[position]);
            imageView.setImageResource(CarImageid.get(position));


        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}
