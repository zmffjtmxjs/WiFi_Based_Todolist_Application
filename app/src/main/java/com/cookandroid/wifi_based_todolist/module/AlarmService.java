package com.cookandroid.wifi_based_todolist.module;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.cookandroid.wifi_based_todolist.DB.DAO.TodoDB;
import com.cookandroid.wifi_based_todolist.DB.DAO.WifiDB;
import com.cookandroid.wifi_based_todolist.DB.DTO.Todo;
import com.cookandroid.wifi_based_todolist.alarmpage.AlarmViewActivity;
import com.cookandroid.wifi_based_todolist.page.AddToDoActivity;

import java.util.ArrayList;
import java.util.Calendar;

// 서비스 클래스를 구현하려면, Service 를 상속받는다
public class AlarmService extends Service {
    WifiDB wifiDB;
    TodoDB todoDB;
    AlarmManager mAlarmManager;


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
        Log.d("test","하하 AlarmService");
        // 서비스가 호출될 때마다 실행
        Bundle extras = intent.getExtras();
        if(extras != null)
        {
            String id = String.valueOf(extras.getInt("id")+1);
            Intent intent2 = new Intent(this, AlarmViewActivity.class);
            intent2.putExtra("id",id);
            this.startActivity(intent2);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 서비스가 종료될 때 실행
        Log.d("test", "서비스의 onDestroy");
    }
}