package com.cookandroid.wifi_based_todolist.DB.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.cookandroid.wifi_based_todolist.DB.DTO.Todo;
import com.cookandroid.wifi_based_todolist.DB.DTO.TodoItem;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Todo.db";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//DB가 생성됬을 때 호출
        db.execSQL("CREATE TABLE IF NOT EXISTS TodoList (id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT NOT NULL, date TEXT NOT NULL, time TEXT NOT NULL, memo TEXT, wifiInfo TEXT NOT NULL)");//id는 pk값, 자동으로 하나씩 증가 / title은 text 데이터 타입 사용(string)
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oidVersion, int newVersion) {
        onCreate(db);
    }

    //SELECT 문
    public ArrayList<Todo> getTodoList() {
        ArrayList<Todo> todos = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();//읽기 가능한
        Cursor cursor = db.rawQuery("SELECT * FROM TodoList ORDER BY writeDATE DESC", null);//가르키는 행위

        if (cursor.getCount() != 0) {
            //조회한 데이터가 있는 경우
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String wifiINfo = cursor.getString(cursor.getColumnIndex("wifiInfo"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                String memo = cursor.getString(cursor.getColumnIndex("memo"));

                Todo todo = new Todo();
                todo.setId(id);
                todo.setContent(content);
                todo.setWifiInfo(wifiINfo);
                todo.setDate(date);
                todo.setTime(time);
                todo.setMemo(memo);
                todos.add((todo));
            }
        }
        cursor.close();

        return todos;
    }
    
    //여기까지 06.03 수정완료

    //INSERT 문
    public void InsertTodo(String _title, String _content, String _writeDate){
        SQLiteDatabase db = getWritableDatabase();//쓰기가 가능한
//        db.execSQL("INSERT INTO TodoList (title, content, writeDate) VALUES('"+_title +"','"+_content +"','"+_writeDate +"');");//물음표 순서대로 매개변수의 값이 들어감
      db.execSQL("INSERT INTO TodoList (title, content, writeDate) VALUES(?,?,?);");
    }

    // UPDATE 문
    public void UpdateTodo(String _title, String _content, String _writeDate, String _beforeDate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE TodoList SET title = '"+_title +"', content = '"+_content +"', writeDate = '"+_writeDate +"' WHERE writeDate = '"+_beforeDate +"'");//key값으로 조건을 지정 id로도 가능
//      db.execSQL("UPDATE TodoList SET title = ?, content = ?, writeDate = ? where id = ?");
    }

    //DELETE 문
    public void DeleteTodo(String _beforeDate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM TodoList WHERE writeDate = ?");
    }
}
