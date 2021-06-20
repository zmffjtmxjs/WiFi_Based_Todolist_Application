package com.cookandroid.wifi_based_todolist.module;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.cookandroid.wifi_based_todolist.DB.DAO.TodoDB;
import com.cookandroid.wifi_based_todolist.DB.DTO.Todo;
import com.cookandroid.wifi_based_todolist.R;
import com.cookandroid.wifi_based_todolist.page.AddToDoActivity;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private ArrayList<Todo> todos;
    private Context context;

    private TodoDB tododb;


    public CustomAdapter(ArrayList<Todo> todo, Context context) {
        this.todos = todo;
        this.context = context;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        Todo item = todos.get(i);
        final int pos = i;
        final Context context = parent.getContext();

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_list,parent,false);
        }

        CheckBox checkBox = view.findViewById(R.id.checkBox);
        TextView textView = view.findViewById(R.id.tv_content);
        ConstraintLayout toDoItem = view.findViewById(R.id.toDoItem);

        textView.setText(item.getContent());

        if(item.getChecked() == 1) {
            checkBox.setChecked(true);
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            checkBox.setChecked(false);
        }



        toDoItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddToDoActivity.setToDoId(item.getId());
                Intent intent = new Intent(view.getContext(), AddToDoActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                tododb = new TodoDB(parent.getContext());
                if (isChecked) {
                    textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    tododb.UpadateCheck(item.getId(), 1);
                } else {
                    textView.setPaintFlags(0);
                    tododb.UpadateCheck(item.getId(), 0);

                }
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
