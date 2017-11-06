package ca.bcit.ass3.li_leung;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import ca.bcit.ass3.li_leung.controllers.DetailController;
import ca.bcit.ass3.li_leung.models.Detail;

public class UpdateDetail extends AppCompatActivity implements
        View.OnClickListener {

    Spinner eventSelectView;
    EditText itemNameView;
    EditText itemUnitView;
    EditText itemQuantityView;
    Button addItemView;

    String eventSelect;
    String itemName;
    String itemUnit;
    String itemQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);

        //getting extras from Main activity
        Intent intent = getIntent();
        eventSelect = intent.getStringExtra("eventSelect");
        itemName = intent.getStringExtra("itemName");
        itemUnit = intent.getStringExtra("itemUnit");
        itemQuantity = intent.getStringExtra("itemQuantity");

        //get all views
        eventSelectView = (Spinner) findViewById(R.id.create_item_select_event);
        itemNameView = (EditText) findViewById(R.id.create_item_name);
        itemUnitView = (EditText) findViewById(R.id.create_item_unit);
        itemQuantityView = (EditText) findViewById(R.id.create_item_quantity);
        addItemView = (Button) findViewById(R.id.create_item_btm);

        //set all files to what user selected
        selectSpinnerValue(eventSelectView, eventSelect); // set the default value of the spinner
        itemNameView.setText(itemName);
        itemUnitView.setText(itemUnit);
        itemQuantityView.setText(itemQuantity);

        //set up add btm listener
        addItemView.setOnClickListener(this);
    }

    /**
     * update database with the input user typed
     * @param view
     */
    @Override
    public void onClick(View view) {
        //when user clicked the update btm
        if (view.equals(R.id.create_item_btm)) {
            if (!eventSelect.equals("") &&
                    !itemName.equals("") &&
                    !itemUnit.equals("") &&
                    !itemQuantity.equals("")) {
                Detail newItem = new Detail(itemName, itemUnit, itemQuantity);
                DetailController detailC = new DetailController(this);
                detailC.update(newItem); // update the item details
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Please insure all the field is inputted correctly",
                        Toast.LENGTH_LONG).show();
            }

        //when user clicked the delete btm
        } else if (view.equals(R.id.delete_item_btm)) {
            DetailController detailC = new DetailController(this);
            List<Detail> allDetails = detailC.read();
            for(Detail d : allDetails){
                if(d.getName().equalsIgnoreCase(eventSelect)){
                    detailC.delete(d.getId());
                }
            }
        }
    }

    /**
     * find the position of a string in the spinner and make it as default value
     * @param spinner
     * @param myString
     */
    private void selectSpinnerValue(Spinner spinner, String myString)
    {
        for(int i = 0; i < spinner.getCount(); i++){
            if(spinner.getItemAtPosition(i).toString().equals(myString)){
                spinner.setSelection(i);
                break;
            }
        }
    }
}
