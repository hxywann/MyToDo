package sheridan.likej.mytodo.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import sheridan.likej.mytodo.database.AppRepository;
import sheridan.likej.mytodo.database.TodoEntity;
import sheridan.likej.mytodo.utilities.SampleData;

public class MainViewModel extends AndroidViewModel {

    public LiveData<List<TodoEntity>> mTodos;
    private AppRepository mRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(application.getApplicationContext());
        mTodos = mRepository.mTodos;
    }

    public void addSampleData() {
        mRepository.addSampleData();
    }

    public void deleteAllData() {
        mRepository.deleteAllData();
    }
}
