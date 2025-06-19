package com.example.todolist;

import static android.widget.Toast.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class homepage extends AppCompatActivity {

    RecyclerView recyclerView;

    ImageView home, calender, add, track, setting;
    ImageView selectedIcon = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        recyclerView = findViewById(R.id.taskview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        TaskDatabase db = TaskDatabase.getInstance(this);
        List<task> taskList = db.taskDao().getAllTasks();

        CustomAdapter adapter = new CustomAdapter(taskList);
        recyclerView.setAdapter(adapter);
        setupBottomNavbar("home");

        //no task available
        TextView emptyView = findViewById(R.id.emptyView);

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
                return false; // Not used for drag-and-drop
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();


                task taskToDelete = taskList.get(position);

                TaskDatabase db = TaskDatabase.getInstance(homepage.this);
                db.taskDao().delete(taskToDelete);

                // Remove from list & notify adapter
                taskList.remove(position);
                adapter.notifyItemRemoved(position);
                makeText(homepage.this,getString(R.string.task_deleted), LENGTH_SHORT).show();
                if (taskList.isEmpty()) {
                    emptyView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }

            }
        };




        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);

    }


    private void setupBottomNavbar(String currentPage) {
        ImageView home = findViewById(R.id.menuButton);
        ImageView calendar = findViewById(R.id.calenderButton);
        ImageView add = findViewById(R.id.addbutton);
        ImageView track = findViewById(R.id.trackbutton);
        ImageView setting = findViewById(R.id.settingbutton);

        // Reset all to default icons
        home.setImageResource(R.drawable.home);
        calendar.setImageResource(R.drawable.calender);
        add.setImageResource(R.drawable.add);
        track.setImageResource(R.drawable.completion);
        setting.setImageResource(R.drawable.setting);

        // Highlight current icon
        switch (currentPage) {
            case "home":
                home.setImageResource(R.drawable.home_filled);
                break;
            case "calendar":
                calendar.setImageResource(R.drawable.calender_filled);
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
            if (!currentPage.equals("home")) {
                startActivity(new Intent(this, homepage.class));
                loadFragment(new HomeFragment());
                finish();
            }
        });

        calendar.setOnClickListener(v -> {

            if (!currentPage.equals("calendar")) {
                startActivity(new Intent(this, calenderPage.class));

                finish();
            }
        });

        add.setOnClickListener(v -> {

            if (!currentPage.equals("add")) {
                startActivity(new Intent(this, addPage.class));

                finish();

            }
        });

        track.setOnClickListener(v -> {
            if (!currentPage.equals("track")) {
                startActivity(new Intent(this, trackPage.class));
                finish();
            }
        });

        setting.setOnClickListener(v -> {
            if (!currentPage.equals("setting")) {
                startActivity(new Intent(this, SettingPage.class));
                finish();
            }
        });
    }
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}