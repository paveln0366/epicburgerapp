package com.epicburger.epicburgerapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_BURGER_ID = "burgerId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int burgerId = (Integer) getIntent().getExtras().get(EXTRA_BURGER_ID);

        // Create cursor
        SQLiteOpenHelper epicBurgerDatabaseHelper = new EpicBurgerDatabaseHelper(this);
        try {
            SQLiteDatabase db = epicBurgerDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("CHEESEBURGERS",
                    new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID"},
                    "_id = ?",
                    new String[] {Integer.toString(burgerId)},
                    null, null, null);
            if (cursor.moveToFirst()) {
                String nameText = cursor.getString(0);
                String description = cursor.getString(1);
                int photoId = cursor.getInt(2);

                TextView detailName = (TextView) findViewById(R.id.detail_name);
                detailName.setText(nameText);

                TextView tvCost = (TextView) findViewById(R.id.detail_cost);
                tvCost.setText("$" + String.valueOf(description));
                tvCost.setTypeface(Typeface.DEFAULT_BOLD);
                tvCost.setTextSize(16);

                ImageView detailImage = (ImageView) findViewById(R.id.detail_image);
                detailImage.setImageDrawable(ContextCompat.getDrawable(this, photoId));
                detailImage.setContentDescription(nameText);

            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Toast toast = Toast.makeText(this, "Database unavailable!", Toast.LENGTH_SHORT);
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
}