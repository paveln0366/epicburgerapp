package com.epicburger.epicburgerapp.orders;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.epicburger.epicburgerapp.MainActivity;
import com.epicburger.epicburgerapp.R;

public class OrderDetailActivity extends AppCompatActivity {

    public static final String EXTRA_ORDER_ID = "orderId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        OrderDetailFragment fragment = (OrderDetailFragment)
                getSupportFragmentManager().findFragmentById(R.id.order_detail_fragment);
        int orderId = (int) getIntent().getExtras().get(EXTRA_ORDER_ID);
        fragment.setOrderId(orderId);
    }
}