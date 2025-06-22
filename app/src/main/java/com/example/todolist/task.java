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

    public String category;

    public task() {}

    public task(String task_name, String detail, boolean isCompleted, String category) {
        this.task_name = task_name;
        this.detail = detail;
        this.isCompleted = isCompleted;
        this.category = category;
    }


    public int getId() {
        return id;
    }

    public String getTask_name() {
        return task_name;
    }

    public String getDetail() {
        return detail;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public String getCategory() {
        return category;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
