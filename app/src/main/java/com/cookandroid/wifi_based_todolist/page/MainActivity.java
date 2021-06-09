package com.cookandroid.wifi_based_todolist.page;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.cookandroid.wifi_based_todolist.DB.DAO.TodoDB;
import com.cookandroid.wifi_based_todolist.DB.DAO.WifiDB;
import com.cookandroid.wifi_based_todolist.DB.DTO.Todo;
import com.cookandroid.wifi_based_todolist.DB.DTO.Wifi;
import com.cookandroid.wifi_based_todolist.R;
import com.cookandroid.wifi_based_todolist.module.BackgroundService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    /* TODO
    *      DB 연동하여 할 일 목록(리스트)을 출력 */
    /* TODO
    *      출력된 리스트의 각 항목 왼쪽에 완료 체크 버튼을 생성 및 체크 시 평시에는 보이지 않음 설정  */
    /* TODO (우선순위 : 하)
           사용자가 원하는 조건(분류)를 선택하여 해당하는 할 일만 출력 */

    ImageView sideBarButton;
    DrawerLayout sideBarDrawer;
    FloatingActionButton addToDoButton;
    Button SetWiFiButton;

    //DB부분 추가
    private TodoDB todoDB;
    private ArrayList<Todo> todos;
    private WifiDB wifiDB;
    private ArrayList<Wifi> wifis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //백그라운드 서비스 계속 작동시키기
        Intent intent = new Intent(
                getApplicationContext(),
                BackgroundService.class);
        startService(intent);// 백그라운드 서비스 "BackgroundService"를 시작합니다. 일단 어플이 시작되면 멈추지 않습니다.onStartCommand()가 실행됩니다.

        //DB부분 추가
        todoDB = new TodoDB(this);
        todos = todoDB.getTodoList();
        wifiDB = new WifiDB(this);
        wifis = wifiDB.getWifiList();

        //ImageView 등록
        sideBarButton = (ImageView) findViewById(R.id.sideBarButton);
        //DrawerLayout 등록
        sideBarDrawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        //FloatingActionButton 등록
        addToDoButton = (FloatingActionButton) findViewById(R.id.addToDoButton);
        //Button 등록
        SetWiFiButton = (Button) findViewById(R.id.goSetWiFi);

        //사이드 메뉴 버튼 터치 시 사이드 바 활성화 기능
        sideBarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sideBarDrawer.openDrawer(Gravity.LEFT);
            }
        });

        //할일 추가 화면 이동
        addToDoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddToDoActivity.class);
                startActivity(intent);
            }
        });

        //WiFi 설정 화면 이동
        SetWiFiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SetWiFiActivity.class);
                startActivity(intent);
            }
        });

    }
//    private void loadRecentDB() {
//        //저장된 DB가져옴
//        todos = todoDB.getTodoList();
//        if (mAdapter == null) {
//            mAdapter = new CustomAdapter(mtodoItems, this);
//            mrv_todo.setHasFixedSize(true);
//            mrv_todo.setAdapter(mAdapter);
//
//        }
//    }

}