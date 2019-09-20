package sheridan.likej.mytodo.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTodo(TodoEntity todoEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TodoEntity> todoList);

    @Delete
    void deleteTodo(TodoEntity todoEntity);

    @Query("SELECT * FROM mytodos WHERE id = :id")
    TodoEntity getTodoById(int id);

    @Query("SELECT * FROM mytodos ORDER BY dueDate DESC")
    LiveData<List<TodoEntity>> getAll();

    @Query("DELETE FROM mytodos")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM mytodos")
    int getCount();
}
