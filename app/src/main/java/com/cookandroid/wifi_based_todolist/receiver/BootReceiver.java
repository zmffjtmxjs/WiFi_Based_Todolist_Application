package com.cookandroid.wifi_based_todolist.receiver;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.cookandroid.wifi_based_todolist.DB.DAO.TodoDB;
import com.cookandroid.wifi_based_todolist.DB.DTO.Todo;
import com.cookandroid.wifi_based_todolist.R;
import com.cookandroid.wifi_based_todolist.alarmpage.AlarmViewActivity;
import com.cookandroid.wifi_based_todolist.module.AlarmSetting;
import com.cookandroid.wifi_based_todolist.module.BackgroundService;
import com.cookandroid.wifi_based_todolist.page.MainActivity;

import java.util.ArrayList;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("test","하하 BootReceiver");
        Intent intent2 = new Intent(
                context,
                AlarmSetting.class);
        context.startService(intent2);
    }
}
