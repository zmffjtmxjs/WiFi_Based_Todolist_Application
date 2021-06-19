package com.cookandroid.wifi_based_todolist.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmDataManager.getInstance().setAlarmEnable(context, false);
    }
}
