package com.epicburger.epicburgerapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_FOOD_ID = "foodId";
    public static final String EXTRA_FOOD_TABLE = "foodTable";
    private int numberOfFood = 1;
    private TextView tvNumberOfFood;
    private TextView tvDetailCost;
    private double costTemp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        String foodTable = (String) getIntent().getExtras().get(EXTRA_FOOD_TABLE);

        int foodId = (Integer) getIntent().getExtras().get(EXTRA_FOOD_ID);
        tvNumberOfFood = (TextView) findViewById(R.id.tv_number_of_food);
        tvDetailCost = (TextView) findViewById(R.id.tv_detail_cost);

        // Create cursor
        SQLiteOpenHelper epicBurgerDatabaseHelper = new EpicBurgerDatabaseHelper(this);
        try {
            SQLiteDatabase db = epicBurgerDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query(foodTable,
                    new String[]{"NAME", "COST", "IMAGE_RESOURCE_ID", "FAVORITE"},
                    "_id = ?",
                    new String[]{Integer.toString(foodId)},
                    null, null, null);
            if (cursor.moveToFirst()) {
                String nameText = cursor.getString(0);
                Double cost = cursor.getDouble(1);
                int photoId = cursor.getInt(2);
                boolean isFavorite = (cursor.getInt(3) == 1);

                TextView detailName = (TextView) findViewById(R.id.detail_name);
                detailName.setText(nameText);

                costTemp = cost;
                tvDetailCost.setText("$" + String.format("%.2f", cost));

                ImageView detailImage = (ImageView) findViewById(R.id.detail_image);
                detailImage.setImageDrawable(ContextCompat.getDrawable(this, photoId));
                detailImage.setContentDescription(nameText);

                CheckBox favorite = (CheckBox) findViewById(R.id.favorite);
                favorite.setChecked(isFavorite);

            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Toast toast = Toast.makeText(this, "Database unavailable! onCreate", Toast.LENGTH_SHORT);
            toast.show();
        }

//        String burgerName = Cheeseburger.cheeseburgers[burgerId].getName();
//        TextView detailName = (TextView) findViewById(R.id.detail_name);
//        detailName.setText(burgerName);
//
//        int burgerImage = Cheeseburger.cheeseburgers[burgerId].getImageResourceId();
//        ImageView detailImage = (ImageView) findViewById(R.id.detail_image);
//        detailImage.setImageDrawable(ContextCompat.getDrawable(this, burgerImage));
//        detailImage.setContentDescription(burgerName);
//
//        double burgerCost = Cheeseburger.cheeseburgers[burgerId].getCost();
//        TextView tvCost = (TextView) findViewById(R.id.detail_cost);
//        tvCost.setText("$" + String.valueOf(burgerCost));
//        tvCost.setTypeface(Typeface.DEFAULT_BOLD);
//        tvCost.setTextSize(16);
    }

    public void onClickDone(View view) {
        CharSequence text = "Your order has been updated!";
        int duration = Snackbar.LENGTH_SHORT;
        Snackbar snackbar = Snackbar.make(findViewById(R.id.detail_coordinator), text, duration);
        snackbar.setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(DetailActivity.this, "Undone!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        snackbar.show();
    }

    public void onFavoriteClicked(View view) {
        int foodId = (Integer) getIntent().getExtras().get(EXTRA_FOOD_ID);
        new UpdateFavoriteTask().execute(foodId);

//        CheckBox favorite = (CheckBox) findViewById(R.id.favorite);
//        ContentValues itemValues = new ContentValues();
//        itemValues.put("FAVORITE", favorite.isChecked());
//
//        SQLiteOpenHelper epicBurgerDatabaseHelper = new EpicBurgerDatabaseHelper(this);
//        try {
//            SQLiteDatabase database = epicBurgerDatabaseHelper.getWritableDatabase();
//            database.update("CHEESEBURGERS",
//                    itemValues,
//                    "_id = ?",
//                    new String[]{Integer.toString(burgerId)});
//        database.close();
//        } catch (SQLException e) {
//            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
//            toast.show();
//        }
    }

    private class UpdateFavoriteTask extends AsyncTask<Integer, Void, Boolean> {
        private ContentValues values;

        @Override
        protected void onPreExecute() {
            CheckBox favorite = (CheckBox) findViewById(R.id.favorite);
            values = new ContentValues();
            values.put("FAVORITE", favorite.isChecked());
        }

        @Override
        protected Boolean doInBackground(Integer... itemIds) {
            String foodTable = (String) getIntent().getExtras().get(EXTRA_FOOD_TABLE);
            int foodId = itemIds[0];
            SQLiteOpenHelper helper = new EpicBurgerDatabaseHelper(DetailActivity.this);
            try {
                SQLiteDatabase database = helper.getWritableDatabase();
                database.update(foodTable, values,
                        "_id = ?", new String[]{Integer.toString(foodId)});
                database.close();
                return true;
            } catch (SQLException exception) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast toast = Toast.makeText(DetailActivity.this, "Database unavailable! DetailActivity UpdateFavoriteTask", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    public void minusNumberOfFood(View view) {
        if (Integer.parseInt((String) tvNumberOfFood.getText()) > 1) {
            tvNumberOfFood.setText(String.valueOf(--numberOfFood));
            Double cost = costTemp * Double.parseDouble((String) tvNumberOfFood.getText());
            tvDetailCost.setText("$" + String.format("%.2f", cost));
        }
    }

    public void plusNumberOfFood(View view) {
        tvNumberOfFood.setText(String.valueOf(++numberOfFood));
        Double cost = costTemp * Double.parseDouble((String) tvNumberOfFood.getText());
        tvDetailCost.setText("$" + String.format("%.2f", cost));
    }
}