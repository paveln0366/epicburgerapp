package com.epicburger.epicburgerapp.burgers;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.epicburger.epicburgerapp.CaptionedImagesAdapter;
import com.epicburger.epicburgerapp.Cheeseburger;
import com.epicburger.epicburgerapp.DetailActivity;
import com.epicburger.epicburgerapp.EpicBurgerDatabaseHelper;
import com.epicburger.epicburgerapp.R;

import java.util.ArrayList;
import java.util.stream.Stream;

public class CheeseBurgersFragment extends Fragment {

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView cheeseRecycler = (RecyclerView) inflater.inflate(
                R.layout.fragment_cheese_burgers, container, false);

        String[] cheeseBurgersNames = new String[4];
        double[] cheeseBurgersCosts = new double[4];
        int[] cheeseBurgersImages = new int[4];

        SQLiteOpenHelper epicBurgerDatabaseHelper = new EpicBurgerDatabaseHelper(getActivity());
        try {
            db = epicBurgerDatabaseHelper.getReadableDatabase();
            cursor = db.rawQuery("SELECT NAME FROM CHEESEBURGERS", null);
            if (cursor.moveToFirst()) {
                ArrayList<String> names = new ArrayList<String>();
                while (!cursor.isAfterLast()) {
                    names.add(cursor.getString(cursor.getColumnIndex("NAME")));
                    cursor.moveToNext();
                }
                String[] cheeseBurgersNamesD = names.toArray(new String[names.size()]);
            }

            cursor = db.rawQuery("SELECT COST FROM CHEESEBURGERS", null);
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

            cursor = db.rawQuery("SELECT IMAGE_RESOURCE_ID FROM CHEESEBURGERS", null);
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


            CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(cheeseBurgersNames, cheeseBurgersCosts, cheeseBurgersImages);
            cheeseRecycler.setAdapter(adapter);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
            cheeseRecycler.setLayoutManager(gridLayoutManager);

            adapter.setListener(new CaptionedImagesAdapter.Listener() {
                @Override
                public void onClick(int position) {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_BURGER_ID, position);
                    getActivity().startActivity(intent);
                }
            });

            cursor.close();
            db.close();
        } catch (Exception e) {
            Toast toast = Toast.makeText(getActivity(), "Database unavailable!", Toast.LENGTH_SHORT);
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


        return cheeseRecycler;
    }
}