package com.cookandroid.wifi_based_todolist.alarmpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.cookandroid.wifi_based_todolist.DB.DAO.TodoDB;
import com.cookandroid.wifi_based_todolist.DB.DAO.WifiDB;
import com.cookandroid.wifi_based_todolist.DB.DTO.Todo;
import com.cookandroid.wifi_based_todolist.DB.DTO.Wifi;
import com.cookandroid.wifi_based_todolist.R;
import com.cookandroid.wifi_based_todolist.page.AddToDoActivity;
import com.cookandroid.wifi_based_todolist.module.CustomAdapter;
import com.cookandroid.wifi_based_todolist.page.SetWiFiActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class AlarmViewActivity extends AppCompatActivity {

    // 사이드 바 관련
    ImageView sideBarButton;
    DrawerLayout sideBarDrawerV;
    
    //할 일 목록 가져오기 관련
    private TodoDB todoDB;
    private ArrayList<Todo> todos;
    private WifiDB wifiDB;
    private ArrayList<Wifi> wifis;
    private ListView lv_todo;
    CustomAdapter customAdapter;

    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        String wifiInfo = intent.getStringExtra("wifiInfo");// 작동원인이 된 wifiInfo 받음.

        //ListView에 할 일목록 가져오기
        todoDB = new TodoDB(this);
        todos = todoDB.getTodoList("wifiInfo",wifiInfo);// 해당 wifiInfo로 검색
        customAdapter = new CustomAdapter(todos,this);

        //ListView 등록
        lv_todo = findViewById(R.id.lv_todoV);
        lv_todo.setAdapter(customAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_view);

        //ImageView 등록
        sideBarButton = (ImageView) findViewById(R.id.sideBarButton);
        //DrawerLayout 등록
        sideBarDrawerV = (DrawerLayout) findViewById(R.id.drawerLayoutV);

        //사이드 메뉴 버튼 터치 시 사이드 바 활성화 기능
        sideBarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sideBarDrawerV.openDrawer(Gravity.LEFT);
            }
        });




    }

}