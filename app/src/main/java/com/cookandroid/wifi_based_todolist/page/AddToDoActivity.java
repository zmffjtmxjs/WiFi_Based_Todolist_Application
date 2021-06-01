package com.cookandroid.wifi_based_todolist.page;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.cookandroid.wifi_based_todolist.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddToDoActivity extends Activity {

    ImageView cancelAddToDo, saveAddToDo;
    TextView titleText, pickDueDate;

    Calendar cal = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  '('EEE')' aaa h'시'mm'분'",
            new Locale(Locale.KOREAN.getLanguage(), Locale.KOREAN.getCountry()));
    String date = dateFormat.format(cal.getTime());
    String newPickDate;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_todo);

        //ImageView 등록
        cancelAddToDo = (ImageView) findViewById(R.id.discard);
        //TextView 등록
        titleText = (TextView) findViewById(R.id.titleText);
        pickDueDate = (TextView) findViewById(R.id.pickedDue);

        pickDueDate.setText(date);

        //메인화면으로 돌아감
        cancelAddToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //만료 날짜 또는 시간이 포함된 레이아웃을 터치 시 팝업을 띄운다.
    public void dateTimePickerPopup (View view) {
        Intent intent = new Intent(this, DuePickerActivity.class);
        intent.putExtra("Date", date);
        startActivityForResult(intent, 1);
    }

    //팝업 창이 종료된 이후 확인을 눌렀을 경우
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1) {
            if(resultCode == RESULT_OK) {
                newPickDate = data.getStringExtra("NewDueDate");
                SimpleDateFormat transport = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                //가져온 시간을 Calender타입으로 변환
                try {
                    cal.setTime(transport.parse(newPickDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                pickDueDate.setText(dateFormat.format(cal.getTime()));
            }
        }
    }
}
