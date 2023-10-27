package com.bawp.todoister.data;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.RoomDatabase;

import com.bawp.todoister.model.Task;
import com.bawp.todoister.util.TaskRoomDatabase;

import java.util.List;

/**
 * Created by junyi on 27/10/23
 * One central place where we have all the data
 * For code organization purposes
 */
public class TodoisterRepo {
    private final TaskDAO taskDAO;
    private final LiveData<List<Task>> allTasks;

    public TodoisterRepo(Application application) {
        TaskRoomDatabase database = TaskRoomDatabase.getDatabase(application);
        taskDAO = database.taskDAO();
        allTasks = database.taskDAO().getTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public void insert(Task task) {
        TaskRoomDatabase.databaseWriterExecutor.execute(() -> taskDAO.insertTask(task));
    }

    public LiveData<Task> get(long id) {
        return taskDAO.get(id);
    }

    public void update(Task task) {
        TaskRoomDatabase.databaseWriterExecutor.execute(() -> taskDAO.update(task));
    }

    public void delete(Task task) {
        TaskRoomDatabase.databaseWriterExecutor.execute(() -> taskDAO.delete(task));
    }
}
