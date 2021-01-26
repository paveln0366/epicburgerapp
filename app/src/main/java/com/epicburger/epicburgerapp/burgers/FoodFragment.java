package com.epicburger.epicburgerapp.burgers;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.epicburger.epicburgerapp.CaptionedImagesAdapter;
import com.epicburger.epicburgerapp.DetailActivity;
import com.epicburger.epicburgerapp.EpicBurgerDatabaseHelper;
import com.epicburger.epicburgerapp.R;

import java.util.ArrayList;

public class FoodFragment extends Fragment {

    private SQLiteDatabase db;
    private Cursor cursor;
    private int numberOfFoods;
    private String foodTable;

    public FoodFragment(String foodTable) {
        this.foodTable = foodTable;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView foodRecycler = (RecyclerView) inflater.inflate(
                R.layout.fragment_food, container, false);

        int[] foodIds = new int[0];
        String[] foodNames = new String[0];
        double[] foodCosts = new double[0];
        int[] foodImages = new int[0];
        String[] foodTableNames = new String[0];

        SQLiteOpenHelper epicBurgerDatabaseHelper = new EpicBurgerDatabaseHelper(getActivity());
        try {
            db = epicBurgerDatabaseHelper.getReadableDatabase();

            cursor = db.rawQuery("SELECT _id FROM " + foodTable, null);
            numberOfFoods = 0;
            if (cursor.moveToFirst()) {
                ArrayList<Integer> arrayListItemIds = new ArrayList<Integer>();
                while (!cursor.isAfterLast()) {
                    numberOfFoods++;
                    arrayListItemIds.add(cursor.getInt(cursor.getColumnIndex("_id")));
                    cursor.moveToNext();
                }
                foodIds = new int[numberOfFoods];
                Integer[] arrayItemIds = arrayListItemIds.toArray(new Integer[arrayListItemIds.size()]);
                int i = 0;
                for (Integer d : arrayItemIds) {
                    foodIds[i] = (int) d;
                    i++;
                }
            }

            cursor = db.rawQuery("SELECT NAME FROM " + foodTable, null);
            if (cursor.moveToFirst()) {
                ArrayList<String> names = new ArrayList<String>();
                while (!cursor.isAfterLast()) {
                    names.add(cursor.getString(cursor.getColumnIndex("NAME")));
                    cursor.moveToNext();
                }
                foodNames = names.toArray(new String[names.size()]);
            }

            cursor = db.rawQuery("SELECT COST FROM " + foodTable, null);
            numberOfFoods = 0;
            if (cursor.moveToFirst()) {
                ArrayList<Double> cost = new ArrayList<Double>();
                while (!cursor.isAfterLast()) {
                    numberOfFoods++;
                    cost.add(cursor.getDouble(cursor.getColumnIndex("COST")));
                    cursor.moveToNext();
                }
                foodCosts = new double[numberOfFoods];
                Double[] cheeseBurgersCostsD = cost.toArray(new Double[cost.size()]);
                int i = 0;
                for (Double d : cheeseBurgersCostsD) {
                    foodCosts[i] = (double) d;
                    i++;
                }
            }

            cursor = db.rawQuery("SELECT IMAGE_RESOURCE_ID FROM " + foodTable, null);
            numberOfFoods = 0;
            if (cursor.moveToFirst()) {
                ArrayList<Integer> imageId = new ArrayList<Integer>();
                while (!cursor.isAfterLast()) {
                    numberOfFoods++;
                    imageId.add(cursor.getInt(cursor.getColumnIndex("IMAGE_RESOURCE_ID")));
                    cursor.moveToNext();
                }
                foodImages = new int[numberOfFoods];
                Integer[] cheeseBurgersImagesD = imageId.toArray(new Integer[imageId.size()]);
                int i = 0;
                for (Integer d : cheeseBurgersImagesD) {
                    foodImages[i] = (int) d;
                    i++;
                }
            }

            cursor = db.rawQuery("SELECT FOOD_TABLE FROM " + foodTable, null);
            if (cursor.moveToFirst()) {
                ArrayList<String> foodTableList = new ArrayList<String>();
                while (!cursor.isAfterLast()) {
                    foodTableList.add(cursor.getString(cursor.getColumnIndex("FOOD_TABLE")));
                    cursor.moveToNext();
                }
                foodTableNames = foodTableList.toArray(new String[foodTableList.size()]);
            }
//                String nameText = cursor.getString(0);
//                String description = cursor.getString(1);
//                int photoId = cursor.getInt(2);
//
//                TextView detailName = (TextView) findViewById(R.id.detail_name);
//                detailName.setText(nameText);
//
//                TextView tvCost = (TextView) findViewById(R.id.detail_cost);
//                tvCost.setText("$" + String.valueOf(description));
//                tvCost.setTypeface(Typeface.DEFAULT_BOLD);
//                tvCost.setTextSize(16);
//
//                ImageView detailImage = (ImageView) findViewById(R.id.detail_image);
//                detailImage.setImageDrawable(ContextCompat.getDrawable(this, photoId));
//                detailImage.setContentDescription(nameText);


            CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(foodIds, foodNames, foodCosts, foodImages, foodTableNames);
            foodRecycler.setAdapter(adapter);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
            foodRecycler.setLayoutManager(gridLayoutManager);

            adapter.setListener(new CaptionedImagesAdapter.Listener() {
                @Override
                public void onClick(int position, String tablesName) {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    //Fix bug: ++position
                    intent.putExtra(DetailActivity.EXTRA_FOOD_ID, position);
                    intent.putExtra(DetailActivity.EXTRA_FOOD_TABLE, foodTable);
                    getActivity().startActivity(intent);
                }
            });

            cursor.close();
            db.close();
        } catch (Exception e) {
            Toast toast = Toast.makeText(getActivity(), "Database unavailable! FoodFragment", Toast.LENGTH_SHORT);
            toast.show();
        }

//        String[] cheeseBurgersNames = new String[Cheeseburger.cheeseburgers.length];
//        for (int i = 0; i < cheeseBurgersNames.length; i++) {
//            cheeseBurgersNames[i] = Cheeseburger.cheeseburgers[i].getName();
//        }

//        double[] cheeseBurgersCosts = new double[Cheeseburger.cheeseburgers.length];
//        for (int i = 0; i < cheeseBurgersCosts.length; i++) {
//            cheeseBurgersCosts[i] = Cheeseburger.cheeseburgers[i].getCost();
//        }

//        int[] cheeseBurgersImages = new int[Cheeseburger.cheeseburgers.length];
//        for (int i = 0; i < cheeseBurgersImages.length; i++) {
//            cheeseBurgersImages[i] = Cheeseburger.cheeseburgers[i].getImageResourceId();
//        }


        return foodRecycler;
    }
}