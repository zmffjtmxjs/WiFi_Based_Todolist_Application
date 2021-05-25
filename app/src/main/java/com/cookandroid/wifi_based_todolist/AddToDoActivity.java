package com.cookandroid.wifi_based_todolist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AddToDoActivity extends Activity {

    ImageView cancelAddToDo, saveAddToDo;
    TextView titleText;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_add_todo);

        //ImageView 등록
        cancelAddToDo = (ImageView) findViewById(R.id.cancelAddToDo);
        //TextView 등록
        titleText = (TextView) findViewById(R.id.titleText);

        //화면 제목 표시
        titleText.setText("할 일 편집");

        //메인화면으로 돌아감
        cancelAddToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
