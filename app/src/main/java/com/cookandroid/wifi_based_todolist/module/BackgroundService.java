package com.cookandroid.wifi_based_todolist.module;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.cookandroid.wifi_based_todolist.DB.DAO.WifiDB;
import com.cookandroid.wifi_based_todolist.DB.DTO.Wifi;
import com.cookandroid.wifi_based_todolist.R;
import com.cookandroid.wifi_based_todolist.alarmpage.AlarmViewActivity;
import com.cookandroid.wifi_based_todolist.page.AddToDoActivity;
import com.cookandroid.wifi_based_todolist.popup.GroupSelector;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

// 서비스 클래스를 구현하려면, Service 를 상속받는다
public class BackgroundService extends Service {

    WifiDB wifiDB;
    String pre;
    String now;


    @Override
    public IBinder onBind(Intent intent) {
        // Service 객체와 (화면단 Activity 사이에서)
        // 통신(데이터를 주고받을) 때 사용하는 메서드
        // 데이터를 전달할 필요가 없으면 return null;
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 서비스에서 가장 먼저 호출됨(최초에 한번만)
        Log.d("test", "서비스의 onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 서비스가 호출될 때마다 실행
        wifiDB = new WifiDB(this);
        now=NetworkStatus.getConnectivityStatusString(getApplicationContext());
        pre=now;
        new Thread() {
            public void run() {
                while (true) {
                    if(!pre.equals(now)) {
                        if ("Wifi enabled".equals(NetworkStatus.getConnectivityStatusString(getApplicationContext()))) {//와이파이 연결상태일 경우
                            try {
                                String ip = IPAddress.getRealIP();
                                for (int i = 0; i < wifiDB.getWifiList().size(); i++) {
                                    if (ip.equals(wifiDB.getWifiList().get(i).getWifiMac())) {
                                        Intent intent = new Intent(getApplicationContext(), AlarmViewActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    try {
                        pre=now;
                        now=NetworkStatus.getConnectivityStatusString(getApplicationContext());
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 서비스가 종료될 때 실행
        Log.d("test", "서비스의 onDestroy");
    }
}