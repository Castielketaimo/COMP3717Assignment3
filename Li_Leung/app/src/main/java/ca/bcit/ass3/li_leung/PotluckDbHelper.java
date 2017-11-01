package ca.bcit.ass3.li_leung;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by castiel on 2017-10-31.
 */

public class PotluckDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "poluckDb.sqlite";
    private static final int DB_VERSION = 1;
    private Context context;

    public PotluckDbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        updateMyDatabase(sqLiteDatabase, i, i1);
    }


    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            if (oldVersion <1) {
                db.execSQL(getCreatePotluckTableSql());
                insertEvent(db);
            }
        }
        catch (SQLException sqle) {
            String msg = "[PotluckDbHelper / updateMyDatabase] DB unavailable";
            msg += "\n\n" + sqle.toString();
            Toast t = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            t.show();
        }
    }

    private String getCreatePotluckTableSql() {
        String sql = "";
        sql += "CREATE TABLE EVENT_MASTER (";
        sql += "_eventId INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += "NAME TEXT, ";
        sql += "DATE DATE, ";
        sql += "TIME TIME;";

        return sql;
    }

    private void insertEvent(SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put("NAME", "Halloween Party");
        values.put("DATE", "Oct 30, 2017");
        values.put("TIME", "6:30 PM");

        db.insert("EVENT_MASTER", null,values);

        ContentValues values1 = new ContentValues();
        values1.put("NAME", "Christmas Party");
        values1.put("DATE", "December 20, 2017");
        values1.put("TIME", "12:30 PM");

        db.insert("EVENT_MASTER", null,values1);

        ContentValues values2 = new ContentValues();
        values2.put("NAME", "New Year Eve");
        values2.put("DATE", "December 31, 2017");
        values2.put("TIME", "8:00 PM");

        db.insert("EVENT_MASTER", null,values2);
    }

}
