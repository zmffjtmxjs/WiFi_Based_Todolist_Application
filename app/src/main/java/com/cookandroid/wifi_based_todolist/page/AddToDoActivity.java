package com.cookandroid.wifi_based_todolist.page;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cookandroid.wifi_based_todolist.DB.DAO.TodoDB;
import com.cookandroid.wifi_based_todolist.DB.DAO.WifiDB;
import com.cookandroid.wifi_based_todolist.DB.DTO.Todo;
import com.cookandroid.wifi_based_todolist.DB.DTO.Wifi;
import com.cookandroid.wifi_based_todolist.R;
import com.cookandroid.wifi_based_todolist.module.AlarmSetting;
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
    Button deleteToDo;
    RadioGroup alarmGroup;
    RadioButton NO,ONE,WEEK;

    final Calendar cal = Calendar.getInstance();

    //선택한 만료 날짜와 시간을 표시하기 위한 포맷
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd (EEE) aaa hh시mm분",
            new Locale(Locale.KOREAN.getLanguage(), Locale.KOREAN.getCountry()));

    //팝업 엑티비티와 정보교환을 위한 포맷
    SimpleDateFormat transport = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    //초기값은 현재시간을 가져온다.
    String date;
    String newPickDate;

    //할일 추가 또는 할일 디테일 여부 확인
    Integer mode;
    //할일 디테일 상태일 때 toDo의 인덱스 값 받는 변수
    static Integer toDoId;


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

        Intent intent2 = new Intent(this,AlarmSetting.class);

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
        //Button
        deleteToDo = (Button) findViewById(R.id.deleteBtn);
        //RadioGroup
        alarmGroup = (RadioGroup) findViewById(R.id.alarmGroup);
        //RadioButton
        NO = (RadioButton) findViewById(R.id.NO);
        ONE = (RadioButton) findViewById(R.id.ONE);
        WEEK = (RadioButton) findViewById(R.id.WEEK);

        if (toDoId != 0) {      // 할일 리스트에서 클릭하고 들어왔을 때
            //화면 제목 변경
            Todo todo = tododb.getTodo(toDoId);

            titleText.setText("할 일 편집");

            //할일 제목 받아오기
            toDoTitle.setText(todo.getContent());

            //날짜 받아오기
            try {
                cal.setTime(new SimpleDateFormat("yyyy.MM.dd HH:mm").parse((todo.getDate() + ' ' + todo.getTime())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            
            //알람 설정 받아오기
            switch (todo.getAlarm()){
                case 1:
                    ONE.setChecked(true);
                    break;
                case 2:
                    WEEK.setChecked(true);
                    break;
                default:
                    NO.setChecked(true);
            }

            //메모 내용 받아오기
            toDoNote.setText(todo.getMemo());

            //표시위치 받아오기
            if ((todo.getWifiInfo()).equals("")) {
                toDoGroup.setText("삭제된 위치(다시 설정해주세요.)");
            } else {
                toDoGroup.setText(todo.getWifiInfo());
            }


            deleteToDo.setVisibility(View.VISIBLE);
        } else {               // 할일 추가 버튼을 누르고 들어왔을 때
            //화면 제목 표시
            titleText.setText("할 일 추가");
        }

        //화면 진입 시 만료 날짜 및 시간 기본값 설정
        date = dateFormat.format(cal.getTime());
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
                int getAlarm;
                switch (alarmGroup.getCheckedRadioButtonId()){
                    case R.id.ONE:
                        getAlarm = 1;
                        break;
                    case R.id.WEEK:
                        getAlarm = 2;
                        break;
                    default:
                        getAlarm = 0;
                }
                String getToDoNote = String.valueOf(toDoNote.getText());
                String getToDoGroup = String.valueOf(toDoGroup.getText());

                todo = new Todo();
                todo.setContent(getTitle);
                todo.setDate(getDate);
                todo.setTime(getTime);
                todo.setMemo(getToDoNote);
                todo.setAlarm(getAlarm);
                todo.setWifiInfo(getToDoGroup);
                todos.add(todo);

                if(toDoId == 0) {     //할일 추가 버튼을 통해서 들어온 상태일때
                    tododb.InsertTodo(getTitle,getDate,getTime,getAlarm,getToDoNote,getToDoGroup);
                    startService(intent2);
                    finish();
                } else {              //리스트뷰 터치를 통해서 들어온 상태일때
                    tododb.UpdateTodo(getTitle,getToDoGroup,getDate,getTime,getAlarm,getToDoNote, toDoId);
                    startService(intent2);
                    finish();
                }

            }
        });

        //삭제버튼 클릭 시 삭제 확인을 위해 얼럿다이얼로그를 출력
        deleteToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAlert();
            }
        });
    }

    //만료 날짜 또는 시간이 포함된 레이아웃을 터치 시 팝업을 띄운다.
    public void dateTimePickerPopup (View view) {
        Intent intent = new Intent(this, DuePickerActivity.class);
        //선택된 날짜의 값을 보낸다.
        intent.putExtra("Date", transport.format(cal.getTime()));
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
            String getGroupId = data.getStringExtra("locate");

            toDoGroup.setText(getGroupId);
        }
    }

    //삭제 재차 확인을 위한 대화상자
    void deleteAlert() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(this)
                .setTitle("삭제하시겠습니까?")
                .setMessage("이 작업은 되돌릴 수 없습니다.")
                .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //TODO 할일 삭제(DELETE문)
                    tododb.DeleteTodo(toDoId);
                    finish();
                }
            }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });

        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }

    public static Integer setToDoId(int id){
        return toDoId=id;
    }
}
