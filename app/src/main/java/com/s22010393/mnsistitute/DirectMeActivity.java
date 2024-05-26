package com.s22010393.mnsistitute;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class DirectMeActivity extends AppCompatActivity implements LocationListener {
    private static final String TAG = "LocationValues";
    Context mContext;
    List<Address> addressList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_me);

        EditText inputTxtFrom = findViewById(R.id.inputFrom);
        Button btnRout = findViewById(R.id.btnShowRoute);

        btnRout.setOnClickListener(v -> {
            String txtFrom = inputTxtFrom.getText().toString();
            if (txtFrom.equals("")) {
                Toast.makeText(DirectMeActivity.this, "Ware are you from?",
                        Toast.LENGTH_SHORT).show();
            } else {
                getRoute(txtFrom);
            }
        });

        Button btnPrevious = findViewById(R.id.btnPrev);

        btnPrevious.setOnClickListener(v -> {
            finish();
        });

    }

    private void getRoute(String txtFrom) {
        String endingPoint = "MNS Institute";
        try {
            //Creating the Uri with start and end palaces.
            Uri uri = Uri.parse("https://www.google.com/maps/dir/" +
                    txtFrom + "/" + endingPoint);
            Log.i(TAG, uri.toString());
            //Create the Intent using uri.
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            //Set intent package to google maps.
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            //Start the intent and make it visible.
            startActivity(intent);

        } catch (Exception ex) {
            //If google map is not available, redirect the user to google play google maps page.
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?" +
                    "id=com.google.android.apps.maps&hl=en&gl=US");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Log.i(TAG, "Lati: " + location.getLatitude() + " :: " + "Long" +
                location.getLongitude());
        try {
            Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
            addressList = geocoder.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);
            assert addressList != null;
            if (addressList.isEmpty()) {
                Log.e(TAG, "Couldn't find the location.");
            }
        } catch (Exception e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
        }
    }
}