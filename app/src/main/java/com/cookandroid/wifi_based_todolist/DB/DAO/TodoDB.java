package com.cookandroid.wifi_based_todolist.DB.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.cookandroid.wifi_based_todolist.DB.DTO.Todo;

import java.util.ArrayList;

public class TodoDB extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Todo.db";

    public TodoDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//DB가 생성됬을 때 호출
        db.execSQL("CREATE TABLE IF NOT EXISTS TodoList (id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT NOT NULL, date TEXT NOT NULL, time TEXT NOT NULL, alarm INTEGER, memo TEXT, wifiInfo TEXT NOT NULL, checked INTEGER);");//id는 pk, 자동으로 하나씩 증가
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public Todo getTodo(int i){
        Todo todo=null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TodoList WHERE id ="+i, null);//가르키는 행위
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String wifiInfo = cursor.getString(cursor.getColumnIndex("wifiInfo"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                int alarm = cursor.getInt(cursor.getColumnIndex("alarm"));
                String memo = cursor.getString(cursor.getColumnIndex("memo"));
                int checked = cursor.getInt(cursor.getColumnIndex("checked"));

                todo = new Todo();
                todo.setId(id);
                todo.setContent(content);
                todo.setWifiInfo(wifiInfo);
                todo.setDate(date);
                todo.setTime(time);
                todo.setAlarm(alarm);
                todo.setMemo(memo);
                todo.setChecked(checked);
            }
        }
        cursor.close();
        return todo;
    }

    public ArrayList<Todo> getTodoList(String filterCondition){
        return getTodoList(filterCondition,null);
    }
    //SELECT 문
    public ArrayList<Todo> getTodoList(String filterCondition,String wifiInfoInput) { // 필터 조건에 해당하는 할 일 목록을 불러옵니다.
        ArrayList<Todo> todos = new ArrayList<>();
        Cursor cursor;

        SQLiteDatabase db = getReadableDatabase();//읽기 가능한
        if( null == filterCondition ) {
            return todos;
        }else if( filterCondition.equals("all") ){
            cursor = db.rawQuery("SELECT * FROM TodoList ORDER BY date DESC;", null);//가르키는 행위
        }else if( filterCondition.equals("wifiInfo") ){
            cursor = db.rawQuery("SELECT * FROM TodoList WHERE wifiInfo ='"+wifiInfoInput+"' ORDER BY date DESC;", null);//가르키는 행위
        }else{
            return todos;
        }

        if (cursor.getCount() != 0) {
            //조회한 데이터가 있는 경우
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String wifiInfo = cursor.getString(cursor.getColumnIndex("wifiInfo"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                int alarm = cursor.getInt(cursor.getColumnIndex("alarm"));
                String memo = cursor.getString(cursor.getColumnIndex("memo"));
                int checked = cursor.getInt(cursor.getColumnIndex("checked"));

                Todo todo = new Todo();
                todo.setId(id);
                todo.setContent(content);
                todo.setWifiInfo(wifiInfo);
                todo.setDate(date);
                todo.setTime(time);
                todo.setAlarm(alarm);
                todo.setMemo(memo);
                todo.setChecked(checked);
                todos.add(todo);
            }
        }
        cursor.close();

        return todos;
    }
    
    //여기까지 06.03 수정완료

    //INSERT 문
    public void InsertTodo(String content, String date, String time,int alarm ,String memo, String wifiInfo){
        SQLiteDatabase db = getWritableDatabase();//쓰기가 가능한
        db.execSQL("INSERT INTO TodoList (content, date, time, alarm, memo, wifiInfo, checked) VALUES('"+content +"','"+date +"','"+time +"','"+alarm+"','"+memo +"','"+wifiInfo +"','0');");
    }

    // UPDATE 문
    public void UpdateTodo(String content, String wifiInfo, String date, String time,int alarm, String memo, int id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE TodoList SET content = '"+content +"', wifiInfo = '"+wifiInfo +"', date = '"+date +"', time = '"+time +"', alarm = '"+alarm+"',memo = '"+memo +"' where id = '"+id +"';");
    }

    public void CheckTodo(String wifiInfo){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE TodoList SET checked = '1' where wifiInfo = '"+wifiInfo+"'");
    }

    public void UnCheckTodo(String wifiInfo){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE TodoList SET checked = '0' where wifiInfo = '"+wifiInfo+"'");
    }

    //DELETE 문
    public void DeleteTodo(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM TodoList WHERE id = '"+id +"';");
    }

    //할일에 등록된 삭제된 wifi를 공백으로 변경하는 UPDATE문
    public void WifiCleanInTodo(String wifiInfo) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE TodoList SET wifiInfo = '" + "' WHERE wifiInfo = '" + wifiInfo + "';");
    }

    //할일 완료 체크 박스 클릭 시 동작하는 UPDATE 문
    public void UpadateCheck(int id, int check) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE TodoList SET checked = '" + check + "' WHERE id = '" + id + "';");
    }
}
