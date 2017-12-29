package me.danilopolani.lockappscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class UnlockActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        stopService(new Intent(getBaseContext(), WindowManagerService.class));
        moveTaskToBack(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        moveTaskToBack(true);
    }
}
