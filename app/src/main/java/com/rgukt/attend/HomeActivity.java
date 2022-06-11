package com.rgukt.attend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    private Button attendance, periods, settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        attendance = findViewById(R.id.btn_attendence);
        periods = findViewById(R.id.btn_periods);
        settings = findViewById(R.id.btn_settings);

        attendance.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this,MainActivity.class)));
        periods.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this,PeriodsActivity.class)));
        settings.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this,SettingsActivity.class)));

    }
}