package com.cookandroid.wifi_based_todolist.page;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cookandroid.wifi_based_todolist.R;

public class AddToDoActivity extends Activity {

    ImageView cancelAddToDo, saveAddToDo;
    TextView titleText, pickDueDate, pickDueTime;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_add_todo);

        //ImageView 등록
        cancelAddToDo = (ImageView) findViewById(R.id.discard);
        //TextView 등록
        titleText = (TextView) findViewById(R.id.titleText);
        pickDueDate = (TextView) findViewById(R.id.pickedDueDate);
        pickDueTime = (TextView) findViewById(R.id.pickedDueTime);

        //화면 제목 표시
        titleText.setText("할 일 편집");

        //만료 날짜 텍스트 뷰 터치 시...
        pickDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //만료 시간 텍스트 뷰 터치 시...
        pickDueTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //메인화면으로 돌아감
        cancelAddToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
