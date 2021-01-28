package com.epicburger.epicburgerapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class OrderListFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String[] orderNames = new String[Order.orders.length];
        for (int i = 0; i < orderNames.length; i++) {
            orderNames[i] = Order.orders[i].getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                inflater.getContext(),
                android.R.layout.simple_list_item_1,
                orderNames);

        setListAdapter(adapter);

//        return inflater.inflate(R.layout.fragment_order_list,
//                container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}