package agermenos.codepath.todolist.activities;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import agermenos.codepath.todolist.pojos.TodoList;
import agermenos.codepath.todolist.utils.Util;
import agermenos.codepath.todolistver20.R;

/**
 * Created by Alejandro on 10/23/15.
 */
public class TodoListDialogFragment extends DialogFragment {

    private TodoList todoList;

    public interface EditNameDialogListener {
        void onChangeName(TodoList todoList);

        void onDelete(TodoList todoListItem);
    }

    EditText mEditText;
    Button mOkButton;
    Button mCancelButton;
    public TodoListDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    public TodoListDialogFragment getMe(){
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.todo_list_dialog, container);

        mEditText = (EditText) view.findViewById(R.id.txt_todo_list_name);
        mOkButton = (Button) view.findViewById(R.id.button_ok);
        mCancelButton = (Button) view.findViewById(R.id.button_cancel);
        if (this.getArguments()!=null){
            todoList= Util.getTodoListFromBundle(this.getArguments());
        }
        getDialog().setTitle("New ToDo List");
        if (todoList!=null) {
            mEditText.setText(todoList.getName());
        }

        // Show soft keyboard automagically
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (todoList==null) {
                    todoList=new TodoList();
                }
                todoList.setName(mEditText.getText().toString());
                for (Fragment f:getFragmentManager().getFragments()) {
                    if (f instanceof TodoListActivityFragment) {
                        ((TodoListActivityFragment) f).onChangeName(todoList);
                    }
                }
                getMe().dismiss();
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMe().dismiss();
            }
        });

        return view;
    }

    public TodoList getTodoList() {
        return todoList;
    }

    public void setTodoList(TodoList todoList) {
        this.todoList = todoList;
    }
}