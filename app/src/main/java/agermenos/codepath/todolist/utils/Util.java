package agermenos.codepath.todolist.utils;

import android.os.Bundle;

import java.util.Date;

import agermenos.codepath.todolist.pojos.TodoList;

/**
 * Created by Alejandro on 10/30/15.
 */
public class Util {
    public static Bundle createBundleWithTodoList(TodoList todoList){
        Bundle b=new Bundle();
        b.putInt("id", todoList.getId());
        b.putString("name", todoList.getName());
        b.putLong("date", todoList.getCreationDate().getTime());
        return b;
    }

    public static TodoList getTodoListFromBundle(Bundle savedInstanceState) {
        TodoList todoList = new TodoList();
        todoList.setId(savedInstanceState.getInt("id"));
        todoList.setName(savedInstanceState.getString("name"));
        todoList.setCreationDate(new Date(savedInstanceState.getLong("date")));
        return todoList;
    }
}
