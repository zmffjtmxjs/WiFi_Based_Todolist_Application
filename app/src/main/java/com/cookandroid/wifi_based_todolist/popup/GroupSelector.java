package com.cookandroid.wifi_based_todolist.popup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.cookandroid.wifi_based_todolist.DB.DAO.TodoDB;
import com.cookandroid.wifi_based_todolist.DB.DAO.WifiDB;
import com.cookandroid.wifi_based_todolist.DB.DTO.Todo;
import com.cookandroid.wifi_based_todolist.DB.DTO.Wifi;
import com.cookandroid.wifi_based_todolist.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GroupSelector extends Activity {
    //리스트 뷰에 넣을 리스트(배열 리스트는 최대 공간이 정해져 있지않음)
    List preWifi = new ArrayList();

    ListView wifiList;
    ArrayAdapter adapter;

    Todo todo;
    TodoDB tododb;
    ArrayList<Wifi> wifis;
    Wifi wifi;
    WifiDB wifidb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_pres);

        //팝업 창의 크기를 전체화면을 기준으로 조절
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        //화면의 넓이와 높이의 50% 크기
        int width = (int) (display.getWidth() * 0.6);
        int height = (int) (display.getHeight() * 0.7);

        //팝업창에 적용
        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;


        //리스트 뷰 등록
        wifiList = (ListView) findViewById(R.id.wifi);
        //리스트 뷰에 배열 리스트를 반영하기 위한 배열 어댑터
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, preWifi);

        //리스트 뷰에 반영할 리스트에 더미 데이터를 넣음
        /* TODO 데이터 베이스에서 위치목록을 가져와서 배열에 추가
        *   Wifi 이름(=위치 이름), Wifi IP 배열이 필요할 것으로 예상 */


//        for(Wifi wifi : wifidb.getWifiList()){
//            if(IP.equals(wifi.getWifiMac())) {
//                Toast.makeText(getApplicationContext(), "이미 저장된 wifi입니다.", Toast.LENGTH_SHORT).show();
//                break;
//            }
//        }

        wifidb = new WifiDB(this);
        for(int i=0; i<wifidb.getWifiList().size();i++){
            preWifi.add(wifidb.getWifiList().get(i).getWifiInfo());
        }
//        for (int i = 0; i < 50; i++) {
//            //리스트에 원소 추가 (공간의 한계는 없음)
//            preWifi.add(tododb.getTodoList());
//        }

        //선언한 어뎁터를 리스트뷰에 적용
        wifiList.setAdapter(adapter);

        wifiList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), String.valueOf(preWifi.get(position)), Toast.LENGTH_SHORT).show();

                //리스트 뷰의 위치를 preWifi 배열의 인덱스 값으로 사용하여 정보 추출 (DB에 맞게 변경 필요 시 자유롭게 바꿀 것)
                Intent intent = new Intent();
                intent.putExtra("locate",  String.valueOf(preWifi.get(position)));
                String ip = wifidb.getWifi(String.valueOf(preWifi.get(position))).getWifiMac();
                intent.putExtra("IP",ip);
                //TODO 와이파이 이름 & IP 가져오기
                setResult(RESULT_OK, intent);

                //액티비티(팝업) 닫기
                finish();
            }
        });

    }
}
