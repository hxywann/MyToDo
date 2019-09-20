package sheridan.likej.mytodo.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import sheridan.likej.mytodo.database.AppRepository;
import sheridan.likej.mytodo.database.DateConverter;
import sheridan.likej.mytodo.database.TodoEntity;

public class EditorViewModel extends AndroidViewModel {

    public MutableLiveData<TodoEntity> mLiveData =
            new MutableLiveData<>();
    private AppRepository mRepository;

    private Executor executor = Executors.newSingleThreadExecutor();



    public EditorViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
    }

    public void loadData(final int todoId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                TodoEntity todo = mRepository.getTodoById(todoId);
                mLiveData.postValue(todo);
            }
        });
    }


    public void saveTodo(String todoTitle, String todoDesc, Date dueDate) {      //, Date dueDate
        TodoEntity todo = mLiveData.getValue();


        if (todo == null) { //new note
            if(TextUtils.isEmpty(todoTitle.trim()) || TextUtils.isEmpty(todoDesc.trim())){  // if user didn't type anything or some space
                return;
            }
            todo = new TodoEntity(todoTitle.trim(), todoDesc.trim(), dueDate);  //, dueDate

        }else{ //existing note
            todo.setTitle(todoTitle.trim());
            todo.setDescription(todoDesc.trim());
            todo.setDueDate(dueDate);
        }
        mRepository.insertTodo(todo);
    }

    public void deleteTodo() {
        mRepository.deleteTodo(mLiveData.getValue());
    }
}
