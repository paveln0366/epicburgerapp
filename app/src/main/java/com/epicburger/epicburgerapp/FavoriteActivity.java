package com.epicburger.epicburgerapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

// TODO:

public class FavoriteActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;
    private Cursor cursorRestart;
    //private int numberOfFoods;

    int[] foodIds = new int[0];
    String[] foodNames = new String[0];
    double[] foodCosts = new double[0];
    int[] foodImages = new int[0];
    String[] foodTable = new String[0];

    int[] foodIdsRestart = new int[0];
    String[] foodNamesRestart = new String[0];
    double[] foodCostsRestart = new double[0];
    int[] foodImagesRestart = new int[0];
    String[] foodTableRestart = new String[0];

    CaptionedImagesAdapter adapter;
    GridLayoutManager manager;

    RecyclerView rvFavorite;
    LinearLayout llNoFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        rvFavorite = (RecyclerView) findViewById(R.id.rv_favorite);
        llNoFavorite = (LinearLayout) findViewById(R.id.ll_no_favorite);

        rvFavorite.setVisibility(View.GONE);
        llNoFavorite.setVisibility(View.VISIBLE);

        SQLiteOpenHelper epicBurgerDatabaseHelper = new EpicBurgerDatabaseHelper(this);
        try {
            db = epicBurgerDatabaseHelper.getReadableDatabase();

            ArrayList<String> tablesList = new ArrayList();
            tablesList.add("CHEESEBURGERS");
            tablesList.add("CHICKENBURGERS");

            ArrayList<ArrayList<?>> sqlData = sqlSelector(tablesList);

            ArrayList<Integer> arrayListItemIds = (ArrayList<Integer>) sqlData.get(0);
            ArrayList<String> names = (ArrayList<String>) sqlData.get(1);
            ArrayList<Double> cost = (ArrayList<Double>) sqlData.get(2);
            ArrayList<Integer> imageId = (ArrayList<Integer>) sqlData.get(3);
            ArrayList<String> tableNameList = (ArrayList<String>) sqlData.get(4);

            if (arrayListItemIds.isEmpty()) {
                rvFavorite.setVisibility(View.GONE);
                llNoFavorite.setVisibility(View.VISIBLE);
            } else {
                rvFavorite.setVisibility(View.VISIBLE);
                llNoFavorite.setVisibility(View.GONE);
            }

            int numberOfFoods = 0;
            for (Integer index : arrayListItemIds) {
                numberOfFoods++;
            }

            foodIds = new int[numberOfFoods];
            Integer[] arrayItemIds = arrayListItemIds.toArray(new Integer[arrayListItemIds.size()]);
            int a = 0;
            for (Integer d : arrayItemIds) {
                foodIds[a] = (int) d;
                a++;
            }

            foodNames = names.toArray(new String[names.size()]);

            foodCosts = new double[numberOfFoods];
            Double[] cheeseBurgersCostsD = cost.toArray(new Double[cost.size()]);
            int b = 0;
            for (Double d : cheeseBurgersCostsD) {
                foodCosts[b] = (double) d;
                b++;
            }

            foodImages = new int[numberOfFoods];
            Integer[] cheeseBurgersImagesD = imageId.toArray(new Integer[imageId.size()]);
            int c = 0;
            for (Integer d : cheeseBurgersImagesD) {
                foodImages[c] = (int) d;
                c++;
            }

            foodTable = tableNameList.toArray(new String[tableNameList.size()]);

            adapter = new CaptionedImagesAdapter(foodIds, foodNames, foodCosts, foodImages, foodTable);
            rvFavorite.setAdapter(adapter);

            manager = new GridLayoutManager(this, 2);
            rvFavorite.setLayoutManager(manager);

            adapter.setListener(new CaptionedImagesAdapter.Listener() {
                @Override
                public void onClick(int position, String tablesName) {
                    Intent intent = new Intent(FavoriteActivity.this, DetailActivity.class);
//                    intent.putExtra(DetailActivity.EXTRA_FOOD_ID, foodIds[position-1]);
                    intent.putExtra(DetailActivity.EXTRA_FOOD_ID, position);
//                    intent.putExtra(DetailActivity.EXTRA_FOOD_TABLE, foodTable[position]);
                    intent.putExtra(DetailActivity.EXTRA_FOOD_TABLE, tablesName);
                    startActivity(intent);
                }
            });
        } catch (SQLException e) {
            Toast toast = Toast.makeText(this, "Database unavailable! FavoriteActivity", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        try {
            ArrayList<String> tablesList = new ArrayList();
            tablesList.add("CHEESEBURGERS");
            tablesList.add("CHICKENBURGERS");

            ArrayList<ArrayList<?>> sqlData = sqlSelectorRestart(tablesList);

            ArrayList<Integer> arrayListItemIds = (ArrayList<Integer>) sqlData.get(0);
            ArrayList<String> names = (ArrayList<String>) sqlData.get(1);
            ArrayList<Double> cost = (ArrayList<Double>) sqlData.get(2);
            ArrayList<Integer> imageId = (ArrayList<Integer>) sqlData.get(3);
            ArrayList<String> tableNameList = (ArrayList<String>) sqlData.get(4);

            if (arrayListItemIds.isEmpty()) {
                rvFavorite.setVisibility(View.GONE);
                llNoFavorite.setVisibility(View.VISIBLE);
            } else {
                rvFavorite.setVisibility(View.VISIBLE);
                llNoFavorite.setVisibility(View.GONE);
            }

            int numberOfFoods = 0;
            for (Integer index : arrayListItemIds) {
                numberOfFoods++;
            }

            foodIdsRestart = new int[numberOfFoods];
            Integer[] arrayItemIds = arrayListItemIds.toArray(new Integer[arrayListItemIds.size()]);
            int a = 0;
            for (Integer d : arrayItemIds) {
                foodIdsRestart[a] = (int) d;
                a++;
            }

            foodNamesRestart = names.toArray(new String[names.size()]);


            foodCostsRestart = new double[numberOfFoods];
            Double[] cheeseBurgersCostsD = cost.toArray(new Double[cost.size()]);
            int b = 0;
            for (Double d : cheeseBurgersCostsD) {
                foodCostsRestart[b] = (double) d;
                b++;
            }

            foodImagesRestart = new int[numberOfFoods];
            Integer[] cheeseBurgersImagesD = imageId.toArray(new Integer[imageId.size()]);
            int c = 0;
            for (Integer d : cheeseBurgersImagesD) {
                foodImagesRestart[c] = (int) d;
                c++;
            }

            foodTableRestart = tableNameList.toArray(new String[tableNameList.size()]);

            adapter = new CaptionedImagesAdapter(foodIdsRestart, foodNamesRestart, foodCostsRestart, foodImagesRestart, foodTableRestart);
            rvFavorite.setAdapter(adapter);

            manager = new GridLayoutManager(this, 2);
            rvFavorite.setLayoutManager(manager);

            adapter.setListener(new CaptionedImagesAdapter.Listener() {
                @Override
                public void onClick(int position, String tablesName) {
                    Intent intent = new Intent(FavoriteActivity.this, DetailActivity.class);
//                    intent.putExtra(DetailActivity.EXTRA_FOOD_ID, foodIds[position-1]);
                    intent.putExtra(DetailActivity.EXTRA_FOOD_ID, position);
//                    intent.putExtra(DetailActivity.EXTRA_FOOD_TABLE, foodTable[position]);
                    intent.putExtra(DetailActivity.EXTRA_FOOD_TABLE, tablesName);
                    //intent.putExtra(DetailActivity.EXTRA_FOOD_TABLE, tableName);
                    startActivity(intent);
                }
            });

        } catch (SQLException e) {
            Toast toast = Toast.makeText(this, "Database unavailable! FavoriteActivity", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public ArrayList sqlSelector(ArrayList<String> tablesList) {

        ArrayList<ArrayList<?>> sqlData = new ArrayList<ArrayList<?>>();

        ArrayList<Integer> arrayListItemIds = new ArrayList<Integer>();
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<Double> cost = new ArrayList<Double>();
        ArrayList<Integer> imageId = new ArrayList<Integer>();
        ArrayList<String> tableNameList = new ArrayList<String>();


        for (String tableName : tablesList) {
            cursor = db.rawQuery("SELECT _id FROM " + tableName + " WHERE FAVORITE = ?", new String[]{"1"});
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    arrayListItemIds.add(cursor.getInt(cursor.getColumnIndex("_id")));
                    cursor.moveToNext();
                }
            }
        }

        for (String tableName : tablesList) {
            cursor = db.rawQuery("SELECT NAME FROM " + tableName + " WHERE FAVORITE = ?", new String[]{"1"});
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    names.add(cursor.getString(cursor.getColumnIndex("NAME")));
                    cursor.moveToNext();
                }
            }
        }

        for (String tableName : tablesList) {
            cursor = db.rawQuery("SELECT COST FROM " + tableName + " WHERE FAVORITE = ?", new String[]{"1"});
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    cost.add(cursor.getDouble(cursor.getColumnIndex("COST")));
                    cursor.moveToNext();
                }
            }
        }
        for (String tableName : tablesList) {
            cursor = db.rawQuery("SELECT IMAGE_RESOURCE_ID FROM " + tableName + " WHERE FAVORITE = ?", new String[]{"1"});
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    imageId.add(cursor.getInt(cursor.getColumnIndex("IMAGE_RESOURCE_ID")));
                    cursor.moveToNext();
                }
            }
        }

        for (String tableName : tablesList) {
            cursor = db.rawQuery("SELECT FOOD_TABLE FROM " + tableName + " WHERE FAVORITE = ?", new String[]{"1"});
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    tableNameList.add(cursor.getString(cursor.getColumnIndex("FOOD_TABLE")));
                    cursor.moveToNext();
                }
            }
        }

        sqlData.add(arrayListItemIds);
        sqlData.add(names);
        sqlData.add(cost);
        sqlData.add(imageId);
        sqlData.add(tableNameList);
        return sqlData;
    }

    public ArrayList sqlSelectorRestart(ArrayList<String> tablesList) {

        ArrayList<ArrayList<?>> sqlData = new ArrayList<ArrayList<?>>();

        ArrayList<Integer> arrayListItemIds = new ArrayList<Integer>();
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<Double> cost = new ArrayList<Double>();
        ArrayList<Integer> imageId = new ArrayList<Integer>();
        ArrayList<String> tableNameList = new ArrayList<String>();

        for (String tableName : tablesList) {
            cursorRestart = db.rawQuery("SELECT _id FROM " + tableName + " WHERE FAVORITE = ?", new String[]{"1"});
            if (cursorRestart.moveToFirst()) {
                while (!cursorRestart.isAfterLast()) {
                    arrayListItemIds.add(cursorRestart.getInt(cursorRestart.getColumnIndex("_id")));
                    cursorRestart.moveToNext();
                }
            }

            cursorRestart = db.rawQuery("SELECT NAME FROM " + tableName + " WHERE FAVORITE = ?", new String[]{"1"});
            if (cursorRestart.moveToFirst()) {
                while (!cursorRestart.isAfterLast()) {
                    names.add(cursorRestart.getString(cursorRestart.getColumnIndex("NAME")));
                    cursorRestart.moveToNext();
                }
            }

            cursorRestart = db.rawQuery("SELECT COST FROM " + tableName + " WHERE FAVORITE = ?", new String[]{"1"});
            if (cursorRestart.moveToFirst()) {
                while (!cursorRestart.isAfterLast()) {
                    cost.add(cursorRestart.getDouble(cursorRestart.getColumnIndex("COST")));
                    cursorRestart.moveToNext();
                }
            }

            cursorRestart = db.rawQuery("SELECT IMAGE_RESOURCE_ID FROM " + tableName + " WHERE FAVORITE = ?", new String[]{"1"});
            if (cursorRestart.moveToFirst()) {
                while (!cursorRestart.isAfterLast()) {
                    imageId.add(cursorRestart.getInt(cursorRestart.getColumnIndex("IMAGE_RESOURCE_ID")));
                    cursorRestart.moveToNext();
                }
            }

            cursor = db.rawQuery("SELECT FOOD_TABLE FROM " + tableName + " WHERE FAVORITE = ?", new String[]{"1"});
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    tableNameList.add(cursor.getString(cursor.getColumnIndex("FOOD_TABLE")));
                    cursor.moveToNext();
                }
            }
        }

        ///////////////////////////////////////////
        cursor = cursorRestart;
        ///////////////////////////////////////////

        sqlData.add(arrayListItemIds);
        sqlData.add(names);
        sqlData.add(cost);
        sqlData.add(imageId);
        sqlData.add(tableNameList);
        return sqlData;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}