package com.cookandroid.wifi_based_todolist.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.cookandroid.wifi_based_todolist.module.AlarmService;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("test","하하 AlarmReceiver");
        Intent mServiceintent = new Intent(context, AlarmService.class);

        Bundle extras = intent.getExtras();
        if(extras != null)
        {
            Bundle bundle = new Bundle();
            bundle.putInt("id", extras.getInt("id"));
            mServiceintent.putExtras(bundle);
        }

        context.startService(mServiceintent);
    }
}
