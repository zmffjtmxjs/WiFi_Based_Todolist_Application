package com.cookandroid.wifi_based_todolist.page;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cookandroid.wifi_based_todolist.R;
import com.cookandroid.wifi_based_todolist.module.MacAddress;

public class SetWiFiActivity extends Activity {

    //............toolbar 관련 요소
    ImageView cancelAddToDo, saveAddToDo;
    TextView titleText;

    //............toolbar 외의 요소
    Button selectWifi; // 현재 연결 된 wifi를 선택하는 버튼
    Button manageLocation; // 이전에 설정한 위치를 관리하는 팝업창을 띄우는 버튼
    TextView selectedMac; // 선택 된 MAC주소를 보여줍니다.
    EditText locationName; // 선택한 wifi에 대응되는 위치 이름을 입력하기 위한 EditText

    String macAddress; // 가져온 mac 주소입니다.

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_set_wifi);

        //.................toolbar 관련 코드

        //ImageView 등록
        cancelAddToDo = (ImageView) findViewById(R.id.discard);
        //TextView 등록
        titleText = (TextView) findViewById(R.id.titleText);

        //화면 제목 표시
        titleText.setText("WiFi 설정");

        //메인화면으로 돌아감
        cancelAddToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //..................toolbar 이외의 기능 코드

        selectWifi = (Button) findViewById(R.id.selectWifi);
        manageLocation = (Button) findViewById(R.id.manageLocation);
        selectedMac = (TextView) findViewById(R.id.selectedMac);
        locationName = (EditText) findViewById(R.id.locationName);

        selectWifi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                macAddress = MacAddress.getMACAddress("wlan0"); // 현재 연결 된 wifi의 Mac주소를 가져 옵니다
                Toast.makeText(getApplicationContext(), macAddress + "선택 됨", Toast.LENGTH_SHORT).show(); // Mac주소를 Toast로 띄워줍니다
                selectedMac.setText("현재 선택 : " + macAddress); // 선택 된 Mac주소를 보여줍니다.
            }
        });
        
        

    }
}
