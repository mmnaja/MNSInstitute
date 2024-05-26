package com.s22010393.mnsistitute;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);


        Button btnPrevious = findViewById(R.id.btnPrev);

        btnPrevious.setOnClickListener(v -> {
            finish();
        });
    }
}