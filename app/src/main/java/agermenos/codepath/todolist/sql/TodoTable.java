package agermenos.codepath.todolist.sql;

/**
 * Created by Alejandro on 10/13/15.
 */
public class TodoTable extends BaseTable {
    public final static String NAME = "TODOS";
    public final static String COLUMNS[][] =
            {   {"ID","integer","PRIMARY KEY"},
                    {"LIST_ID", "integer",""},
                    {"TEXT", "varchar",""},
                    {"CREATION_DATE", "date",""},
                    {"STATUS", "varchar",""},
                    {"PRIORITY", "varchar",""},
        };
    @Override
    public String getColumns(int x, int y) {
        return COLUMNS[x][y];
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getSize(){
        return COLUMNS.length;
    }
}
