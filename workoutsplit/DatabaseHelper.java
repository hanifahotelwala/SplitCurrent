package com.example.android.splitfeatures.workoutsplit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * for workoutsplit
 */

    public class DatabaseHelper extends SQLiteOpenHelper {

        private static final String TAG = "DatabaseHelper";
        private static final String TABLE_NAME = "workoutsplit_table";
        private static final String COL1 = "ID";
        private static final String COL2 = "WORKOUT";
        private static final String COL3 = "SETS";
        private static final String COL4 = "REPS";

        public DatabaseHelper(Context context) {
            super(context, TABLE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL2 + " TEXT, " + COL3 + " INTEGER, " + COL4 + " INTEGER)";
            db.execSQL(createTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

        public boolean addData(String item)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL2, item);

            Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);

            long result = db.insert(TABLE_NAME, null, contentValues);

            //if date as inserted incorrectly it will return -1
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }
        public boolean addData(String workout, int sets, int reps)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL2, workout);
            contentValues.put(COL3, sets);
            contentValues.put(COL4, reps);

            Log.d(TAG, "addData: Adding " + workout + " to " + TABLE_NAME);

            long result = db.insert(TABLE_NAME, null, contentValues);

            //if date as inserted incorrectly it will return -1
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }

        /**
         * Returns all the data from database
         * @return
         */
        public Cursor getData(){
            SQLiteDatabase db = this.getWritableDatabase();
            String query = "SELECT * FROM " + TABLE_NAME;
            Cursor data = db.rawQuery(query, null);
            return data;
        }


    public void deleteAll() {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_NAME,null,null);
            db.close();
               // String query = "DELETE FROM TABLE_NAME";
                //db.execSQL(query);

    }



    /**
         * Returns only the ID that matches the name passed in
         * @param workout
         * @return
         */
        public Cursor getItemID(String workout){
            SQLiteDatabase db = this.getWritableDatabase();
            String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                    " WHERE " + COL2 + " = '" + workout + "'";
            Cursor data = db.rawQuery(query, null);
            return data;
        }

        /**
         * Updates the name field
         * @param newName
         * @param id
         * @param oldName
         */
        public void updateName(String newName, int id, String oldName){
            SQLiteDatabase db = this.getWritableDatabase();
            String query = "UPDATE " + TABLE_NAME + " SET " + COL2 +
                    " = '" + newName + "' WHERE " + COL1 + " = '" + id + "'" +
                    " AND " + COL2 + " = '" + oldName + "'";
            Log.d(TAG, "updateName: query: " + query);
            Log.d(TAG, "updateName: Setting name to " + newName);
            db.execSQL(query);
        }

        /**
         * Delete from database
         * @param id
         * @param name
         */
        public void deleteName(int id, String name){
            SQLiteDatabase db = this.getWritableDatabase();
            String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                    + COL1 + " = '" + id + "'" +
                    " AND " + COL2 + " = '" + name + "'";
            Log.d(TAG, "deleteName: query: " + query);
            Log.d(TAG, "deleteName: Deleting " + name + " from database.");
            db.execSQL(query);
        }

    }



/**
 * Source: inspiration for set up was retrieved from this gitHub repository.
 * edited the code to suit my needs:  https://github.com/mitchtabian/SaveReadWriteDeleteSQLite
 */