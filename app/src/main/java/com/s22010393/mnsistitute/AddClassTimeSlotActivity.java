package com.s22010393.mnsistitute;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import entities.DayOfWeek;

public class AddClassTimeSlotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class_time_slot);
        Spinner spinnerDays = findViewById(R.id.spinner_days);


        // Convert enum values to a list of strings
        String[] days = new String[DayOfWeek.values().length];
        for (int i = 0; i < DayOfWeek.values().length; i++) {
            days[i] = DayOfWeek.values()[i].name();
        }

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, days);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinnerDays.setAdapter(adapter);
    }
}