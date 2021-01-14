package com.epicburger.epicburgerapp.burgers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;

import com.epicburger.epicburgerapp.CaptionedImagesAdapter;
import com.epicburger.epicburgerapp.Cheeseburger;
import com.epicburger.epicburgerapp.R;

public class CheeseBurgersFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView cheeseRecycler = (RecyclerView) inflater.inflate(
                R.layout.fragment_cheese_burgers, container, false);

        String[] cheeseBurgersNames = new String[Cheeseburger.cheeseburgers.length];
        for (int i = 0; i < cheeseBurgersNames.length; i++) {
            cheeseBurgersNames[i] = Cheeseburger.cheeseburgers[i].getName();
        }

        double[] cheeseBurgersCosts = new double[Cheeseburger.cheeseburgers.length];
        for (int i = 0; i < cheeseBurgersCosts.length; i++) {
            cheeseBurgersCosts[i] = Cheeseburger.cheeseburgers[i].getCost();
        }

        int[] cheeseBurgersImages = new int[Cheeseburger.cheeseburgers.length];
        for (int i = 0; i < cheeseBurgersImages.length; i++) {
            cheeseBurgersImages[i] = Cheeseburger.cheeseburgers[i].getImageResourceId();
        }

        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(cheeseBurgersNames, cheeseBurgersCosts, cheeseBurgersImages);
        cheeseRecycler.setAdapter(adapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        cheeseRecycler.setLayoutManager(gridLayoutManager);

        return cheeseRecycler;
    }
}