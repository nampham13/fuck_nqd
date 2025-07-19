package com.example.todolistapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY id ASC")
    List<Task> getAllTasks();
    
    @Insert
    long insert(Task task);
    
    @Update
    void update(Task task);
    
    @Delete
    void delete(Task task);
    
    @Query("DELETE FROM tasks")
    void deleteAll();
}