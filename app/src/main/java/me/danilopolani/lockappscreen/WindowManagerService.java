package me.danilopolani.lockappscreen;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class WindowManagerService extends Service {
    protected WindowManager wm;
    protected FrameLayout mOverlay;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        mOverlay = (FrameLayout) inflater.inflate(R.layout.activity_lock, null);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT);
        wm.addView(mOverlay, params);

        // Show UNLOCK notification
        Intent intent = new Intent(this, UnlockActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        // build notification
        Notification n = new Notification.Builder(this)
                .setContentTitle("Lock App Screen")
                .setContentText(getString(R.string.tap_to_unlock))
                .setSmallIcon(android.R.color.transparent)
                .setOngoing(true)
                .setContentIntent(pIntent)
                .setAutoCancel(false)
                .getNotification();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, n);
    }

    @Override
    public void onDestroy() {
        wm.removeViewImmediate(mOverlay);

        Intent intent = new Intent(this, LockActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        // Show LOCK notification
        Notification n = new Notification.Builder(this)
                .setContentTitle("Lock App Screen")
                .setContentText(getString(R.string.tap_to_lock))
                .setSmallIcon(android.R.color.transparent)
                .setContentIntent(pIntent)
                .setAutoCancel(false)
                .getNotification();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, n);

        super.onDestroy();
    }
}
