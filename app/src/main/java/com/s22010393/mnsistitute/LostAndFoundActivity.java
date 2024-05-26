package com.s22010393.mnsistitute;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class LostAndFoundActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_and_found);



        Button btnPrevious = findViewById(R.id.btnPrev);

        btnPrevious.setOnClickListener(v -> {
            finish();
        });

    }
}