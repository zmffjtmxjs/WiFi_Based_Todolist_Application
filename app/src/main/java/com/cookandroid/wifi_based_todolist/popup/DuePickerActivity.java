package com.cookandroid.wifi_based_todolist.popup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.cookandroid.wifi_based_todolist.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static java.util.Calendar.*;

public class DuePickerActivity extends Activity {

    //뷰 등록을 위한 변수선언
    DatePicker datePicker;
    TimePicker timePicker;
    TextView dateView;

    final Calendar pickDate = getInstance();

    //선택한 만료 날짜와 시간을 표시하기 위한 포맷
    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd (EEE) aaa hh시mm분",
            new Locale(Locale.KOREAN.getLanguage(), Locale.KOREAN.getCountry()));

    //팝업 엑티비티와 정보교환을 위한 포맷
    SimpleDateFormat transport = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_date_and_time_picker);

        //DatePicker 등록
        datePicker = findViewById(R.id.datePicker);
        //TimePicker 등록
        timePicker = findViewById(R.id.timePicker);
        //TextView 등록
        dateView = findViewById(R.id.dateView);

        //할 일 추가(편집) 화면에서 보낸 데이터 받기
        Intent intent = getIntent();
        String pickedStrDate = intent.getStringExtra("Date");

        //가져온 시간을 Calender타입으로 변환
        try {
            pickDate.setTime(transport.parse(pickedStrDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //데이트 피커 초기값 지정
        datePicker.init(
                pickDate.get(YEAR),
                pickDate.get(MONTH),
                pickDate.get(DAY_OF_MONTH),
                //데이트 피커 체인지 리스너
                new DatePicker.OnDateChangedListener()
                {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        //데이트 피커와 타임 피커 리스너 코드가 중첩되어 함수로 만듦
                        autoUpdatePickDateView();
                    }
                });

        //타임 피커 초기값 지정
        timePicker.setCurrentHour(pickDate.get(HOUR_OF_DAY));
        timePicker.setCurrentMinute(pickDate.get(MINUTE));

        //시간 초기값 설정이 완료된 후 텍스트 뷰 내용 업데이트
        autoUpdatePickDateView();

        //타임 피커 체인지 리스너
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int min) {

                autoUpdatePickDateView();
            }
        });
    }

    //데이트 피커, 타임 피커 리스너의 함수 - 하단 텍스트뷰와 pickDate 캘린더 업데이트
    protected String autoUpdatePickDateView() {
        pickDate.set(
                datePicker.getYear(),               //년
                datePicker.getMonth(),              //월
                datePicker.getDayOfMonth(),         //일
                timePicker.getCurrentHour(),        //시
                timePicker.getCurrentMinute());     //분

        dateView.setText(dateFormat.format(pickDate.getTime()));

        String newDueDate = transport.format(pickDate.getTime());

        return newDueDate;
    }

    //확인 버튼 클릭
    public void mOnSubmit(View v) {
        //원래의 엑티비티에 결과 전송
        Intent intent = new Intent();
        intent.putExtra("NewDueDate", autoUpdatePickDateView());
        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();
    }

    //취소 버튼 클릭
    public void mOnDiscard(View v) {
        //원래의 엑티비티에 결과 전송
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent); //결과 : 취소됨

        //액티비티(팝업) 닫기
        finish();
    }

    //팝업 외 구역 터치 시 닫히지 않도록 함
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    //안드로이드 백버튼에 대한 반응
    @Override
    public void onBackPressed() {
        //원래의 엑티비티에 결과 전송
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent); //결과 : 취소됨

        //액티비티(팝업) 닫기
        finish();
    }
}
