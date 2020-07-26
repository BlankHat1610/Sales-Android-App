package dev.mxt.banhang.util;

import android.app.Application;
import android.os.SystemClock;

public class SplashDelay extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SystemClock.sleep(2000);
    }
}
