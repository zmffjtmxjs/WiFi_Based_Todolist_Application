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
import androidx.constraintlayout.widget.ConstraintLayout;
import com.cookandroid.wifi_based_todolist.DB.DTO.Todo;
import com.cookandroid.wifi_based_todolist.R;
import com.cookandroid.wifi_based_todolist.page.AddToDoActivity;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private ArrayList<Todo> todos;
    private Context context;

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

        toDoItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddToDoActivity.setToDoId(pos + 1);
                Intent intent = new Intent(view.getContext(), AddToDoActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    textView.setPaintFlags(0);
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
