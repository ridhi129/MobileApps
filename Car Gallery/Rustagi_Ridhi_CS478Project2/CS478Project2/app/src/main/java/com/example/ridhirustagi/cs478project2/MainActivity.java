package com.example.ridhirustagi.cs478project2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import java.util.ArrayList;
import java.util.Arrays;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.util.Log;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;




public class MainActivity extends AppCompatActivity {

    // Storing dealer infomation as an arraylist in Hashmap dealerInfo
    HashMap<Integer,ArrayList<String>> dealerInfo = new HashMap<Integer,ArrayList<String>>();

    public MainActivity(){
        dealerInfo.put(0,acura);
        dealerInfo.put(1,audi);
        dealerInfo.put(2,honda);
        dealerInfo.put(3,bmw);
        dealerInfo.put(4,dodge);
        dealerInfo.put(5,mini);

    }
    protected static final String EXTRA_RES_ID = "POS";
    GridView grid;
    // Array of car brands
    String CarName[]={
            "Acura",
            "Audi",
            "Honda",
            "BMW",
            "Dodge",
            "Mini"
    };
    // List of car images
    ArrayList<Integer> CarImageid = new ArrayList<Integer>(
            Arrays.asList(
                    R.drawable.acura,
                    R.drawable.audi,
                    R.drawable.honda,
                    R.drawable.bmw,
                    R.drawable.dodge,
                    R.drawable.mini

            ));
  // List of high resolution images
    ArrayList<Integer> CarImageid_Large = new ArrayList<Integer>(
            Arrays.asList(
                    R.drawable.acura_big,
                    R.drawable.audi_big,
                    R.drawable.honda_big,
                    R.drawable.bmw_big,
                    R.drawable.dodge_big,
                    R.drawable.mini_big

            ));

    // Array of official web page urls
    String[] arrayOfUrls = {
            "http://www.acura.com/",
            "https://www.audiusa.com/",
            "http://www.honda.com/",
            "http://www.bmw.com/com/en/",
            "http://www.dodge.com/en/",
            "http://www.miniusa.com/content/miniusa/en.html"
    };

    // Arraylist of each car dealer info to be added to hashmap
    ArrayList<String> acura = new ArrayList<String>(
            Arrays.asList(

                    "1. McGrath Acura : 1301 N Elston Ave, Chicago, IL 60642",
                    "2. Greater Chicago Motors : 1850 N Elston Ave, Chicago, IL 60642",
                    "3. Muller's Woodfield Acura : 1099 W Higgins Rd, Hoffman Estates, IL 60169"
            ));


    ArrayList<String> audi = new ArrayList<String>(
            Arrays.asList(

                    "1. Greater Chicago Motors : 1850 N Elston Ave, Chicago, IL 60642",
                    "2. Fletcher Jones Audi : 1111 N Clark St, Chicago, IL 60610",
                    "3. Audi Orland Park : 8021 W. 159th Street Tinley Park, IL 60477"
            ));

    ArrayList<String> honda = new ArrayList<String>(
            Arrays.asList(

                    "1. Fletcher Jones Honda : 1100 N Clark St, Chicago, IL 60610",
                    "2. Honda City Chicago : 4950 S Pulaski Rd, Chicago, IL 60632",
                    "3. McGrath City Honda : 6720 W Grand Ave, Chicago, IL 60707"
            ));


    ArrayList<String> bmw = new ArrayList<String>(
            Arrays.asList(

                    "1. Perillo BMW : 1035 N Clark St, Chicago, IL 60610",
                    "2. Motoworks Chicago : 1901 S Western Ave, Chicago, IL 60608",
                    "3. BMW of Orland Park : 11030 W 159th St, Orland Park, IL 60467"
            ));


    ArrayList<String> dodge = new ArrayList<String>(
            Arrays.asList(

                    "1. South Chicago Dodge Chrysler Jeep: 7340 S Western Ave, Chicago, IL 60636",
                    "2. Midway Dodge : 4747 S Pulaski Rd, Chicago, IL 60632",
                    "3. Marino Chrysler Jeep Dodge : 5133 W Irving Park Rd, Chicago, IL 60641"
            ));


    ArrayList<String> mini = new ArrayList<String>(
            Arrays.asList(

                    "1. MINI of Chicago : 1111 W Diversey Pkwy, Chicago, IL 60614",
                    "2. PATRICK MINI   : 700 E Golf Rd, Schaumberg IL 60173",
                    "3. BILL JACOBS MINI : 2491 Aurora Avenue, Naperville IL 60540"
            ));


    // Using the CustomGridAdapter to display images and tex of cars in Gridview
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Instantiating the custom adapter and binding it to the gridview element defined in the activity_main.xml
        CustomGridAdapter adapter = new CustomGridAdapter(MainActivity.this, CarName, CarImageid);
        grid = (GridView)findViewById(R.id.gridview);
        grid.setAdapter(adapter);

        // Setting onClickListener to car thumnail images- ShortClick
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                //Create an Intent to start the ImageViewActivity
                Intent intent = new Intent(MainActivity.this, ImageViewActivity.class);

                // Add the ID of the thumbnail image to display as an Intent Extra
               // intent.putExtra(EXTRA_RES_ID, (int) id);
                intent.putExtra(EXTRA_RES_ID,CarImageid_Large.get(position));
                intent.putExtra("Car_URL", arrayOfUrls[position]); // Passing car web page url as Intent extra to be used by BrowseActivity


                // Start the ImageViewActivity
                startActivity(intent);
            }


        });

       // Binding contextmenu to the entire gridview
        registerForContextMenu(grid);


    }


    // Creating context menu items -  Longclick
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        // menu.setHeaderTitle("Context Menu");
        menu.add(0, v.getId(), 0, "Full Size Image");
        menu.add(0, v.getId(), 0, "Official Web Page");
        menu.add(0, v.getId(), 0, "Dealers Info");
    }

    // Defining onClick actions for each item of context menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // get the position of the context menu
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        String url1 = arrayOfUrls[index];

        if(item.getTitle()=="Full Size Image") {
           // open the ImageView Activity by passing the CarImage id as Intent extra
            Intent intent = new Intent(MainActivity.this, ImageViewActivity.class);
            intent.putExtra(EXTRA_RES_ID, CarImageid_Large.get(index));
            intent.putExtra("Car_URL",url1);
            startActivity(intent);
        }

        else if(item.getTitle()=="Official Web Page"){

            // open the BrowseActivity by passing the url of the car as Intent extra

            Log.i("MainActivity","inside browse");
            Intent intent = new Intent(MainActivity.this, BrowseActivity.class);
            intent.putExtra("Car_URL",url1);
            startActivity(intent);

        }

        else if(item.getTitle() == "Dealers Info"){

            // starting the DealerDetailsActivity by passing the Arraylist of dealers info as intent extra
            Intent intent = new Intent(MainActivity.this, DealerDetailsActivity.class);
            intent.putExtra("Dealer",dealerInfo.get(index));
            startActivity(intent);
        }


        return true;
    }


}
