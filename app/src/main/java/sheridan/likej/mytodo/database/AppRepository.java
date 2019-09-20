package sheridan.likej.mytodo.database;

import android.content.Context;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import sheridan.likej.mytodo.utilities.SampleData;

public class AppRepository {
    private static AppRepository ourInstance;

    public LiveData<List<TodoEntity>> mTodos;

    private AppDatabase mDb;
    private Executor executor =  Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context) {
        if(ourInstance == null){
            ourInstance = new AppRepository(context);
        }

        return ourInstance;
    }

    private AppRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
        mTodos = getAllNotes();

    }

    public void addSampleData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {

                mDb.todoDao().insertAll(SampleData.getTodos());
            }
        });
    }

    private LiveData<List<TodoEntity>> getAllNotes(){
        return mDb.todoDao().getAll();
    }

    public void deleteAllData() {
        executor.execute(new Runnable() {
            @Override
            public void run(){
                mDb.todoDao().deleteAll();
            }
        });

    }

    public TodoEntity getTodoById(int todoId) {
        return mDb.todoDao().getTodoById(todoId);
    }

    public void insertTodo(TodoEntity todo) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.todoDao().insertTodo(todo);  //if it is exsting todo it will be replaced
            }
        });
    }

    public void deleteTodo(TodoEntity todo) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.todoDao().deleteTodo(todo);
            }
        });

    }
}
