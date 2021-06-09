package com.cookandroid.wifi_based_todolist.page;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.wifi_based_todolist.DB.DAO.TodoDB;
import com.cookandroid.wifi_based_todolist.DB.DTO.Todo;
import com.cookandroid.wifi_based_todolist.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<Todo> todos;
    private Context context;
    private TodoDB todoDB;

    public CustomAdapter(ArrayList<Todo> todos, Context context) {
        this.todos = todos;
        this.context = context;
        todoDB = new TodoDB(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

        return new ViewHolder(holder);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_content.setText(todos.get(position).getContent());
        //checkbox?
        //holder.checkBox.setChecked();
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_content;
        private CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.tv_content);
            checkBox = itemView.findViewById(R.id.checkBox);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int curPos = getAdapterPosition();
                    Todo todo = todos.get(curPos);

                    String[] strChoiceItems = {"수정","삭제"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("원하는 작업 선택");
                    builder.setItems(strChoiceItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position) {
                            if (position == 0){//수정하기

                            }else if (position == 1){//삭제하기

                            }

                        }
                    });
                    builder.show();

                }
            });
        }
        public void addItem(Todo _item){
            todos.add(0,_item);
            notifyItemInserted(0);

        }
    }
}
