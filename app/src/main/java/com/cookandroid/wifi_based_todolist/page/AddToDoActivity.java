package com.cookandroid.wifi_based_todolist.page;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cookandroid.wifi_based_todolist.DB.DAO.TodoDB;
import com.cookandroid.wifi_based_todolist.DB.DAO.WifiDB;
import com.cookandroid.wifi_based_todolist.DB.DTO.Todo;
import com.cookandroid.wifi_based_todolist.DB.DTO.Wifi;
import com.cookandroid.wifi_based_todolist.R;
import com.cookandroid.wifi_based_todolist.popup.DuePickerActivity;
import com.cookandroid.wifi_based_todolist.popup.GroupSelector;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddToDoActivity extends Activity {

    //뷰 등록을 위한 선언
    ImageView cancelAddToDo, saveAddToDo;
    TextView titleText, pickDueDate, toDoGroup;
    EditText toDoTitle, toDoNote;

    final Calendar cal = Calendar.getInstance();

    //선택한 만료 날짜와 시간을 표시하기 위한 포맷
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd (EEE) aaa hh시mm분",
            new Locale(Locale.KOREAN.getLanguage(), Locale.KOREAN.getCountry()));

    //팝업 엑티비티와 정보교환을 위한 포맷
    SimpleDateFormat transport = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    //초기값은 현재시간을 가져온다.
    String date = dateFormat.format(cal.getTime());
    String newPickDate;

    //Data
    Todo todo;
    TodoDB tododb;
    ArrayList<Todo> todos;
    Wifi wifi;
    WifiDB wifidb;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_add_todo);

        //DB
        //아직 todo 저장이 없음
        todo = new Todo();
        tododb = new TodoDB(this);
        todos = new ArrayList<>();

        wifi = new Wifi();
        wifidb = new WifiDB(this);

        //ImageView 등록
        cancelAddToDo = (ImageView) findViewById(R.id.discard);
        saveAddToDo = (ImageView) findViewById(R.id.save);
        //TextView 등록
        titleText = (TextView) findViewById(R.id.titleText);
        pickDueDate = (TextView) findViewById(R.id.pickedDue);
        toDoGroup = (TextView) findViewById(R.id.pickedGroup);
        //EditText
        toDoTitle = (EditText) findViewById(R.id.ToDoTitle);
        toDoNote = (EditText) findViewById(R.id.ToDoNote);


        //화면 제목 표시
        titleText.setText("할 일 편집");

        //화면 진입 시 만료 날짜 및 시간 기본값 설정
        pickDueDate.setText(date);

        //메인화면으로 돌아감
        cancelAddToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //세이브 버튼 터치 시
        saveAddToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getTitle = String.valueOf(toDoTitle.getText());
                String getDate = new SimpleDateFormat("yyyy.MM.dd").format(cal.getTime());
                String getTime = new SimpleDateFormat("HH:mm").format(cal.getTime());
                String getToDoNote = String.valueOf(toDoNote.getText());
                String getToDoGroup = String.valueOf(toDoGroup.getText());

                todo = new Todo();
                todo.setContent(getTitle);
                todo.setDate(getDate);
                todo.setTime(getTime);
                todo.setMemo(getToDoNote);
                todo.setWifiInfo(getToDoGroup);
                todos.add(todo);


                tododb.InsertTodo(getTitle,getDate,getTime,getToDoNote,getToDoGroup);



                Log.v("제목", getTitle);
                Log.v("날짜", getDate);
                Log.v("시간", getTime);
                Log.v("메모", getToDoNote);
                Log.v("그룹", getToDoGroup);
            }
        });
    }

    //만료 날짜 또는 시간이 포함된 레이아웃을 터치 시 팝업을 띄운다.
    public void dateTimePickerPopup (View view) {
        Intent intent = new Intent(this, DuePickerActivity.class);
        //선택된 날짜의 값을 보낸다.
        intent.putExtra("Date", date);
        startActivityForResult(intent, 1);
    }

    //표시 위치 선택 텍스트를 터치 시 팝업을 띄운다.
    public void locationPopup (View view) {
        Intent intent = new Intent(this, GroupSelector.class);
        startActivityForResult(intent, 2);
    }

    //팝업 창이 종료된 이후
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //날짜 및 시간 선택기에서 확인을 눌렀을 경우
        if((requestCode == 1) && (resultCode == RESULT_OK)) {
            newPickDate = data.getStringExtra("NewDueDate");

            //가져온 시간을 Calender타입으로 변환
            try {
                cal.setTime(transport.parse(newPickDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            pickDueDate.setText(dateFormat.format(cal.getTime()));
        }
        //와이파이 선택 팝업에서 리스트 아이템을 선택했을 경우
        else if((requestCode == 2) && (resultCode == RESULT_OK)) {
            //선택한 리스트 뷰의 id값을 가져옴 (DB에 맞게 변경필요)
            //TODO 와이파이 이름 & IP 받기
            String getGroupId = data.getStringExtra("Id");
            String getGroupName = data.getStringExtra("Name");

            toDoGroup.setText(getGroupName + getGroupId);
        }
    }
}
