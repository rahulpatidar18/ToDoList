package com.example.todolist;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryTaskActivity extends AppCompatActivity {

    TextView title, emptyView;
    RecyclerView recyclerView;
    CustomAdapter adapter;
    List<task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_task);

        title = findViewById(R.id.category_title);
        recyclerView = findViewById(R.id.task_list);
        emptyView = findViewById(R.id.emptyView);

        String category = getIntent().getStringExtra("category_name");

        if (category == null || category.isEmpty()) {
            finish();
            return;
        }

        title.setText(category + " Tasks");

        TaskDatabase db = TaskDatabase.getInstance(this);
        tasks = db.taskDao().getTasksByCategory(category);

        adapter = new CustomAdapter(tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        updateEmptyView();
        enableSwipeToDelete(db);
    }

    private void enableSwipeToDelete(TaskDatabase db) {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                task deletedTask = tasks.get(position);
                db.taskDao().delete(deletedTask);
                tasks.remove(position);
                adapter.notifyItemRemoved(position);
                updateEmptyView();
            }
        };

        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
    }

    private void updateEmptyView() {
        if (tasks == null || tasks.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}
