package com.cookandroid.wifi_based_todolist.page;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.cookandroid.wifi_based_todolist.R;

public class DuePickerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_date_and_time_picker);
    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        //원래의 엑티비티에 결과 전송
        Intent intent = new Intent();
        setResult(RESULT_OK, intent); //결과 : 확인됨

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
