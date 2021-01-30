package com.epicburger.epicburgerapp.orders;

import android.content.Intent;
import android.os.Bundle;

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

        ListFragment fragment = new OrderListFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.order_list_container, fragment);
        ft.commit();
    }

    @Override
    public void orderClicked(long id) {
        Intent intent = new Intent(this, OrderDetailActivity.class);
        intent.putExtra(OrderDetailActivity.EXTRA_ORDER_ID, (int)id);
        startActivity(intent);
    }
}