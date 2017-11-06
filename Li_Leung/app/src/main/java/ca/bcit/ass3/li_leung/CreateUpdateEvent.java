package ca.bcit.ass3.li_leung;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import ca.bcit.ass3.li_leung.controllers.EventController;
import ca.bcit.ass3.li_leung.models.Event;

public class CreateUpdateEvent extends AppCompatActivity implements
        View.OnClickListener{

    Intent intent;
    EditText pickEventName;
    TextView pickDate;
    TextView pickTime;
    Button createEvent;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        //get views on create event page
        pickEventName = (EditText) findViewById(R.id.create_event_name);
        pickDate = (TextView) findViewById(R.id.create_event_date);
        pickTime = (TextView) findViewById(R.id.create_event_time);
        createEvent = (Button) findViewById(R.id.create_event_btm);

        //set onClickListener
        pickDate.setOnClickListener(this);
        pickTime.setOnClickListener(this);
        createEvent.setOnClickListener(this);

        //set default value if user come here to update event
        intent = getIntent();
        if (intent.hasExtra("pickEventName")){
            setDefaultValue();
        }
    }

    /**
     * Set up the events after user click on one of the elements in the create event activity
     * the next action will depends on what user clicked
     * @param view
     */
            @Override
            public void onClick(View view) {

                //start
                if (view == pickDate) {

                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);

                    // Launch Time Picker Dialog
                    DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(android.widget.DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                                    //set the date in text box
                                    pickDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }


                if (view == pickTime) {

                    // Get Current Time
                    final Calendar c = Calendar.getInstance();
                    mHour = c.get(Calendar.HOUR_OF_DAY);
                    mMinute = c.get(Calendar.MINUTE);

                    // Launch Time Picker Dialog
                    TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {

                                    //set the date in text box
                                    pickTime.setText(hourOfDay + ":" + minute);
                                }
                            }, mHour, mMinute, false);
                    timePickerDialog.show();
                }

                if(view == createEvent) {
                    //check if user input all field
                    if(     !pickEventName.getText().toString().equals("") &&
                            !pickDate.getText().toString().equals("") &&
                            !pickTime.getText().toString().equals("")
                            )
                    {
                        Event newEvent = new Event(pickEventName.getText().toString(),
                                pickDate.getText().toString(),
                                pickTime.getText().toString());

                        EventController eventC = new EventController(this);
                        eventC.create(newEvent);
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Please insure all the field is inputted correctly" ,
                                Toast.LENGTH_LONG).show();
                    }
                }
            }

    /**
     * set up the default value if user come to this class to update their event
     */
    private void setDefaultValue(){
        pickEventName.setText(intent.getStringExtra("pickEventName"));
        pickDate.setText(intent.getStringExtra("pickDate"));
        pickTime.setText(intent.getStringExtra("pickTime"));
        createEvent.setText("Update");
    }

}
