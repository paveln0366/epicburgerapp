package com.epicburger.epicburgerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EpicBurgerDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "epicburger";
    private static final int DB_VERSION = 2;

    public EpicBurgerDatabaseHelper(Context context) {
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

    private static void insertIntoDatabase(SQLiteDatabase db,
                                           String table,
                                           String name,
                                           String description,
                                           int imageResourceId,
                                           double cost) {
        ContentValues values = new ContentValues();
        values.put("NAME", name);
        values.put("DESCRIPTION", description);
        values.put("IMAGE_RESOURCE_ID", imageResourceId);
        values.put("COST", cost);
        db.insert(table, null, values);
    }

    private void updateDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE CHEESEBURGERS (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "DESCRIPTION TEXT, "
                    + "IMAGE_RESOURCE_ID INTEGER, "
                    + "COST REAL);");
            insertIntoDatabase(db, "CHEESEBURGERS","Standard Cheeseburger",
                    "bun, cheese, cutlet, cucumber, onion, sauce",
                    R.drawable.burger_cheeseburger_standard,
                    1.20);
            insertIntoDatabase(db, "CHEESEBURGERS","Double Cheeseburger",
                    "bun, cheese, 2x cutlet, cucumber, onion, sauce",
                    R.drawable.burger_cheeseburger_double,
                    1.50);
            insertIntoDatabase(db, "CHEESEBURGERS","Junior Cheeseburger",
                    "bun, cheese, cutlet, cucumber, onion, tomato, lettuce, sauce",
                    R.drawable.burger_cheeseburger_junior,
                    2.20);
            insertIntoDatabase(db, "CHEESEBURGERS","Epic Cheeseburger",
                    "bun, cheese, big cutlet, cucumber, onion, tomato, lettuce, sauce",
                    R.drawable.burger_cheeseburger_epic,
                    2.55);

            db.execSQL("CREATE TABLE CHICKENBURGERS (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "DESCRIPTION TEXT, "
                    + "IMAGE_RESOURCE_ID INTEGER, "
                    + "COST REAL);");
            insertIntoDatabase(db, "CHICKENBURGERS","Junior Chickenburger",
                    "bun, cutlet, lettuce, sauce",
                    R.drawable.burger_chickenburger_junior,
                    1.30);
            insertIntoDatabase(db, "CHICKENBURGERS","Double Chickenburger",
                    "bun, cheese, 2x cutlet, cucumber, onion, sauce",
                    R.drawable.burger_chickenburger_double,
                    1.60);
            insertIntoDatabase(db, "CHICKENBURGERS","Delux Chickenburger",
                    "bun, cheese, cutlet, cucumber, onion, tomato, sauce",
                    R.drawable.burger_chickenburger_delux,
                    2.40);
            insertIntoDatabase(db, "CHICKENBURGERS","Chef Chickenburger",
                    "bun, big cutlet, cucumber, onion, tomato, sauce",
                    R.drawable.burger_chickenburger_chef,
                    2.50);
            insertIntoDatabase(db, "CHICKENBURGERS","XXL Chickenburger",
                    "bun, cheese, 2х big cutlet, cucumber, onion, lettuce, sauce",
                    R.drawable.burger_chickenburger_xxl,
                    3.00);
        }

        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE CHEESEBURGERS ADD COLUMN FAVORITE NUMERIC;");
            db.execSQL("ALTER TABLE CHEESEBURGERS ADD COLUMN NEW NUMERIC;");

            db.execSQL("ALTER TABLE CHICKENBURGERS ADD COLUMN FAVORITE NUMERIC;");
            db.execSQL("ALTER TABLE CHICKENBURGERS ADD COLUMN NEW NUMERIC;");
        }
    }
}
