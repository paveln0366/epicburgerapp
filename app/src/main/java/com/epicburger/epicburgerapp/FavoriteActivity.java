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
import android.widget.Toast;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        String[] cheeseBurgersNames = new String[4];
        double[] cheeseBurgersCosts = new double[4];
        int[] cheeseBurgersImages = new int[4];

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        RecyclerView rvFavorite = (RecyclerView) findViewById(R.id.rv_favorite);

        SQLiteOpenHelper epicBurgerDatabaseHelper = new EpicBurgerDatabaseHelper(this);
        try {
            db = epicBurgerDatabaseHelper.getReadableDatabase();

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

            CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(cheeseBurgersNames, cheeseBurgersCosts, cheeseBurgersImages);
            rvFavorite.setAdapter(adapter);

            GridLayoutManager manager = new GridLayoutManager(this, 2);
            rvFavorite.setLayoutManager(manager);

            adapter.setListener(new CaptionedImagesAdapter.Listener() {
                @Override
                public void onClick(int position) {
                    Intent intent = new Intent(FavoriteActivity.this, DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_BURGER_ID, ++position);
                    startActivity(intent);
                }
            });
        } catch (SQLException e) {
            Toast toast = Toast.makeText(this, "Database unavailable!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}