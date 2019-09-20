package sheridan.likej.mytodo.utilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import sheridan.likej.mytodo.database.TodoEntity;

public class SampleData {
    private static final String SAMPLE_TITLE_1 = "Room SQLite.";
    private static final String SAMPLE_TITLE_2 = "Google recommends using Room instead of SQLite.";
    private static final String SAMPLE_TITLE_3 = "Apps that handle non-trivial amounts";

    private static final String SAMPLE_DESC_1 = "Room provides an abstraction layer over SQLite.";
    private static final String SAMPLE_DESC_2 = "Google highly recommends using Room instead of SQLite. " +
            "\nHowever, if you prefer to use SQLite APIs directly, read Save Data Using SQLite.";
    private static final String SAMPLE_DESC_3 = "Apps that handle non-trivial amounts of structured data can benefit greatly from persisting that data locally." +
            "\nThe most common use case is to cache relevant pieces of data.";

    private static Date getDate(int diff) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.MILLISECOND, diff);
        return cal.getTime();
    }
    public static List<TodoEntity> getTodos(){
        List<TodoEntity> todos = new ArrayList<>();
        todos.add(new TodoEntity(1, SAMPLE_TITLE_1, SAMPLE_DESC_1, getDate(0)));
        todos.add(new TodoEntity(2, SAMPLE_TITLE_2, SAMPLE_DESC_2, getDate(-1)));
        todos.add(new TodoEntity(3, SAMPLE_TITLE_3, SAMPLE_DESC_3, getDate(-2)));
        return todos;
    }
}
