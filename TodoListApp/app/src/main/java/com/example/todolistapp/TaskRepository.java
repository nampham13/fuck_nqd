package com.example.todolistapp;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskRepository {
    private final TaskDao taskDao;
    private final ExecutorService executorService;

    public TaskRepository(Application application) {
        TaskDatabase database = TaskDatabase.getInstance(application);
        taskDao = database.taskDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public List<Task> getAllTasks() {
        try {
            return new GetAllTasksAsyncTask(taskDao).execute().get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void insert(Task task) {
        executorService.execute(() -> taskDao.insert(task));
    }

    public void update(Task task) {
        executorService.execute(() -> taskDao.update(task));
    }

    public void delete(Task task) {
        executorService.execute(() -> taskDao.delete(task));
    }

    private static class GetAllTasksAsyncTask extends AsyncTask<Void, Void, List<Task>> {
        private final TaskDao taskDao;

        private GetAllTasksAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected List<Task> doInBackground(Void... voids) {
            return taskDao.getAllTasks();
        }
    }
}