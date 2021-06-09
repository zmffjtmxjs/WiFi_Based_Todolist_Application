package com.cookandroid.wifi_based_todolist.page;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cookandroid.wifi_based_todolist.DB.DAO.WifiDB;
import com.cookandroid.wifi_based_todolist.DB.DTO.Wifi;
import com.cookandroid.wifi_based_todolist.R;
import com.cookandroid.wifi_based_todolist.module.IPAddress;
import com.cookandroid.wifi_based_todolist.popup.GroupSelector;

import java.util.ArrayList;

public class SetWiFiActivity extends Activity {

    //DB
    private WifiDB wifidb;
    private Wifi wifi;
    private ArrayList<Wifi> wifis;

    //............toolbar 관련 요소
    ImageView cancelSetWifi, saveSetWifi;
    TextView titleText;

    //............toolbar 외의 요소
    Button selectWifi; // 현재 연결 된 wifi를 선택하는 버튼
    Button manageLocation; // 이전에 설정한 위치를 관리하는 팝업창을 띄우는 버튼
    TextView selectedIP; // 선택 된 IP주소를 보여줍니다.
    TextView locationText; // "위치 이름"을 입력해야 한다고 알려줄 텍스트 뷰
    EditText locationName; // 선택한 wifi에 대응되는 위치 이름을 입력하기 위한 EditText

    String IP; // 가져온 IP 주소입니다.

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_set_wifi);

        wifidb = new WifiDB(this);
        wifis = new ArrayList<>();
        wifi = new Wifi();

        //ImageView 등록
        cancelSetWifi = (ImageView) findViewById(R.id.discard);
        saveSetWifi = (ImageView) findViewById(R.id.save);
        //TextView 등록
        titleText = (TextView) findViewById(R.id.titleText);
        selectWifi = (Button) findViewById(R.id.selectWifi);
        manageLocation = (Button) findViewById(R.id.manageLocation);
        selectedIP = (TextView) findViewById(R.id.selectedIP);
        locationText = (TextView) findViewById(R.id.locationText);
        locationName = (EditText) findViewById(R.id.locationName);

        //.................toolbar 관련 코드

        //화면 제목 표시
        titleText.setText("WiFi 설정");

        //메인화면으로 돌아감
        cancelSetWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //IP와 위치 이름이 입력되어 있다면 -> 저장작업 / 그렇지 않다면 -> 아무 동작도 하지 않음
        saveSetWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //insert DB
                wifi.setWifiMac(IP);
                wifi.setWifiInfo(locationName.getText().toString());
                wifis.add(wifi);



                if(IP != null && locationName != null && !locationName.equals("") ){
                    for(Wifi wifi : wifidb.getWifiList()){
                        if(IP.equals(wifi.getWifiMac())) {
                            Toast.makeText(getApplicationContext(), "이미 저장된 wifi입니다.", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }

                    wifidb.InsertTodo(IP, locationName.getText().toString());

                    // 저장 성공할 경우. 첫 상태로 만듭니다.
                    IP = null;
                    selectedIP.setText("");
                    locationName.setText("");
                    locationName.setVisibility(View.INVISIBLE);
                    locationText.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "저장 완료", Toast.LENGTH_SHORT).show();
                    System.out.println(IP);
                    System.out.println(locationName);
                }
            }
        });

        //..................toolbar 이외의 기능 코드

        locationText.setVisibility(View.INVISIBLE);
        locationName.setVisibility(View.INVISIBLE);// 와이파이가 선택되기 전에는 숨깁니다.

        selectWifi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Thread thread = new Thread(){//네트워크 작업을 위해서 스레드 작업
                    public void run(){
                        try {
                            IP = IPAddress.getRealIP(); // 현재 연결 된 wifi의 IP를 가져 옵니다
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getApplicationContext(), IP + "선택 됨", Toast.LENGTH_SHORT).show(); // IP를 Toast로 띄워줍니다
                selectedIP.setText("현재 선택 : " + IP); // 선택 된 IP를 보여줍니다.

                locationText.setVisibility(View.VISIBLE);
                locationName.setVisibility(View.VISIBLE);// 와이파이가 선택되면 보입니다.
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
