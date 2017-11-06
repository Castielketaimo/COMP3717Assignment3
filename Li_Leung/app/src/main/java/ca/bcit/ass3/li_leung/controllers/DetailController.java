package ca.bcit.ass3.li_leung.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ca.bcit.ass3.li_leung.DatabaseHandler;
import ca.bcit.ass3.li_leung.models.Detail;

/**
 * Controller for the Event_Detail table.
 *
 * @author Castiel Li & Justin Leung
 */
public class DetailController extends DatabaseHandler
{
    /** Event_Detail table name. */
    private static final String TABLE_NAME = "Event_Detail";

    /** ID column name. */
    private static final String ID_COLUMN_NAME = "_detailId";

    /**
     * Inherit/use DatabaseHandler constructor.
     *
     * @param context - Apps Current Content
     */
    public DetailController(Context context)
    {
        super(context);
    }

    /**
     * Creates a new detail.
     *
     * @param detail - detail being created.
     * @return success - true detail was made.
     */
    public boolean create(Detail detail)
    {
        // Prepare insertion.
        ContentValues values = new ContentValues();
        values.put("ItemName", detail.getName());
        values.put("ItemUnit", detail.getUnit());
        values.put("ItemQuantity", detail.getQuantity());

        // Connect to database, insert, and close database.
        SQLiteDatabase db = this.getWritableDatabase();
        boolean success = db.insert(TABLE_NAME, null, values) > 0;
        db.close();

        // Return whether success or not.
        return success;
    }

    /**
     * Returns all detail records.
     *
     * @return recordList - All details in Event_Details
     */
    public List<Detail> read()
    {
        // New list of details.
        List<Detail> recordsList = new ArrayList<>();

        // SQL statement to grab all details ordered by descending.
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + ID_COLUMN_NAME + " DESC";

        // Create new db instance, and cursor instance.
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        // Go through and add all details to the detail list.
        if (cursor.moveToFirst())
        {
            do
            {
                // Get detail details
                int _detailId = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID_COLUMN_NAME)));
                String Name = cursor.getString(cursor.getColumnIndex("ItemName"));
                String Date = cursor.getString(cursor.getColumnIndex("ItemUnit"));
                String Time = cursor.getString(cursor.getColumnIndex("ItemQuantity"));

                // Create new detail instance, with detailId.
                Detail detail = new Detail(Name, Date, Time, _detailId);

                // Add record to list.
                recordsList.add(detail);
            }
            while (cursor.moveToNext());
        }

        // Close cursor and database.
        cursor.close();
        db.close();

        // Return list of details.
        return recordsList;
    }

    /**
     * Read a single detail.
     *
     * @param detailId - Details Id in the database.
     * @return detail - Detail object.
     */
    public Detail readSingleRecord(int detailId)
    {
        // Create detail object.
        Detail detail = null;

        // Grab detail from DB with specific detail id.
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE _detailId = " + detailId;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        // If detail exists.
        if (cursor.moveToFirst())
        {
            // Get detail details
            int _detailId = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID_COLUMN_NAME)));
            String ItemName = cursor.getString(cursor.getColumnIndex("ItemName"));
            String ItemUnit = cursor.getString(cursor.getColumnIndex("ItemUnit"));
            String ItemQuantity = cursor.getString(cursor.getColumnIndex("ItemQuantity"));

            // Assign the detail object.
            detail = new Detail(ItemName, ItemUnit, ItemQuantity, _detailId);
        }

        // Close cursor and db.
        cursor.close();
        db.close();

        // Return the detail.
        return detail;
    }

    /**
     * Update detail.
     *
     * @param detail - Detail being updated.
     * @return success - True if update was successful.
     */
    public boolean update(Detail detail)
    {
        // Prepare update.
        ContentValues values = new ContentValues();
        values.put("ItemName", detail.getName());
        values.put("ItemUnit", detail.getUnit());
        values.put("ItemQuantity", detail.getQuantity());

        // Prepare update on specific detail id.
        String where = ID_COLUMN_NAME + " = ?";
        String[] whereArgs = { Integer.toString(detail.getId()) };

        // Connect, update, and close connection.
        SQLiteDatabase db = this.getWritableDatabase();
        boolean success = db.update(TABLE_NAME, values, where, whereArgs) > 0;
        db.close();

        // Return whether successful or not.
        return success;
    }

    /**
     * Delete an detail record.
     *
     * @param detailId - Id for the record to delete.
     * @return success - True if deletion worked.
     */
    public boolean delete(int detailId)
    {
        // Connect, delete, and close connection.
        SQLiteDatabase db = this.getWritableDatabase();
        boolean success = db.delete(TABLE_NAME, ID_COLUMN_NAME + " ='" + detailId + "'", null) > 0;
        db.close();

        // Return whether successful.
        return success;
    }
}
