package com.cookandroid.wifi_based_todolist.page;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cookandroid.wifi_based_todolist.R;
import com.cookandroid.wifi_based_todolist.module.MacAddress;
import com.cookandroid.wifi_based_todolist.popup.GroupSelector;

import org.w3c.dom.Text;

import java.text.ParseException;

public class SetWiFiActivity extends Activity {

    //............toolbar 관련 요소
    ImageView cancelSetWifi, saveSetWifi;
    TextView titleText;

    //............toolbar 외의 요소
    Button selectWifi; // 현재 연결 된 wifi를 선택하는 버튼
    Button manageLocation; // 이전에 설정한 위치를 관리하는 팝업창을 띄우는 버튼
    Button deleteWifi;  //와이파이 삭제 버튼      (추가 zmffjtmxjs)
    TextView selectedMac; // 선택 된 MAC주소를 보여줍니다.
    TextView locationText; // "위치 이름"을 입력해야 한다고 알려줄 텍스트 뷰
    EditText locationName; // 선택한 wifi에 대응되는 위치 이름을 입력하기 위한 EditText

    String macAddress; // 가져온 mac 주소입니다.

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_set_wifi);

        //.................toolbar 관련 코드

        //ImageView 등록
        cancelSetWifi = (ImageView) findViewById(R.id.discard);
        saveSetWifi = (ImageView) findViewById(R.id.save);
        //TextView 등록
        titleText = (TextView) findViewById(R.id.titleText);

        //화면 제목 표시
        titleText.setText("WiFi 설정");

        //메인화면으로 돌아감
        cancelSetWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Mac주소와 위치 이름이 입력되어 있다면 -> 저장작업 / 그렇지 않다면 -> 아무 동작도 하지 않음
        saveSetWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(macAddress != null && locationName != null && !locationName.equals("") ){

                    //macAddress와 locationName을 DB에 저장하는 코드 => junhyeok
                    // [중복 체크 필요할 듯 / 중복 시 break;]


                    selectedMac.setText(""); // 저장 성공할 경우. 첫 상태로 만듭니다.
                    locationName.setText("");
                    locationText.setVisibility(View.INVISIBLE);
                    locationName.setVisibility(View.INVISIBLE);
                    macAddress = null;
                    Toast.makeText(getApplicationContext(), "저장 완료", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //..................toolbar 이외의 기능 코드

        selectWifi = (Button) findViewById(R.id.selectWifi);
        manageLocation = (Button) findViewById(R.id.manageLocation);
        selectedMac = (TextView) findViewById(R.id.selectedMac);
        locationText = (TextView) findViewById(R.id.locationText);
        locationName = (EditText) findViewById(R.id.locationName);
        deleteWifi = (Button) findViewById(R.id.deleteWiFi);


        locationText.setVisibility(View.INVISIBLE);
        locationName.setVisibility(View.INVISIBLE);// 와이파이가 선택되기 전에는 숨깁니다.
        deleteWifi.setVisibility(View.INVISIBLE);

        selectWifi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                macAddress = MacAddress.getMACAddress("wlan0"); // 현재 연결 된 wifi의 Mac주소를 가져 옵니다
                Toast.makeText(getApplicationContext(), macAddress + "선택 됨", Toast.LENGTH_SHORT).show(); // Mac주소를 Toast로 띄워줍니다
                selectedMac.setText("현재 선택 : " + macAddress); // 선택 된 Mac주소를 보여줍니다.

                locationText.setVisibility(View.VISIBLE);
                locationName.setVisibility(View.VISIBLE);// 와이파이가 선택되면 보입니다.
                deleteWifi.setVisibility(View.VISIBLE);
            }
        });

    }

    //기존 위치 관리 버튼을 눌렀을 때
    public void locationPopup (View view) {
        Intent intent = new Intent(this, GroupSelector.class);
        startActivityForResult(intent, 1);
    }

    //와이파이 선택 팝업에서 리스트 아이템을 선택했을 경우
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1) {
            if(resultCode == RESULT_OK) {
                //선택한 리스트 뷰의 id값을 가져옴 (DB에 맞게 변경필요)
                //TODO 와이파이 이름 & IP 보내기
                String getData = data.getStringExtra("id");
            }
        }
    }
}
