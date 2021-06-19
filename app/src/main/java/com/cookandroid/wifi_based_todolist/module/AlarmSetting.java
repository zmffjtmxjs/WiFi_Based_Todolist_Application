package com.cookandroid.wifi_based_todolist.module;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.cookandroid.wifi_based_todolist.DB.DAO.TodoDB;
import com.cookandroid.wifi_based_todolist.DB.DAO.WifiDB;
import com.cookandroid.wifi_based_todolist.DB.DTO.Todo;
import com.cookandroid.wifi_based_todolist.DB.DTO.Wifi;
import com.cookandroid.wifi_based_todolist.R;
import com.cookandroid.wifi_based_todolist.alarmpage.AlarmViewActivity;

import java.util.ArrayList;
import java.util.Calendar;

// 서비스 클래스를 구현하려면, Service 를 상속받는다
public class AlarmSetting extends Service {

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
        Log.d("test","하하 AlarmSetting");
        // 서비스가 호출될 때마다 실행
        wifiDB = new WifiDB(this);
        todoDB = new TodoDB(this);
        Context c = this;
        ArrayList<Todo> todos = todoDB.getTodoList("all");
        ArrayList<Todo> alarmTodos = new ArrayList<Todo>();

        for(int i = 0 ; i < todos.size() ; i++){
            if(todos.get(i).getAlarm()==1 || todos.get(i).getAlarm()==2){
                alarmTodos.add(todos.get(i));
            }
        }
        for(int i = 0 ; i < alarmTodos.size() ; i++){
            String[] date = alarmTodos.get(i).getDate().split("\\.");
            String[] time = alarmTodos.get(i).getTime().split("\\:");
            Calendar mCalendar = Calendar.getInstance();
            mCalendar.set(Calendar.YEAR,Integer.parseInt(date[0]));
            mCalendar.set(Calendar.MONTH,Integer.parseInt(date[1])-1);
            mCalendar.set(Calendar.DATE,Integer.parseInt(date[2]));
            mCalendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(time[0]));
            mCalendar.set(Calendar.MINUTE,Integer.parseInt(time[1]));

            Intent mAlarmIntent = new Intent("com.test.alarmtestous.ALARM_START");
            Bundle bundle = new Bundle();
            bundle.putInt("id",alarmTodos.get(i).getId());
            intent.putExtras(bundle);

            PendingIntent mPendingIntent =
                    PendingIntent.getBroadcast(
                            c,
                            i,
                            mAlarmIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );

            AlarmManager mAlarmManager = (AlarmManager) getSystemService(c.ALARM_SERVICE);
            mAlarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    mCalendar.getTimeInMillis(),
                    mPendingIntent
            );
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