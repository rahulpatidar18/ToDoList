package com.example.todolist;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insert(task task);

    @Delete
    void delete(task task);

    @Query("SELECT * FROM tasks")
    List<task> getAllTasks();
}
