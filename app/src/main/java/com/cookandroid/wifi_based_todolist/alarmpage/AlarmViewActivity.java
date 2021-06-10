package com.cookandroid.wifi_based_todolist.alarmpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

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
import com.cookandroid.wifi_based_todolist.page.CustomAdapter;
import com.cookandroid.wifi_based_todolist.page.SetWiFiActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class AlarmViewActivity extends AppCompatActivity {
    //recyclerView
    private ListView rv_todo;
    CustomAdapter customAdapter;

    private FloatingActionButton addToDoButton;
    private ArrayList<Todo> todos;
    private TodoDB todoDB;
    private CustomAdapter adapter;

    ImageView sideBarButton;
    DrawerLayout sideBarDrawer;
    Button SetWiFiButton;

    //DB부분 추가
    private WifiDB wifiDB;
    private ArrayList<Wifi> wifis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        todoDB = new TodoDB(this);
//        todos = new ArrayList<>();

        //백그라운드 서비스 계속 작동시키기

//        todoDB = new TodoDB(this);
//        todos = new ArrayList<>();

        //DB부분 추가
        todoDB = new TodoDB(this);

        //todos = todoDB.getTodoList();
        todos = todoDB.getTodoList();

        wifiDB = new WifiDB(this);
        wifis = wifiDB.getWifiList();

        customAdapter = new CustomAdapter(todos, this);
//        loadReceontDB();

        //RecyclerView 등록 --6맨--
        rv_todo = findViewById(R.id.rv_todo);
        rv_todo.setAdapter(customAdapter);


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

        //리스트 내의 할일을 터치 했을 시
        rv_todo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int toDoId = Integer.parseInt(String.valueOf(parent.getItemAtPosition(position)));

                Intent intent = new Intent(getApplicationContext(), AddToDoActivity.class);
                intent.putExtra("Mode", 1);
                //할일의 인덱스 번호를 보냄
                intent.putExtra("toDoId", toDoId);

                startActivity(intent);


            }
        });

        //할일 추가 화면 이동
        addToDoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddToDoActivity.class);
                startActivity(intent);
                //addTodo에 들어가서 생성할 제목 내용 시간 등을 findView 설정하고 save눌렸을 때(save.setOnclick) insert문에 제목 내용 등 입력하여 저장 -> db에 저장
                //UI 쪽에도 표현해야함 -> 어댑터 사용
                //
//                Todo todo = new Todo();
//                todo.setContent(ToDoTiTle.getText().toString());
//                todo.setMemo(ToDoNote.getText().toString());

//                adapter.addItem(item);
//                rv_todo.smoothScrollToPosition(0);

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

}