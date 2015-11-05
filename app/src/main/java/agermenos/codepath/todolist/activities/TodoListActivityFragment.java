package agermenos.codepath.todolist.activities;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import agermenos.codepath.todolist.adapters.TodoListAdapter;
import agermenos.codepath.todolist.pojos.TodoList;
import agermenos.codepath.todolist.sql.TodoListDbHelper;
import agermenos.codepath.todolistver20.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class TodoListActivityFragment extends Fragment
        implements TodoListDialogFragment.EditNameDialogListener{

    TodoListAdapter todoListAdapter;
    TodoListDbHelper db;
    TodoListActivityFragment parent;
    ListView todoListView;

    @Override
    public void onDelete(TodoList todoListItem){
        String message;
        if (db==null){
            db = new TodoListDbHelper(getContext());
        }
        if (todoListItem.getId()!=null){
            db.deleteTodoList(todoListItem.getId());
            message=getString(R.string.todo_list_deleted);
        }
        else {
            message=getString(R.string.todo_list_not_found);
        }
        todoListAdapter = refreshTodoListAdapter();
        //todoListAdapter.swapItems(getTodoList()); //refreshing adapter
        todoListAdapter.remove(todoListItem);
        Snackbar.make(this.getView(), message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void onChangeName(TodoList todoList) {
        String message;
        todoListAdapter = refreshTodoListAdapter();
        if (db==null){
            db = new TodoListDbHelper(getContext());
        }
        if (todoList.getId()!=null){
            db.updateTodoList(todoList);
            message=getString(R.string.todo_list_updated);
            todoListAdapter.swapItems(getTodoList());
        }
        else {
            Integer id = db.createToDoList(todoList);
            todoList.setId(id);
            todoListAdapter.add(todoList);
            message=getString(R.string.todo_list_created);
        }
        Snackbar.make(this.getView(), message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private TodoListAdapter refreshTodoListAdapter() {
        if (todoListAdapter==null) {
            todoListAdapter = (TodoListAdapter)todoListView.getAdapter();
        }
        return todoListAdapter;
    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        void onItemSelected(Integer todoListSelected);
    }

    public TodoListActivityFragment() {
        parent=this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_todo_list, container, false);
        todoListView = (ListView) rootView.findViewById(R.id.listview_todos);
        todoListView.setAdapter(createUpdateTodoListAdapter());
        todoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((Callback) getActivity())
                        .onItemSelected(todoListAdapter.getItem(position).getId());
            }
        });
        return rootView;
    }

    private TodoListAdapter createUpdateTodoListAdapter() {
        List<TodoList>todoLists=getTodoList();
        if(null==todoLists || todoLists.isEmpty()){
            todoLists = new ArrayList<>();
            TodoList tl = new TodoList("Testing new list");
            int todoListId = db.createToDoList(tl);
            tl.setId(todoListId);
            todoLists.add(tl);
        }
        if (todoListAdapter==null) {
            return new TodoListAdapter(getContext(), todoLists);
        }
        return todoListAdapter;
    }

    private List<TodoList> getTodoList(){
        if (db==null) {
            db = new TodoListDbHelper(getContext());
        }
        return db.getAllToDosList();
    }
}
