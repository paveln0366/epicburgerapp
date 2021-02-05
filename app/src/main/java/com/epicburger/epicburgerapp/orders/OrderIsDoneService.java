package com.epicburger.epicburgerapp.orders;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.epicburger.epicburgerapp.MainActivity;
import com.epicburger.epicburgerapp.R;

public class OrderIsDoneService extends IntentService {

    public static final String EXTRA_MESSAGE = "message";

    private static final String CHANNEL_ID = "channel_id";
    private static final int NOTIFICATION_ID = 101;

    public OrderIsDoneService() {
        super("OrderIsDoneService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        synchronized (this) {
            try {
                wait(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String text = intent.getStringExtra(EXTRA_MESSAGE);
        createNotification(text);
    }

    private void createNotification(final String text) {
        String name = text;
        String description = "№0001";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_order)
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

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_order)
//                    .setContentTitle(getString(R.string.question))
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