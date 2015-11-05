package agermenos.codepath.todolist.sql;

/**
 * Created by Alejandro on 10/13/15.
 */
public abstract class BaseTable {

    public abstract String getColumns (int x, int y);
    public abstract int getSize();
    public abstract String getName();
}
