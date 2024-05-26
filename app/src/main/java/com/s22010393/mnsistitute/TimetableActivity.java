package com.s22010393.mnsistitute;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class TimetableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);




        Button btnPrevious = findViewById(R.id.btnPrev);
        Button btnAddTimeSlot = findViewById(R.id.addTimeSlot);

        btnAddTimeSlot.setOnClickListener(v -> {
            startActivity(new Intent(TimetableActivity.this, AddClassTimeSlotActivity.class));
        });

        btnPrevious.setOnClickListener(v -> {
            finish();
        });
    }
}