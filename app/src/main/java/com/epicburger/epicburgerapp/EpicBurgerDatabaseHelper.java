package com.epicburger.epicburgerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EpicBurgerDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "epicburger";
    private static final int DB_VERSION = 2;

    EpicBurgerDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateDatabase(db, oldVersion, newVersion);
    }

    private static void insertIntoDatabase(SQLiteDatabase db, String name,
                                           String description, int imageResourceId,
                                           double cost) {
        ContentValues values = new ContentValues();
        values.put("NAME", name);
        values.put("DESCRIPTION", description);
        values.put("IMAGE_RESOURCE_ID", imageResourceId);
        values.put("COST", cost);
        db.insert("BURGERS", null, values);
    }

    private void updateDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE BURGERS (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "DESCRIPTION TEXT, "
                    + "IMAGE_RESOURCE_ID INTEGER, "
                    + "COST REAL);");
            insertIntoDatabase(db, "Standard Cheeseburger",
                    "bun, cheese, cutlet, cucumber, onion, sauce",
                    R.drawable.cheeseburger_standard,
                    1.20);
            insertIntoDatabase(db, "Double Cheeseburger",
                    "bun, cheese, 2x cutlet, cucumber, onion, sauce",
                    R.drawable.cheeseburger_double,
                    1.50);
            insertIntoDatabase(db, "Junior Cheeseburger",
                    "bun, cheese, cutlet, cucumber, onion, tomato, lettuce, sauce",
                    R.drawable.cheeseburger_junior,
                    2.20);
            insertIntoDatabase(db, "Epic Cheeseburger",
                    "bun, cheese, big cutlet, cucumber, onion, tomato, lettuce, sauce",
                    R.drawable.cheeseburger_epic,
                    2.50);
        }

        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE BURGERS ADD COLUMN FAVORITE NUMERIC;");
            db.execSQL("ALTER TABLE BURGERS ADD COLUMN NEW NUMERIC;");
        }
    }
}
