package com.epicburger.epicburgerapp.orders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import com.epicburger.epicburgerapp.R;

public class OrdersActivity extends AppCompatActivity implements OrderListFragment.Listener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void orderClicked(long id) {
        View orderDetailContainer = findViewById(R.id.order_detail_container);
        if (orderDetailContainer != null) {
            OrderDetailFragment orderDetailFragment = new OrderDetailFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            orderDetailFragment.setOrderId(id);
            ft.replace(R.id.order_detail_container, orderDetailFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();
        } else {
            Intent intent = new Intent(this, OrderDetailActivity.class);
            intent.putExtra(OrderDetailActivity.EXTRA_ORDER_ID, (int)id);
            startActivity(intent);
        }
    }
}