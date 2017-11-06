package ca.bcit.ass3.li_leung;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.bcit.ass3.li_leung.controllers.DetailController;
import ca.bcit.ass3.li_leung.controllers.EventController;
import ca.bcit.ass3.li_leung.models.Detail;
import ca.bcit.ass3.li_leung.models.Event;

/**
 * Main Activity for the application.
 *
 * @author Castiel Li & Justin Leung
 */
public class MainActivity extends AppCompatActivity
{


    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    /**
     * Starting driver for activity.
     *
     * @param savedInstanceState - Previous state of activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //This find our toolbar view in activity_main.xml file
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Get the list view
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        //setting list adapter
        expListView.setAdapter(listAdapter);


        expListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                long listPosition = expListView.getExpandableListPosition(position); //get the position of the header

                int itemType = ExpandableListView.getPackedPositionType(listPosition);
                int groupPosition = ExpandableListView.getPackedPositionGroup(listPosition);
                int childPosition = ExpandableListView.getPackedPositionChild(listPosition);

                //if group item long clicked
                if (itemType == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                    onGroupLongClick(groupPosition, view);
                }

                //if child item long clicked
                else if (itemType == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    //  ...
                    onChildLongClick(groupPosition, childPosition);
                }
                return false;
            }
        });

        //allow user to edit items when click on item
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(MainActivity.this, UpdateDetail.class);

                //getting the event that the detail was under
                String header = listDataHeader.get(groupPosition);
                String[] headerArray = header.split(" ");
                String eventSelect = headerArray[0];

                //getting the item details
                String child = listDataChild.get(header).get(childPosition);
                String[] childArray = child.split(" ");
                String itemName = childArray[0];
                String itemUnit = childArray[1];
                String itemQuantity = childArray[2];

                //put all item details into intent
                intent.putExtra("eventSelect" , eventSelect);
                intent.putExtra("itemName" , itemName);
                intent.putExtra("itemUnit" , itemUnit);
                intent.putExtra("itemQuantity" , itemQuantity);

                startActivity(intent);
                return false;
            }
        });
    }

    private void onChildLongClick(int groupPosition, int childPosition) {
    }

    private void onGroupLongClick(final int groupPosition, View view) {
        //Createing the instance of PopupMenu
        PopupMenu popup = new PopupMenu(this, view);
        //inflating the popup using xml
        popup.getMenuInflater().inflate(R.menu.menu_event_popup, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getTitle().equals("Delete event")){
                    //delete event in database
                    EventController eventC = new EventController(MainActivity.this);
                    List<Event> allEvents = eventC.read();
                    for(Event e : allEvents){
                        if(e.getName().equalsIgnoreCase(listDataHeader.get(groupPosition))){
                            eventC.delete(e.getId());
                        }
                    }
                    return true;
                }

                //if its edit
                Intent intent= new Intent(MainActivity.this, CreateUpdateEvent.class);

                //getting the event details
                String event = listDataHeader.get(groupPosition);
                String[] headerArray = event.split(",");

                String eventName = headerArray[0];
                String eventDate = headerArray[1];
                String eventTime = headerArray[2];

                //pass thought intent
                intent.putExtra("pickEventName", eventName);
                intent.putExtra("pickDate", eventDate);
                intent.putExtra("pickTime", eventTime);
                startActivity(intent);

                return true;
            }
        });

        popup.show();//showing popup menu
    }



    /*
 * Preparing the list data
 */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Halloween Party , Oct 31/ 2017 , 7:00 pm");
        listDataHeader.add("Thanksgiving Party , Oct 31/ 2017 , 7:00 pm");
        listDataHeader.add("Xmas Party  , Oct 31/ 2017 , 7:00 pm");

        // Adding child data
        List<String> HalloweenParty = new ArrayList<String>();
        HalloweenParty.add("Candy 2bags 20");
        HalloweenParty.add("Candy 2bags 20");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("Candy 2bags 20");
        nowShowing.add("Candy 2bags 202");
        nowShowing.add("Candy 2bags 20");
        nowShowing.add("Candy 2bags 20");
        nowShowing.add("Candy 2bags 20");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), HalloweenParty); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu. This adds items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        //Display fragment base on the menu item selected
        switch (item.getItemId()) {
            case R.id.action_create_event:
                intent = new Intent(this, CreateUpdateEvent.class);
                startActivity(intent);
                return true;
            case R.id.action_search_event:
            case R.id.action_add_items:
                intent = new Intent(this, AddItem.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
