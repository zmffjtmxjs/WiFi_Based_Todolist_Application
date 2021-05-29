package com.cookandroid.wifi_based_todolist.page;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.cookandroid.wifi_based_todolist.R;

import java.util.Calendar;

public class AddToDoActivity extends Activity {

    ImageView cancelAddToDo, saveAddToDo;
    TextView titleText, pickDueDate, pickDueTime;
    //날짜 연산을 위한 변수 선언
    private int mYear = 0, mMonth = 0, mDay = 0;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_todo);

        //ImageView 등록
        cancelAddToDo = (ImageView) findViewById(R.id.discard);
        //TextView 등록
        titleText = (TextView) findViewById(R.id.titleText);
        pickDueDate = (TextView) findViewById(R.id.pickedDueDate);
        pickDueTime = (TextView) findViewById(R.id.pickedDueTime);

        //화면 제목 표시
        titleText.setText("할 일 편집");

        //달력 인스턴스 생성
        Calendar getToday = Calendar.getInstance();
        mYear = getToday.get(Calendar.YEAR);
        mMonth = getToday.get(Calendar.MONTH);
        mDay = getToday.get(Calendar.DAY_OF_MONTH);

        pickDueDate.setText(mYear + "-" + mMonth + "-" + mDay);

        //메인화면으로 돌아감
        cancelAddToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //만료 날짜 또는 시간이 포함된 레이아웃을 터치 시 팝업을 띄운다.
    public void mOnPopupClick (View view) {
        Intent intent = new Intent(this, DuePickerActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1) {
            if(requestCode == RESULT_OK) {

            }
        }
    }
}
