package com.epicburger.epicburgerapp.orders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.epicburger.epicburgerapp.R;

public class OrderDetailFragment extends Fragment {

    private long orderId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            OrderManagementFragment orderManagement = new OrderManagementFragment();
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.add(R.id.order_management_container, orderManagement);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        } else {
            orderId = savedInstanceState.getLong("orderId");
        }
    }

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

    @Override
    public void onSaveInstanceState(@NonNull Bundle saveInstanceState) {
        saveInstanceState.putLong("orderId", orderId);
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
}