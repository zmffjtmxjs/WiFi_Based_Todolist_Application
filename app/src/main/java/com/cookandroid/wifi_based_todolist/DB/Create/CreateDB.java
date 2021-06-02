package com.cookandroid.wifi_based_todolist.DB.Create;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateDB extends SQLiteOpenHelper {//DB생성 및 버전 관리를 도와주는 헬퍼 클래스
   static final String DATABASE_NAME = "TodoDB";


    public CreateDB(Context context, int version) {//DB 파일 생성
        super(context, DATABASE_NAME, null,  version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//테이블 생성
        db.execSQL("Create table TodoDB (num INTEGER, content text, wifiInfo text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//version 값이 상향될 때 실행
        db.execSQL("Drop table IF EXISTS TodoDB");//기존 테이블을 삭제하고 새로운 테이블을 만듬
        onCreate(db);

    }

}



//execSQL: Select 명령 외 SQL 명령어 실행