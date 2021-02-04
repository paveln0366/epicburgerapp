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

    private static final String CHANNEL_ID = "channel_id";
    private static final int NOTIFICATION_ID = 101;

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


        createNotification();
    }

    private void createNotification() {
        String name = "Notification Title";
        String description = "Notification Description";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);


            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(android.R.drawable.sym_def_app_icon)
                    .setContentTitle(name)
                    .setContentText(description)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setVibrate(new long[] {0, 1000})
                    .setAutoCancel(true);

            // Create action
            Intent actionIntent = new Intent(this, MainActivity.class);
            PendingIntent actionPendingIntent = PendingIntent.getActivity(
                    this,
                    0,
                    actionIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setContentIntent(actionPendingIntent);

            notificationManager.notify(NOTIFICATION_ID, builder.build());
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(android.R.drawable.sym_def_app_icon)
//                    .setContentTitle(getString(R.string.question))
//                    .setContentText(text)
                    .setContentTitle(name)
                    .setContentText(description)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setVibrate(new long[] {0, 1000})
                    .setAutoCancel(true);

            // Create action
            Intent actionIntent = new Intent(this, MainActivity.class);
            PendingIntent actionPendingIntent = PendingIntent.getActivity(
                    this,
                    0,
                    actionIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setContentIntent(actionPendingIntent);

            // Issuing a notification
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }
}