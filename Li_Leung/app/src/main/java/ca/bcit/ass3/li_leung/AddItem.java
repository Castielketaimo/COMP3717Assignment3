package ca.bcit.ass3.li_leung;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import ca.bcit.ass3.li_leung.controllers.DetailController;
import ca.bcit.ass3.li_leung.controllers.EventController;
import ca.bcit.ass3.li_leung.models.Detail;

public class AddItem extends AppCompatActivity implements
        View.OnClickListener{

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
        setContentView(R.layout.activity_add_item);

        //get all views
        eventSelectView = (Spinner) findViewById(R.id.create_item_select_event);
        itemNameView = (EditText) findViewById(R.id.create_item_name);
        itemUnitView = (EditText) findViewById(R.id.create_item_unit);
        itemQuantityView = (EditText) findViewById(R.id.create_item_quantity);
        addItemView = (Button) findViewById(R.id.create_item_btm);

        //set spinner value
        EventController eventC = new EventController(this);
        List<Event>
        eventSelectView.setSelection();

        //get all the value of views
        eventSelect = eventSelectView.getSelectedItem().toString();
        itemName = itemNameView.getText().toString();
        itemUnit = itemUnitView.getText().toString();
        itemQuantity = itemQuantityView.getText().toString();

        addItemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (!eventSelect.equals("") &&
                !itemName.equals("") &&
                !itemUnit.equals("") &&
                !itemQuantity.equals("")) {
            Detail newItem = new Detail(itemName, itemUnit, itemQuantity);
            DetailController detailC = new DetailController(this);
            detailC.create(newItem);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Please insure all the field is inputted correctly" ,
                    Toast.LENGTH_LONG).show();
        }
    }
}
