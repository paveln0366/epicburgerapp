package com.epicburger.epicburgerapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class OrderDetailFragment extends Fragment {

    private long orderId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        TextView tvOrderName = (TextView) view.findViewById(R.id.tv_order_name);
        TextView tvOrderCost = (TextView) view.findViewById(R.id.tv_order_cost);
        Order order = Order.orders[(int) orderId];
        tvOrderName.setText(order.getName());
        tvOrderCost.setText(String.valueOf(order.getCost()));

    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
}