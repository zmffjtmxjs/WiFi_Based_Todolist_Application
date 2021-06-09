package com.cookandroid.wifi_based_todolist.page;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.wifi_based_todolist.DB.DAO.TodoDB;
import com.cookandroid.wifi_based_todolist.DB.DTO.Todo;
import com.cookandroid.wifi_based_todolist.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private ArrayList<Todo> todos;
    private Context context;
    private TodoDB todoDB;

    public CustomAdapter(ArrayList<Todo> todo, Context context) {
        this.context = context;
        todoDB = new TodoDB(context);
        this.todos = todoDB.getTodoList();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Todo item = todos.get(i);
        final int pos = i;
        final Context context = viewGroup.getContext();

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_list,viewGroup,false);
        }

        CheckBox checkBox = view.findViewById(R.id.checkBox);
        TextView textView = view.findViewById(R.id.tv_content);
        ConstraintLayout toDoItem = view.findViewById(R.id.toDoItem);

        textView.setText(item.getContent());

        toDoItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, pos + "번", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(view.getContext(), AddToDoActivity.class);
                intent.putExtra("mode", 1);
                intent.putExtra("toDoId", pos);
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
            }
        });






        return view;
    }


    @Override
    public int getCount() {
        return todos.size();
    }

    @Override
    public Object getItem(int i) {
        return todos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


}
