package ca.bcit.ass3.li_leung.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ca.bcit.ass3.li_leung.DatabaseHandler;
import ca.bcit.ass3.li_leung.models.Event;

/**
 * Controller for the Event_Master table.
 *
 * @author Castiel Li & Justin Leung
 */
public class EventController extends DatabaseHandler
{
    /** Event_Master table name. */
    private static final String TABLE_NAME = "Event_Master";

    /** ID column name. */
    private static final String ID_COLUMN_NAME = "_eventId";

    /**
     * Inherit/use DatabaseHandler constructor.
     *
     * @param context - Apps Current Content
     */
    public EventController(Context context)
    {
        super(context);
    }

    /**
     * Creates a new event.
     *
     * @param event - event being made.
     * @return success - true event was made.
     */
    public boolean create(Event event)
    {
        // Prepare insertion.
        ContentValues values = new ContentValues();
        values.put("Name", event.getName());
        values.put("Date", event.getDate());
        values.put("Time", event.getTime());

        // Connect to database, insert, and close database.
        SQLiteDatabase db = this.getWritableDatabase();
        boolean success = db.insert(TABLE_NAME, null, values) > 0;
        db.close();

        // Return whether success or not.
        return success;
    }

    /**
     * Returns all event records.
     *
     * @return recordList - All events in Event_Master
     */
    public List<Event> read()
    {
        // New list of events.
        List<Event> recordsList = new ArrayList<Event>();

        // SQL statement to grab all events ordered by descending.
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + ID_COLUMN_NAME + " DESC";

        // Create new db instance, and cursor instance.
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        // Go through and add all events to the event list.
        if (cursor.moveToFirst())
        {
            do
            {
                // Get event details
                int _eventId = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID_COLUMN_NAME)));
                String Name = cursor.getString(cursor.getColumnIndex("Name"));
                String Date = cursor.getString(cursor.getColumnIndex("Date"));
                String Time = cursor.getString(cursor.getColumnIndex("Time"));

                // Create new event instance, with eventId.
                Event event = new Event(Name, Date, Time, _eventId);

                // Add record to list.
                recordsList.add(event);
            }
            while (cursor.moveToNext());
        }

        // Close cursor and database.
        cursor.close();
        db.close();

        // Return list of events.
        return recordsList;
    }

    /**
     * Read a single event.
     *
     * @param eventId - Events Id in the database.
     * @return event - Event object.
     */
    public Event readSingleRecord(int eventId)
    {
        // Create event object.
        Event event = null;

        // Grab event from DB with specific event id.
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE _eventId = " + eventId;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        // If event exists.
        if (cursor.moveToFirst())
        {
            // Get event details
            int _eventId = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID_COLUMN_NAME)));
            String Name = cursor.getString(cursor.getColumnIndex("Name"));
            String Date = cursor.getString(cursor.getColumnIndex("Date"));
            String Time = cursor.getString(cursor.getColumnIndex("Time"));

            // Assign the event object.
            event = new Event(Name, Date, Time, _eventId);
        }

        // Close cursor and db.
        cursor.close();
        db.close();

        // Return the event.
        return event;
    }

    /**
     * Update event.
     *
     * @param event - Event being updated.
     * @return success - True if update was successful.
     */
    public boolean update(Event event)
    {
        // Prepare update.
        ContentValues values = new ContentValues();
        values.put("Name", event.getName());
        values.put("Date", event.getDate());
        values.put("Time", event.getTime());

        // Prepare update on specific event id.
        String where = ID_COLUMN_NAME + " = ?";
        String[] whereArgs = { Integer.toString(event.getId()) };

        // Connect, update, and close connection.
        SQLiteDatabase db = this.getWritableDatabase();
        boolean success = db.update(TABLE_NAME, values, where, whereArgs) > 0;
        db.close();

        // Return whether successful or not.
        return success;
    }

    /**
     * Delete an event record.
     *
     * @param eventId - Id for the record to delete.
     * @return success - True if deletion worked.
     */
    public boolean delete(int eventId)
    {
        // Connect, delete, and close connection.
        SQLiteDatabase db = this.getWritableDatabase();
        boolean success = db.delete(TABLE_NAME, ID_COLUMN_NAME + " ='" + eventId + "'", null) > 0;
        db.close();

        // Return whether successful.
        return success;
    }
}
