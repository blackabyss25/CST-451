package com.example.lighthouse.model;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.security.Provider;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;

public class NotificationScheduler extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
}
