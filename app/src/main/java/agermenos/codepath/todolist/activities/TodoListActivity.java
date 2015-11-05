package agermenos.codepath.todolist.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import agermenos.codepath.todolist.pojos.TodoList;
import agermenos.codepath.todolist.utils.Util;
import agermenos.codepath.todolistver20.R;

public class TodoListActivity extends AppCompatActivity implements TodoListActivityFragment.Callback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditDialog(null);
            }
        });
    }

    @Override
    public void onItemSelected(Integer index) {
        Snackbar.make(this.getCurrentFocus(), "Item selected! " + index, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
      /**  if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle args = new Bundle();
            args.putString(Intent.EXTRA_TEXT, artistSelected);

            TopHitsActivityFragment fragment = new TopHitsActivityFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.player_detail_container, fragment, TOP_HITS_FRAGMENTS_TAG)
                    .commit();
        } else {**/
         //   Intent intent = new Intent(this, TopHitsActivity.class)
         //           .putExtra(Intent.EXTRA_TEXT, artistSelected);
         //   startActivity(intent);
       // }
    }

    public void showEditDialog(TodoList todoList) {
        FragmentManager fm = getSupportFragmentManager();
        TodoListDialogFragment editNameDialog = new TodoListDialogFragment();
        if (todoList!=null) {
            editNameDialog.setArguments(Util.createBundleWithTodoList(todoList));
        }
        editNameDialog.show(fm, "fragment_edit_todo");
    }

    public void deleteTodoList(TodoList todoListItem) {
        for (android.support.v4.app.Fragment f:getSupportFragmentManager().getFragments()) {
            if (f instanceof TodoListActivityFragment) {
                ((TodoListActivityFragment) f).onDelete(todoListItem);
            }
        }
    }
}
