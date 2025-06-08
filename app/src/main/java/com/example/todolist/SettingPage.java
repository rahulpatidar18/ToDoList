package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class SettingPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout); // auto-created layout

        setupBottomNavbar("setting");
    }

    //for click listener on bottom navbar
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
                loadFragment(new SettingFragment());
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
