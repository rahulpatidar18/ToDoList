package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class homepage extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView categoryView;
    CheckBox checkBox;
    TextView emptyView;
    List<task> taskList;
    CustomAdapter taskAdapter;

    Category[] categoryList = new Category[]{
            new Category("Work"),
            new Category("Study"),
            new Category("Personal"),
            new Category("Fitness"),
            new Category("Shopping")
    };

    ImageView home, calender, add, track, setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        recyclerView = findViewById(R.id.taskview);
        categoryView = findViewById(R.id.Category_View);
        emptyView = findViewById(R.id.emptyView);
        checkBox = findViewById(R.id.checkBox);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoryView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        CategoryAdapter adapter = new CategoryAdapter(categoryList, category -> {
            Intent intent = new Intent(homepage.this, CategoryTaskActivity.class);
            intent.putExtra("category_name", category.getCategoryName());
            startActivity(intent);
        });
        categoryView.setAdapter(adapter);

        setupBottomNavbar("home");

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finishAffinity();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadTasks();
    }

    private void reloadTasks() {
        TaskDatabase db = TaskDatabase.getInstance(this);
        taskList = db.taskDao().getAllTasks();

        taskAdapter = new CustomAdapter(taskList);
        recyclerView.setAdapter(taskAdapter);

        if (taskList.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

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
                task taskToDelete = taskList.get(position);
                db.taskDao().delete(taskToDelete);
                taskList.remove(position);
                taskAdapter.notifyItemRemoved(position);

                if (taskList.isEmpty()) {
                    emptyView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }
        };

        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
    }

    private void setupBottomNavbar(String currentPage) {
        home = findViewById(R.id.menuButton);
        calender = findViewById(R.id.calenderButton);
        add = findViewById(R.id.addbutton);
        track = findViewById(R.id.trackbutton);
        setting = findViewById(R.id.settingbutton);

        home.setImageResource(R.drawable.home);
        calender.setImageResource(R.drawable.calender);
        add.setImageResource(R.drawable.add);
        track.setImageResource(R.drawable.completion);
        setting.setImageResource(R.drawable.setting);

        switch (currentPage) {
            case "home":
                home.setImageResource(R.drawable.home_filled);
                break;
            case "calendar":
                calender.setImageResource(R.drawable.calender_filled);
                break;
            case "add":
                add.setImageResource(R.drawable.add_filled);
                break;
            case "track":
                track.setImageResource(R.drawable.completion_filled);
                break;
            case "setting":
                setting.setImageResource(R.drawable.setting_filled);
                break;
        }

        home.setOnClickListener(v -> {
        });

        calender.setOnClickListener(v -> {
            if (!currentPage.equals("calendar")) {
                Intent intent = new Intent(this, calenderPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        add.setOnClickListener(v -> {
            if (!currentPage.equals("add")) {
                Intent intent = new Intent(this, addPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        track.setOnClickListener(v -> {
            if (!currentPage.equals("track")) {
                Intent intent = new Intent(this, trackPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        setting.setOnClickListener(v -> {
            if (!currentPage.equals("setting")) {
                Intent intent = new Intent(this, SettingPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }
}
