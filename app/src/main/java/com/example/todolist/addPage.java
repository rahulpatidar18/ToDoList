package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

public class addPage extends AppCompatActivity {

    private EditText taskNameInput, taskDetailInput;
    private Spinner categorySpinner;
    private Button submitButton;
    ImageView home, calender, add, track, setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);

        taskNameInput = findViewById(R.id.taskNameInput);
        taskDetailInput = findViewById(R.id.taskDetailInput);
        categorySpinner = findViewById(R.id.category_spinner);
        submitButton = findViewById(R.id.submitTaskButton);

        List<String> categories = Arrays.asList("Select Category", "Work", "Study", "Personal", "Fitness", "Shopping");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.spinner_item,
                categories
        ) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                TextView view = (TextView) super.getDropDownView(position, convertView, parent);
                if (position == 0) {
                    view.setTextColor(getResources().getColor(android.R.color.darker_gray));
                } else {
                    view.setTextColor(getResources().getColor(R.color.black));
                }
                return view;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        submitButton.setOnClickListener(view -> {
            String name = taskNameInput.getText().toString().trim();
            String description = taskDetailInput.getText().toString().trim();
            String category = categorySpinner.getSelectedItem().toString();

            if (name.isEmpty()) {
                Toast.makeText(addPage.this, "Task name cannot be empty!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (category.equals("Select Category")) {
                Toast.makeText(addPage.this, "Please select a category!", Toast.LENGTH_SHORT).show();
                return;
            }

            task newTask = new task(name, description, false, category);
            TaskDatabase.getInstance(addPage.this).taskDao().insert(newTask);

            Toast.makeText(addPage.this, "Task added", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(addPage.this, homepage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });

        setupBottomNavbar("add");

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(addPage.this, homepage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
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
            if (!currentPage.equals("home")) {
                Intent intent = new Intent(this, homepage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        calender.setOnClickListener(v -> {
            if (!currentPage.equals("calendar")) {
                Intent intent = new Intent(this, calenderPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        add.setOnClickListener(v -> {});

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
