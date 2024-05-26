package com.s22010393.mnsistitute;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements SensorEventListener {
    //Firebase variables
    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser user;

    SensorManager sensorManager;
    private boolean isTemperatureSensorAvailable = false;
    private final Locale local = Locale.US;
    TextView txtTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        Button btnManageStudent = findViewById(R.id.manageStudent);
        Button btnManageClass = findViewById(R.id.manageClass);
        Button btnTimetable = findViewById(R.id.timetable);
        Button btnLostAndFound = findViewById(R.id.lostAndFound);
        Button btnMap = findViewById(R.id.map);
        Button btnMessaging = findViewById(R.id.messaging);
        Button btnSettings = findViewById(R.id.settings);
        Button btnLogout = findViewById(R.id.btnLogout);
        Button btnExit = findViewById(R.id.btnExit);
        TextView loggedInUser = findViewById(R.id.loggedInUser);
        txtTemp = findViewById(R.id.temperature);

        //Get the sensor service as sensor manager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //Check the temperature sensor availability
        isTemperatureSensorAvailable ();
        if (isTemperatureSensorAvailable){
            Sensor sensorTemp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            sensorManager.registerListener(this, sensorTemp, SensorManager.SENSOR_DELAY_NORMAL);
            //String tempValue = String.format(local,"%.1f", 0.0) + " C";
            txtTemp.setText("C");
        }

        //setContentView(R.layout.activity_home);
        //Get the user data from Firebase Store
        DocumentReference docRef = db.collection(getString(R.string.app_name)).document(user.getUid());
        docRef.get().addOnSuccessListener(document -> {
            if (document != null) {
                //get the data as cast to hashmap
                Map<String, Object> data = document.getData();
                //get the username field and set text for greet user as the same value inside firestore
                String username = data.get("fullName").toString();
                String welcomeMessage = "Hi " + username;
                Log.i("Auth", welcomeMessage);
                loggedInUser.setText(welcomeMessage);
            }
        });

        btnManageStudent.setOnClickListener(v -> {
            Intent intentManageStudent = new Intent(HomeActivity.this,
                    ManageStudentsActivity.class);
            startActivity(intentManageStudent);
        });

        btnManageClass.setOnClickListener(v -> {
            Intent intentManageClass = new Intent(HomeActivity.this,
                    ManageClassesActivity.class);
            startActivity(intentManageClass);
        });

        btnTimetable.setOnClickListener(v -> {
            Intent intentTimetable = new Intent(HomeActivity.this,
                    TimetableActivity.class);
            startActivity(intentTimetable);
        });

        btnLostAndFound.setOnClickListener(v -> {
            Intent intentLostAndFound = new Intent(HomeActivity.this,
                    LostAndFoundActivity.class);
            startActivity(intentLostAndFound);
        });

        btnMap.setOnClickListener(v -> {
            Intent intentReport = new Intent(HomeActivity.this,
                    DirectMeActivity.class);
            startActivity(intentReport);
        });

        btnMessaging.setOnClickListener(v -> {
            Intent intentMessaging = new Intent(HomeActivity.this,
                    MessagingActivity.class);
            startActivity(intentMessaging);
        });

        btnSettings.setOnClickListener(v -> {
            Intent intentSettings = new Intent(HomeActivity.this,
                    SettingsActivity.class);
            startActivity(intentSettings);
        });

        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(HomeActivity.this, "Sign out successful.",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        });

        btnExit.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this, "Exiting the Application.",
                    Toast.LENGTH_SHORT).show();
            finishAndRemoveTask();
        });
    }

    protected void isTemperatureSensorAvailable () {
        // Get the sensor service as sensor manager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        isTemperatureSensorAvailable = (sensorManager.getDefaultSensor(
                Sensor.TYPE_AMBIENT_TEMPERATURE) != null);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float temperature = event.values[0] > 0 ? event.values[0] : 0.0f;
        String tempValue = String.format(local,"%.1f", temperature) + "C";
        txtTemp.setText(tempValue);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}