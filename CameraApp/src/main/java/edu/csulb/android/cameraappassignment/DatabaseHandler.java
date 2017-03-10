package edu.csulb.android.cameraappassignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static edu.csulb.android.cameraappassignment.MainActivity.adapter;

/**
 * Created by Mak on 2/27/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "ImageData";

    //Constructor is must for SQLiteOpenHelper
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        // SQL statement to create table
        String CREATE_IMAGE_TABLE = "CREATE TABLE images ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "caption TEXT, "+
                "path TEXT )";

        // create table
        db.execSQL(CREATE_IMAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS images");

        // create fresh table
        this.onCreate(db);
    }

    // images table name
    private static final String TABLE_IMAGES = "images";

    // images Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_CAPTION = "caption";
    private static final String KEY_PATH = "path";
    private static final String[] COLUMNS = {KEY_ID,KEY_CAPTION,KEY_PATH};

    public void addImage(String caption, String path) {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        Log.e("caption in addImage",caption);
        // 2. build query
        Cursor cursor =
                db.query(TABLE_IMAGES, // a. table
                        COLUMNS, // b. column names
                        "caption = ?", // c. selections
                        new String[]{caption}, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

// 3. if we got results get the first one
        if (cursor.getCount()>0) {
            Log.e("Query Executed","INSERT - if part");
            cursor.moveToFirst();
    /*        // 2. create ContentValues to add key "column"/value
            ContentValues values = new ContentValues();
            values.put(KEY_CAPTION, caption); // get caption
            values.put(KEY_PATH, path); // get path
*/
        } else {
            // 2. create ContentValues to add key "column"/value
            Log.e("Query Executed","Insert - Else part");
            ContentValues values = new ContentValues();
            values.put(KEY_CAPTION, caption); // get caption
            values.put(KEY_PATH, path); // get path

            // 3. insert
            db.insert(TABLE_IMAGES, // table
                    null, //nullColumnHack
                    values); // key/value -> keys = column names/ values = column values

            // 4. close
            db.close();
        }

    }

    public String getPath(String caption){
        SQLiteDatabase db = this.getWritableDatabase();
        String path = null;
        Cursor cursor =
                db.query(TABLE_IMAGES, // a. table
                        COLUMNS, // b. column names
                        "caption = ?", // c. selections
                        new String[]{caption}, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

       // if we got results get the first one
        if (cursor.getCount()>0) {
            Log.e("Query Executed","getPATH - if part");
            cursor.moveToFirst();

            path= cursor.getString(2);

        }

return path;
    }

   public List<ImageInfo> getAllData(){
       List<ImageInfo> images = new ArrayList<ImageInfo>();

       // 1. build the query
       String query = "SELECT  * FROM "+ TABLE_IMAGES ;

       // 2. get reference to writable DB
       SQLiteDatabase db = this.getReadableDatabase();
       Cursor cursor = db.rawQuery(query, null);
Log.e("Query Executed","SELECT QUERY");
       // 3. go over each row, build book and add it to list
       ImageInfo image = null;
       if (cursor.moveToFirst()) {
           do {
               image = new ImageInfo(cursor.getString(1),cursor.getString(2));
               Log.e("getAllData",cursor.getString(1));
               Log.e("getAllData",cursor.getString(2));
               images.add(image);
           } while (cursor.moveToNext());
       }

       Log.e("getAllBooks()", images.toString());
db.close();
       return images;
    }



}

