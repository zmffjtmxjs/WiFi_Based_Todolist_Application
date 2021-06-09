package com.cookandroid.wifi_based_todolist.alarmpage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AlarmViewActivity extends AppCompatActivity {// 알람이 왔을 때 보여주기 위한 화면입니다. 4개의 화면과 완전히 따로 놉니다.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_view);
    }
}