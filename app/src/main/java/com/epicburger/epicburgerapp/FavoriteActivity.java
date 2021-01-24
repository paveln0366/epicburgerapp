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

// TODO: Update this activity for different tables

public class FavoriteActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;
    private Cursor cursorRestart;

    int[] itemIds = new int[4];
    String[] cheeseBurgersNames = new String[4];
    double[] cheeseBurgersCosts = new double[4];
    int[] cheeseBurgersImages = new int[4];

    int[] itemIdsRestart = new int[4];
    String[] cheeseBurgersNamesRestart = new String[4];
    double[] cheeseBurgersCostsRestart = new double[4];
    int[] cheeseBurgersImagesRestart = new int[4];

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


        SQLiteOpenHelper epicBurgerDatabaseHelper = new EpicBurgerDatabaseHelper(this);
        try {
            db = epicBurgerDatabaseHelper.getReadableDatabase();

            cursor = db.rawQuery("SELECT _id FROM CHEESEBURGERS WHERE FAVORITE = ?", new String[]{"1"});
            if (cursor.moveToFirst()) {
                rvFavorite.setVisibility(View.VISIBLE);
                llNoFavorite.setVisibility(View.GONE);
                ArrayList<Integer> arrayListItemIds = new ArrayList<Integer>();
                while (!cursor.isAfterLast()) {
                    arrayListItemIds.add(cursor.getInt(cursor.getColumnIndex("_id")));
                    cursor.moveToNext();
                }
                Integer[] arrayItemIds = arrayListItemIds.toArray(new Integer[arrayListItemIds.size()]);
                int i = 0;
                for (Integer d : arrayItemIds) {
                    itemIds[i] = (int) d;
                    i++;
                }
            }

            cursor = db.rawQuery("SELECT NAME FROM CHEESEBURGERS WHERE FAVORITE = ?", new String[] {"1"});
            if (cursor.moveToFirst()) {
                ArrayList<String> names = new ArrayList<String>();
                while (!cursor.isAfterLast()) {
                    names.add(cursor.getString(cursor.getColumnIndex("NAME")));
                    cursor.moveToNext();
                }
                cheeseBurgersNames = names.toArray(new String[names.size()]);
            }

            cursor = db.rawQuery("SELECT COST FROM CHEESEBURGERS WHERE FAVORITE = ?", new String[]{"1"});
            if (cursor.moveToFirst()) {
                ArrayList<Double> cost = new ArrayList<Double>();
                while (!cursor.isAfterLast()) {
                    cost.add(cursor.getDouble(cursor.getColumnIndex("COST")));
                    cursor.moveToNext();
                }
                Double[] cheeseBurgersCostsD = cost.toArray(new Double[cost.size()]);
                int i = 0;
                for (Double d : cheeseBurgersCostsD) {
                    cheeseBurgersCosts[i] = (double) d;
                    i++;
                }
            }

            cursor = db.rawQuery("SELECT IMAGE_RESOURCE_ID FROM CHEESEBURGERS WHERE FAVORITE = ?", new String[]{"1"});
            if (cursor.moveToFirst()) {
                ArrayList<Integer> imageId = new ArrayList<Integer>();
                while (!cursor.isAfterLast()) {
                    imageId.add(cursor.getInt(cursor.getColumnIndex("IMAGE_RESOURCE_ID")));
                    cursor.moveToNext();
                }
                Integer[] cheeseBurgersImagesD = imageId.toArray(new Integer[imageId.size()]);
                int i = 0;
                for (Integer d : cheeseBurgersImagesD) {
                    cheeseBurgersImages[i] = (int) d;
                    i++;
                }
            }

            adapter = new CaptionedImagesAdapter(itemIds, cheeseBurgersNames, cheeseBurgersCosts, cheeseBurgersImages);
            rvFavorite.setAdapter(adapter);

            manager = new GridLayoutManager(this, 2);
            rvFavorite.setLayoutManager(manager);

            adapter.setListener(new CaptionedImagesAdapter.Listener() {
                @Override
                public void onClick(int position) {
                    Intent intent = new Intent(FavoriteActivity.this, DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_FOOD_ID, itemIds[position]);
                    intent.putExtra(DetailActivity.EXTRA_FOOD_TABLE, "CHEESEBURGERS");
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

        rvFavorite = (RecyclerView) findViewById(R.id.rv_favorite);
       llNoFavorite = (LinearLayout) findViewById(R.id.ll_no_favorite);

        cursorRestart = db.rawQuery("SELECT _id FROM CHEESEBURGERS WHERE FAVORITE = ?", new String[]{"1"});
        if (cursorRestart.moveToFirst()) {
            ///////////////////////////////////////////
            rvFavorite.setVisibility(View.VISIBLE);
            llNoFavorite.setVisibility(View.GONE);
            ///////////////////////////////////////////
            ArrayList<Integer> arrayListItemIds = new ArrayList<Integer>();
            while (!cursorRestart.isAfterLast()) {
                arrayListItemIds.add(cursorRestart.getInt(cursorRestart.getColumnIndex("_id")));
                cursorRestart.moveToNext();
            }
            Integer[] arrayItemIds = arrayListItemIds.toArray(new Integer[arrayListItemIds.size()]);
            int i = 0;
            for (Integer d : arrayItemIds) {
                itemIdsRestart[i] = (int) d;
                i++;
            }
        } else {
            ///////////////////////////////////////////
            rvFavorite.setVisibility(View.GONE);
            llNoFavorite.setVisibility(View.VISIBLE);
            ///////////////////////////////////////////
        }

        cursorRestart = db.rawQuery("SELECT NAME FROM CHEESEBURGERS WHERE FAVORITE = ?", new String[] {"1"});
        if (cursorRestart.moveToFirst()) {
            ArrayList<String> names = new ArrayList<String>();
            while (!cursorRestart.isAfterLast()) {
                names.add(cursorRestart.getString(cursorRestart.getColumnIndex("NAME")));
                cursorRestart.moveToNext();
            }
            cheeseBurgersNamesRestart = names.toArray(new String[names.size()]);
        }

        cursorRestart = db.rawQuery("SELECT COST FROM CHEESEBURGERS WHERE FAVORITE = ?", new String[]{"1"});
        if (cursorRestart.moveToFirst()) {
            ArrayList<Double> cost = new ArrayList<Double>();
            while (!cursorRestart.isAfterLast()) {
                cost.add(cursorRestart.getDouble(cursorRestart.getColumnIndex("COST")));
                cursorRestart.moveToNext();
            }
            Double[] cheeseBurgersCostsD = cost.toArray(new Double[cost.size()]);
            int i = 0;
            for (Double d : cheeseBurgersCostsD) {
                cheeseBurgersCostsRestart[i] = (double) d;
                i++;
            }
        }

        cursorRestart = db.rawQuery("SELECT IMAGE_RESOURCE_ID FROM CHEESEBURGERS WHERE FAVORITE = ?", new String[]{"1"});
        if (cursorRestart.moveToFirst()) {
            ArrayList<Integer> imageId = new ArrayList<Integer>();
            while (!cursorRestart.isAfterLast()) {
                imageId.add(cursorRestart.getInt(cursorRestart.getColumnIndex("IMAGE_RESOURCE_ID")));
                cursorRestart.moveToNext();
            }
            Integer[] cheeseBurgersImagesD = imageId.toArray(new Integer[imageId.size()]);
            int i = 0;
            for (Integer d : cheeseBurgersImagesD) {
                cheeseBurgersImagesRestart[i] = (int) d;
                i++;
            }
        }

        adapter = new CaptionedImagesAdapter(itemIdsRestart, cheeseBurgersNamesRestart, cheeseBurgersCostsRestart, cheeseBurgersImagesRestart);
        rvFavorite.setAdapter(adapter);

        manager = new GridLayoutManager(this, 2);
        rvFavorite.setLayoutManager(manager);

        adapter.setListener(new CaptionedImagesAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(FavoriteActivity.this, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_FOOD_ID, itemIds[position]);
                intent.putExtra(DetailActivity.EXTRA_FOOD_TABLE, "CHEESEBURGERS");
                startActivity(intent);
            }
        });
        ///////////////////////////////////////////
        cursor = cursorRestart;
        ///////////////////////////////////////////
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}