package com.cookandroid.wifi_based_todolist.DB.DAO;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.cookandroid.wifi_based_todolist.R;

public class TodoInfo extends AppCompatActivity {
    EditText Content, memo;
    DatePicker date;
    TimePicker time;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_add_todo);

        Content = findViewById(R.id.ToDoTiTle);
        memo = findViewById(R.id.ToDoNote);
        date = findViewById(R.id.datePicker);
        time = findViewById(R.id.timePicker);

    }

    public void insert(View v){
//        String content
    }


}
