package ca.bcit.ass3.li_leung.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ca.bcit.ass3.li_leung.DatabaseHandler;
import ca.bcit.ass3.li_leung.models.Contribution;

/**
 * Controller for the Contribution table.
 *
 * @author Castiel Li & Justin Leung
 */
public class ContributionController extends DatabaseHandler
{
    /** Contribution table name. */
    private static final String TABLE_NAME = "Contribution";

    /** ID column name. */
    private static final String ID_COLUMN_NAME = "_contributionId";

    /**
     * Inherit/use DatabaseHandler constructor.
     *
     * @param context - Apps Current Content
     */
    public ContributionController(Context context)
    {
        super(context);
    }

    /**
     * Creates a new contribution.
     *
     * @param contribution - contribution being created.
     * @return success - true contribution was made.
     */
    public boolean create(Contribution contribution)
    {
        // Prepare insertion.
        ContentValues values = new ContentValues();
        values.put("Name", contribution.getName());
        values.put("Quantity", contribution.getQuantity());
        values.put("Date", contribution.getDate());

        // Connect to database, insert, and close database.
        SQLiteDatabase db = this.getWritableDatabase();
        boolean success = db.insert(TABLE_NAME, null, values) > 0;
        db.close();

        // Return whether success or not.
        return success;
    }

    /**
     * Returns all contribution records.
     *
     * @return recordList - All contributions in Contribution table.
     */
    public List<Contribution> read()
    {
        // New list of contributions.
        List<Contribution> recordsList = new ArrayList<>();

        // SQL statement to grab all contributions ordered by descending.
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + ID_COLUMN_NAME + " DESC";

        // Create new db instance, and cursor instance.
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        // Go through and add all contributions to the contribution list.
        if (cursor.moveToFirst())
        {
            do
            {
                // Get contribution details
                int _contributionId = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID_COLUMN_NAME)));
                String Name = cursor.getString(cursor.getColumnIndex("Name"));
                String Quantity = cursor.getString(cursor.getColumnIndex("Quantity"));
                String Date = cursor.getString(cursor.getColumnIndex("Date"));

                // Create new contribution instance, with contributionId.
                Contribution contribution = new Contribution(Name, Quantity, Date, _contributionId);

                // Add record to list.
                recordsList.add(contribution);
            }
            while (cursor.moveToNext());
        }

        // Close cursor and database.
        cursor.close();
        db.close();

        // Return list of contributions.
        return recordsList;
    }

    /**
     * Read a single contribution.
     *
     * @param contributionId - contributions Id in the database.
     * @return contribution - contribution object.
     */
    public Contribution readSingleRecord(int contributionId)
    {
        // Create contribution object.
        Contribution contribution = null;

        // Grab contribution from DB with specific contribution id.
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " = " + contributionId;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        // If contribution exists.
        if (cursor.moveToFirst())
        {
            // Get contribution details
            int _contributionId = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID_COLUMN_NAME)));
            String Name = cursor.getString(cursor.getColumnIndex("Name"));
            String Quantity = cursor.getString(cursor.getColumnIndex("Quantity"));
            String Date = cursor.getString(cursor.getColumnIndex("Date"));

            // Assign the contribution object.
            contribution = new Contribution(Name, Quantity, Date, _contributionId);
        }

        // Close cursor and db.
        cursor.close();
        db.close();

        // Return the contribution.
        return contribution;
    }

    /**
     * Update contribution.
     *
     * @param contribution - contribution being updated.
     * @return success - true if update was successful.
     */
    public boolean update(Contribution contribution)
    {
        // Prepare update.
        ContentValues values = new ContentValues();
        values.put("Name", contribution.getName());
        values.put("Quantity", contribution.getQuantity());
        values.put("Date", contribution.getDate());

        // Prepare update on specific contribution id.
        String where = ID_COLUMN_NAME + " = ?";
        String[] whereArgs = { Integer.toString(contribution.getId()) };

        // Connect, update, and close connection.
        SQLiteDatabase db = this.getWritableDatabase();
        boolean success = db.update(TABLE_NAME, values, where, whereArgs) > 0;
        db.close();

        // Return whether successful or not.
        return success;
    }

    /**
     * Delete an contribution record.
     *
     * @param contributionId - Id for the record to delete.
     * @return success - True if deletion worked.
     */
    public boolean delete(int contributionId)
    {
        // Connect, delete, and close connection.
        SQLiteDatabase db = this.getWritableDatabase();
        boolean success = db.delete(TABLE_NAME, ID_COLUMN_NAME + " ='" + contributionId + "'", null) > 0;
        db.close();

        // Return whether successful.
        return success;
    }
}
