package com.example.todolist;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks")
public class task {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String task_name;
    public String detail;
    public boolean isCompleted;

    public task() {}

    public task(String task_name, String detail, boolean isCompleted) {
        this.task_name = task_name;
        this.detail = detail;
        this.isCompleted = isCompleted;
    }
}
