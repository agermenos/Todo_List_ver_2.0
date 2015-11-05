package agermenos.codepath.todolist.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alejandro on 10/11/2015.
 */
public class FormatUtil {

    public static String formatDate(Date date, String pattern){
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(pattern);
        return DATE_FORMAT.format(date);
    }
}
