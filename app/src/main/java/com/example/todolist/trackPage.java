package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

public class trackPage extends AppCompatActivity {

    ImageView home, calendar, add, track, setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_layout);

        setupBottomNavbar("track");

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(trackPage.this, homepage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
    }

    private void setupBottomNavbar(String currentPage) {
        home = findViewById(R.id.menuButton);
        calendar = findViewById(R.id.calenderButton);
        add = findViewById(R.id.addbutton);
        track = findViewById(R.id.trackbutton);
        setting = findViewById(R.id.settingbutton);

        home.setImageResource(R.drawable.home);
        calendar.setImageResource(R.drawable.calender);
        add.setImageResource(R.drawable.add);
        track.setImageResource(R.drawable.completion);
        setting.setImageResource(R.drawable.setting);

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
                Intent intent = new Intent(this, homepage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        calendar.setOnClickListener(v -> {
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
