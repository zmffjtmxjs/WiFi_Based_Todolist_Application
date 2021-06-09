package com.cookandroid.wifi_based_todolist.module;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String status = NetworkStatus.getConnectivityStatusString(context);
        Toast.makeText(context, status, Toast.LENGTH_LONG).show();
    }
}