package com.example.aayzstha.pnpapp;



import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    //Database Version
    private static final int DATABASE_VERSION = 48;
    //Database Name
    private static final String DATABASENAME = "addressDb.db";
    //Database Table Names
    static final String MANUAL_ADD = "myAddress";

    //Tables Columns Names

    private static final String  custId = "cId";
    private static final String  custContact = "cNum";
    private static final String  custName = "name";
    private static final String strtAdd = "strtAdd";
    private static final String cityName = "cityName";
    private static final String districtName= "districtName";
    private static final String zoneName= "zoneName";

    //sql queries for creating various tables
    private String CREATE_MANUAL_ADD = "CREATE TABLE IF NOT EXISTS " + MANUAL_ADD + "("
            + custId + " INTEGER PRIMARY KEY AUTOINCREMENT, " + custName + " TEXT, " + custContact + " INTEGER, " +
            strtAdd + " TEXT, " + cityName + " TEXT, " + districtName + " TEXT, " +
            zoneName + " TEXT)";

    //sql queries for Droping Various Tables

    private String DROP_MANUAL_ADD = "DROP TABLE IF EXISTS " + MANUAL_ADD;

    private ArrayList<ProductModule> list = new ArrayList<>();

    public DataBaseHelper(Context context) {
        super(context, DATABASENAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MANUAL_ADD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        db.execSQL(DROP_MANUAL_ADD);
        onCreate(db);
    }

    //Inserting data to corresponding table
    public void addData(ProductModule pd, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv2 = new ContentValues();
        cv2.put(custId, pd.getPid());
        cv2.put(custName, pd.getPname());
        cv2.put(custContact, pd.getPcontact());
        cv2.put(strtAdd, pd.getPstreet());
        cv2.put(cityName, pd.getPcity());
        cv2.put(districtName, pd.getPdistrict());
        cv2.put(zoneName, pd.getPzone());

        //Inserting
        db.insert(tableName, null, cv2);
        db.close();
    }
    /*
            * get data from database through cursor and pass to corresponding table classes to
            * ProductModule and arraylist
            */

    public ArrayList<ProductModule> getData(String tableName) {
        list.clear();
        String selectQuery = "Select * from " + tableName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Traversing through all rows and adding to list
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    ProductModule item = new ProductModule();
                    item.setPid(cursor.getInt(cursor.getColumnIndex(custId)));
                    item.setPname(cursor.getString(cursor.getColumnIndex(custName)));
                    item.setPcontact(cursor.getString(cursor.getColumnIndex(custContact)));
                    item.setPstreet(cursor.getString(cursor.getColumnIndex(strtAdd)));
                    item.setPcity(cursor.getString(cursor.getColumnIndex(cityName)));
                    item.setPdistrict(cursor.getString(cursor.getColumnIndex(districtName)));
                    item.setPzone(cursor.getString(cursor.getColumnIndex(zoneName)));
                    list.add(item);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        //returning the list
        return list;
    }

    //Update corresponding table
    public void updateData(ProductModule pd, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv3 = new ContentValues();
        cv3.put(custId, pd.getPid());
        cv3.put(custName, pd.getPname());
        cv3.put(custContact, pd.getPcontact());
        cv3.put(strtAdd, pd.getPstreet());
        cv3.put(cityName, pd.getPcity());
        cv3.put(districtName, pd.getPdistrict());
        cv3.put(zoneName, pd.getPzone());
        //Updating
        db.update(tableName, cv3, custId + " = ?", new String[]{String.valueOf(pd.getPid())});
        db.close();
    }

    //Deleting data from Corresponding table
    public void removeData(ProductModule pd, String tableName) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            //deleting
            db.delete(tableName, custId + " = ? ", new String[]{String.valueOf(pd.getPid())});
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}