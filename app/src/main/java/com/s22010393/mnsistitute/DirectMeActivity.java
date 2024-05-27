package com.s22010393.mnsistitute;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;


public class DirectMeActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "LocationValues";
    Context mContext;
    List<Address> addressList;
    GoogleMap myMap;
    private static final LatLng MNSLOC = new LatLng(7.217697, 79.851127);
    FusedLocationProviderClient fusedLocationProviderClient;
    Location currentLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_me);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        EditText inputTxtFrom = findViewById(R.id.inputFrom);
        Button btnRout = findViewById(R.id.btnShowRoute);

        btnRout.setOnClickListener(v -> {
            String txtFrom = inputTxtFrom.getText().toString();
            if (txtFrom.equals("")) {
                //Get the current location and find the rout to the Institute
                getLastLocation();
            } else {
                //Find the route to the Institute from given location
                getRoute(txtFrom);
            }
        });

        Button btnPrevious = findViewById(R.id.btnPrev);

        btnPrevious.setOnClickListener(v -> finish());

    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            // here to request the missing permissions, and then overriding
            ActivityCompat.requestPermissions(DirectMeActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            return;
        }
        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    getRoute(location);
                }
            }
        });
    }
    private void getRoute(Location location) {
        Uri uri = Uri.parse("https://www.google.com/maps/dir/?api=1&origin=" + location.getLatitude()
         + "," + location.getLongitude() + "&destination=" + MNSLOC.latitude + "," + MNSLOC.longitude);
        Log.i(TAG, uri.toString());
        //Create the Intent using uri.
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        //Set intent package to google maps.
        intent.setPackage("com.google.android.apps.maps");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //Start the intent and make it visible.
        startActivity(intent);

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
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;
        LatLng myLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        myMap.addMarker(new MarkerOptions().position(myLocation).title("My Location"));
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(MNSLOC, 14));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0  && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this, "Permission rejected by user", Toast.LENGTH_LONG).show();
            }
        }
    }
}