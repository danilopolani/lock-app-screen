package me.danilopolani.lockappscreen;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lockNotify();

        // Notify the user that app is loaded
        Toast.makeText(this, getString(R.string.loaded), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        lockNotify();
    }

    private void lockNotify() {
        Intent intent = new Intent(this, LockActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        // build notification
        Notification n = new Notification.Builder(this)
                .setContentTitle("Lock App Screen")
                .setContentText(getString(R.string.tap_to_lock))
                .setSmallIcon(android.R.color.transparent)
                .setContentIntent(pIntent)
                .setAutoCancel(false)
                .getNotification();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, n);

        moveTaskToBack(true);
    }
}
