package agermenos.codepath.todolist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import agermenos.codepath.todolist.activities.TodoListActivity;
import agermenos.codepath.todolist.pojos.TodoList;
import agermenos.codepath.todolistver20.R;


/**
 * Created by Alejandro on 10/18/15.
 */
public class TodoListAdapter extends ArrayAdapter<TodoList> {
    private Context mContext;
    List<TodoList> todoList;

    public TodoListAdapter(Context context, List<TodoList> todoList) {
        super(context, 0, todoList);
        mContext=context;
        this.todoList = todoList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final TodoList todoListItem = todoList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_todos, parent, false);
        }
        TextView tv_taskName = (TextView)convertView.findViewById(R.id.todo_task);
        tv_taskName.setText(todoListItem.getName());
        ImageButton editButton = (ImageButton)convertView.findViewById(R.id.edit_icon);
        ImageButton deleteButton = (ImageButton)convertView.findViewById(R.id.delete_icon);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext instanceof TodoListActivity) {
                    ((TodoListActivity) mContext).showEditDialog(todoListItem);
                }
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext instanceof TodoListActivity) {
                    ((TodoListActivity) mContext).deleteTodoList(todoListItem);
                }
            }
        });
        return convertView;
    }

    public void swapItems(List<TodoList> items) {
        this.todoList = items;
        notifyDataSetChanged();
    }

}
