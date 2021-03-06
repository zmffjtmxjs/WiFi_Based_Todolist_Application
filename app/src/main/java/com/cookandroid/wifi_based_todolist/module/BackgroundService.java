package com.cookandroid.wifi_based_todolist.module;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.cookandroid.wifi_based_todolist.DB.DAO.TodoDB;
import com.cookandroid.wifi_based_todolist.DB.DAO.WifiDB;
import com.cookandroid.wifi_based_todolist.DB.DTO.Todo;
import com.cookandroid.wifi_based_todolist.DB.DTO.Wifi;
import com.cookandroid.wifi_based_todolist.alarmpage.AlarmViewActivity;
import com.cookandroid.wifi_based_todolist.page.MainActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;


// 서비스 클래스를 구현하려면, Service 를 상속받는다
public class BackgroundService extends Service {

    WifiDB wifiDB;
    TodoDB todoDB;
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
        Log.d("test","하하 BackgroundService");
        // 서비스가 호출될 때마다 실행
        wifiDB = new WifiDB(this);
        todoDB = new TodoDB(this);
        now=NetworkStatus.getConnectivityStatusString(getApplicationContext());
        pre=now;
        new Thread() {
            public void run() {
                while (true) {
                    Log.d("test","하하 BackgroundService2");
                    if(!pre.equals(now)) {
                        if ("Wifi enabled".equals(NetworkStatus.getConnectivityStatusString(getApplicationContext()))) {//와이파이 연결상태일 경우
                            try {
                                String ip = IPAddress.getRealIP();
                                ArrayList<Wifi> wifis = new ArrayList<Wifi>();
                                ArrayList<Todo> todos = todoDB.getTodoList("all");
                                for (int i = 0;i<todos.size();i++){                             // 할 일 목록에 설정된 위치만 비교합니다.
                                    Wifi wifi = wifiDB.getWifi(todos.get(i).getWifiInfo()); // 할 일 중에는 등록된 위치가 ""인 것도 있기 때문에 null 체크 해줘야 합니다.
                                    if(wifi!=null) {
                                        wifis.add(wifi);
                                    }
                                }

                                for (int i = 0; i < wifis.size(); i++) {
                                    if (ip.equals(wifis.get(i).getWifiMac())) {
                                        Intent intent = new Intent(getApplicationContext(), AlarmViewActivity.class);
                                        intent.putExtra("wifiInfo",wifis.get(i).getWifiInfo());
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
                        Thread.sleep(3000);
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