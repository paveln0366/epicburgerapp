package com.epicburger.epicburgerapp.burgers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.epicburger.epicburgerapp.R;

public class ChickenBurgersFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Fill list view
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                inflater.getContext(),
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.chicken_burgers)
        );

        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}