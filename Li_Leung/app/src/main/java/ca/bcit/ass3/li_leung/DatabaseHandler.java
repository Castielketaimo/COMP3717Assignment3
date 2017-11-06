package ca.bcit.ass3.li_leung;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Database Wrapper/Handler for the application.
 *
 * @author Castiel Li & Justin Leung
 */
public class DatabaseHandler extends SQLiteOpenHelper
{

    /** Potluck database name. */
    private static final String DB_NAME = "Potluck";

    /** Database version number. */
    private static final int DB_VERSION = 2;

    /**
     * Provides helper with applications context.
     *
     * @param context - Apps Current Content
     */
    public DatabaseHandler(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Called when the database is created for the first time.
     *
     * @param db - SQLite db connection
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        dropTables(db); // For development only.
        initDatabase(db);
    }

    /**
     * Called when the database needs to be upgraded.
     *
     * @param db - SQLite db connection
     * @param oldVersion - Old db version number
     * @param newVersion - New db version number
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        dropTables(db);
        initDatabase(db);
    }

    /**
     * Called when the database needs to be upgraded.
     *
     * @param db - SQLite db connection
     */
    @Override
    public void onOpen(SQLiteDatabase db)
    {
        initDatabase(db);
    }

    /**
     * Initializes the structure of the database.
     *
     * @param db - SQLite db connection
     */
    private static void initDatabase(SQLiteDatabase db)
    {
        // Event_Master table.
        String event_master_table_sql = "CREATE TABLE IF NOT EXISTS Event_Master (_eventId INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Date TEXT, Time TEXT);";

// Event_Detail table.
        String event_detail_table_sql = "CREATE TABLE IF NOT EXISTS Event_Detail (_detailId INTEGER PRIMARY KEY AUTOINCREMENT, ItemName TEXT, ItemUnit TEXT, ItemQuantity TEXT, eventId INTEGER, FOREIGN KEY (eventId) REFERENCES Event_Master(_eventId));";
        // Contribution table.
        String contribution_table_sql = "CREATE TABLE IF NOT EXISTS Contribution (" + "_contributionId INTEGER PRIMARY KEY AUTOINCREMENT, " + "Name TEXT, " + "Quantity TEXT, " + "Date TEXT);";

        // Create the tables/execute the SQL.
        db.execSQL(event_master_table_sql);
        db.execSQL(event_detail_table_sql);
        db.execSQL(contribution_table_sql);
    }

    /**
     * Clears the database of all tables.
     *
     * @param db - SQLite db connection
     */
    private static void dropTables(SQLiteDatabase db)
    {
        // Query to obtain the names of all tables in your database
        Cursor c = db.rawQuery("SELECT name FROM Potluck WHERE type='table'", null);
        List<String> tables = new ArrayList<>();

        // Iterate over the result set, adding every table name to a list
        while (c.moveToNext())
        {
            tables.add(c.getString(0));
        }

        // Call DROP TABLE on every table name
        for (String table : tables)
        {
            String dropQuery = "DROP TABLE IF EXISTS " + table;
            db.execSQL(dropQuery);
        }

        // Close cursor.
        c.close();
    }
}
