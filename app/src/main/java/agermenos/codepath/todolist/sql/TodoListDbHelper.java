package agermenos.codepath.todolist.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import agermenos.codepath.todolist.pojos.Todo;
import agermenos.codepath.todolist.pojos.TodoList;

/**
 * Created by Alejandro on 10/13/15.
 */
public class TodoListDbHelper extends SQLiteOpenHelper{
    public final static String DATABASE_NAME="TodoList";
    public final static int DATABASE_VERSION=2;
    private static final String LOG = "TodoListDbHelper";
    BaseTable todoTbl=new TodoTable();
    BaseTable todoListTbl = new TodoListTable();

    public TodoListDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(createTableScript(new TodoListTable()));
        db.execSQL(createTableScript(new TodoTable()));
    }

    private String createTableScript(BaseTable table){
        StringBuffer createScript = new StringBuffer ("CREATE TABLE " + table.getName());
        createScript.append(" (");
        for (int k=0;k<table.getSize();k++) {
            createScript.append(table.getColumns(k,0)).append(" ")
                    .append(table.getColumns(k, 1))
                    .append((table.getColumns(k, 2) != null ?" "+table.getColumns(k,2):""))
                    .append(",");
        }
        createScript.replace(createScript.length()-1, createScript.length(), ")");
        return createScript.toString();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + todoListTbl.getName());
        db.execSQL("DROP TABLE IF EXISTS " + todoTbl.getName());
        // create new tables
        onCreate(db);
    }
    /**
    * Creating a todo
    */
    public long createToDo(Todo todo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = getValuesFromTodo(todo);
        // insert row
        long todo_id = db.insert(todoTbl.getName(), null, values);
        return new Integer((int) todo_id);
    }

    /**
     * Creating a todo_list
     */
    public Integer createToDoList(TodoList todoList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = getValuesFromTodoList(todoList);
        // insert row
        long todo_id = db.insert(todoListTbl.getName(), null, values);
        return new Integer((int) todo_id);
    }

    public TodoList getTodoList(long todoListId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + todoListTbl.getName() + " WHERE "
                + todoListTbl.getColumns(0,0) + " = " + todoListId;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        TodoList tdl = createTodoListFromCursor(c);

        return tdl;
    }

    public Todo getTodo(long todo_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + todoTbl.getName() + " WHERE "
                + todoTbl.getColumns(0,0) + " = " + todo_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Todo td = createTodoFromCursor(c);

        return td;
    }

    /*
     * getting all todolists
     * */
    public List<TodoList> getAllToDosList() {
        List<TodoList> todoList = new ArrayList<TodoList>();
        String selectQuery = "SELECT  * FROM " + todoListTbl.getName();

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                TodoList tdl = createTodoListFromCursor(c);

                // adding to todo list
                todoList.add(tdl);
            } while (c.moveToNext());
        }

        return todoList;
    }

    /*
     * getting all todos
     * */
    public List<Todo> getAllToDos() {
        List<Todo> todos = new ArrayList<Todo>();
        String selectQuery = "SELECT  * FROM " + todoTbl.getName();

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Todo td = createTodoFromCursor(c);

                // adding to todo list
                todos.add(td);
            } while (c.moveToNext());
        }

        return todos;
    }

    /*
     * getting all todos under single tag
     **/
    public List<TodoList> getTodosListById(int listId) {
        List<TodoList> todoList = new ArrayList<TodoList>();

        StringBuilder query = new StringBuilder("SELECT * FROM ");
        query.append(todoListTbl.getName());
        query.append(" WHERE id=").append(listId);

        Log.e(LOG, query.toString());

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query.toString(), null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                TodoList tdl=createTodoListFromCursor(c);

                // adding to todo list
                todoList.add(tdl);
            } while (c.moveToNext());
        }

        return todoList;
    }

    /*
     * getting all todos under single tag
     **/
    public List<Todo> getTodosById(int id) {
        List<Todo> todos = new ArrayList<Todo>();

        StringBuilder query = new StringBuilder("SELECT * FROM ");
        query.append(todoTbl.getName());
        query.append(" WHERE list_id=").append(id);

        Log.e(LOG, query.toString());

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query.toString(), null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Todo td=createTodoFromCursor(c);

                // adding to todo list
                todos.add(td);
            } while (c.moveToNext());
        }

        return todos;
    }

    /*
     * Updating a todoList
     */
    public int updateTodoList(TodoList todoList) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = getValuesFromTodoList(todoList);

        // updating row
        return db.update(todoListTbl.getName(), values, todoListTbl.getColumns(0,0) + " = ?",
                new String[] { String.valueOf(todoList.getId()) });
    }

    /*
     * Updating a todo
     */
    public int updateToDo(Todo todo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = getValuesFromTodo(todo);

        // updating row
        return db.update(todoTbl.getName(), values, todoTbl.getColumns(0,0) + " = ?",
                new String[] { String.valueOf(todo.getId()) });
    }

    /*
     * Deleting a todoList
     */
    public void deleteTodoList(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(todoListTbl.getName(), todoListTbl.getColumns(0,0) + " = ?",
                new String[] { String.valueOf(id) });
    }

    /*
     * Deleting a todo
     */
    public void deleteToDo(long tado_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(todoTbl.getName(), todoTbl.getColumns(0,0) + " = ?",
                new String[] { String.valueOf(tado_id) });
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    private Todo createTodoFromCursor(Cursor c) {
        Todo td = new Todo();
        td.setId(c.getInt(c.getColumnIndex(todoTbl.getColumns(0,0))));
        td.setListId(c.getInt(c.getColumnIndex(todoTbl.getColumns(1,0))));
        td.setText(c.getString(c.getColumnIndex(todoTbl.getColumns(2,0))));
        td.setCreationDate(new Date(c.getString(c.getColumnIndex(todoTbl.getColumns(3,0)))));
        td.setStatus(c.getString(c.getColumnIndex(todoTbl.getColumns(4,0))));
        td.setPriority(Todo.choosePriority(c.getString(c.getColumnIndex(todoTbl.getColumns(5,0)))));
        return td;
    }

    private TodoList createTodoListFromCursor(Cursor c) {
        TodoList tdl = new TodoList();
        tdl.setId(c.getInt(c.getColumnIndex(todoListTbl.getColumns(0, 0))));
        tdl.setName(c.getString(c.getColumnIndex(todoListTbl.getColumns(1, 0))));
        tdl.setCreationDate(new Date(c.getLong(c.getColumnIndex(todoListTbl.getColumns(2,0)))));
        return tdl;
    }

    private ContentValues getValuesFromTodo(Todo todo){
        ContentValues values = new ContentValues();
        values.put(todoTbl.getColumns(0, 0), todo.getId());
        values.put(todoTbl.getColumns(1, 0), todo.getListId());
        values.put(todoTbl.getColumns(2, 0), todo.getText());
        values.put(todoTbl.getColumns(3, 0), String.valueOf(todo.getCreationDate()));
        values.put(todoTbl.getColumns(4, 0), todo.getStatus());
        values.put(todoTbl.getColumns(5, 0), String.valueOf(todo.getPriority()));
        return values;
    }

    private ContentValues getValuesFromTodoList(TodoList todoList){
        ContentValues values = new ContentValues();
        values.put(todoListTbl.getColumns(0, 0), todoList.getId());
        values.put(todoListTbl.getColumns(1, 0), todoList.getName());
        values.put(todoListTbl.getColumns(2, 0), todoList.getCreationDate().getTime());
        return values;
    }


}
