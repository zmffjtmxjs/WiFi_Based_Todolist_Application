package com.cookandroid.wifi_based_todolist.DB.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.cookandroid.wifi_based_todolist.DB.DTO.Todo;
import com.cookandroid.wifi_based_todolist.DB.DTO.Wifi;

import java.util.ArrayList;

public class WifiDB extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Wifi.db";

    public WifiDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//DB가 생성됬을 때 호출
        db.execSQL("CREATE TABLE IF NOT EXISTS WifiInfo (wifiMac String NOT NULL, wifiInfo TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oidVersion, int newVersion) {
        onCreate(db);
    }

    public Wifi getWifi(String locate) {
        Wifi wifi = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM WifiInfo WHERE wifiInfo = " + "'" + locate + "'", null);//가르키는 행위
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                String wifiMac = cursor.getString(cursor.getColumnIndex("wifiMac"));
                String wifiInfo = cursor.getString(cursor.getColumnIndex("wifiInfo"));

                wifi = new Wifi();
                wifi.setWifiMac(wifiMac);
                wifi.setWifiInfo(wifiInfo);
            }
        }
        cursor.close();
        return wifi;
    }

    //SELECT 문
    public ArrayList<Wifi> getWifiList() {
        ArrayList<Wifi> wifis = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();//읽기 가능한
        Cursor cursor = db.rawQuery("SELECT * FROM WifiInfo", null);//가르키는 행위

        if (cursor.getCount() != 0) {
            //조회한 데이터가 있는 경우
            while (cursor.moveToNext()) {
                String wifiMac = cursor.getString(cursor.getColumnIndex("wifiMac"));
                String wifiInfo = cursor.getString(cursor.getColumnIndex("wifiInfo"));

                Wifi wifi = new Wifi();
                wifi.setWifiMac(wifiMac);
                wifi.setWifiInfo(wifiInfo);
                wifis.add(wifi);
            }
        }
        cursor.close();

        return wifis;
    }

    //여기까지 06.08 수정완료

    //INSERT 문
    public void InsertWifi(String wifiMac, String wifiInfo){
        SQLiteDatabase db = getWritableDatabase();//쓰기가 가능한
        db.execSQL("INSERT INTO WifiInfo (wifiMac, wifiInfo) VALUES('"+wifiMac +"','"+wifiInfo +"');");
    }

    // UPDATE 문
    public void UpdateWifi(String wifiMac, String wifiInfo, String newWifiInfo){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE WifiInfo SET wifiMac = '"+wifiMac +"', wifiInfo = '"+newWifiInfo +"' where wifiInfo = '"+wifiInfo+"'");
    }

    //DELETE 문
    public void DeleteWifi(String wifiInfo){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM WifiInfo WHERE wifiInfo = '" + wifiInfo + "'");
    }
}
