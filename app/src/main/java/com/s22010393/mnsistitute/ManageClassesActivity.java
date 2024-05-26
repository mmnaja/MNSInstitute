package com.s22010393.mnsistitute;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import utils.FirebaseUtils;

public class ManageClassesActivity extends AppCompatActivity {
    FirebaseUtils dbUtil;
    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_classes);

        //loadClasses();

        dbUtil = new FirebaseUtils();

        Button btnPrevious = findViewById(R.id.btnPrev);
        Button btnAddClass = findViewById(R.id.addClass);
        Button btnManageClass = findViewById(R.id.manageCLass);



        btnManageClass.setOnClickListener(v -> startActivity(
                new Intent(ManageClassesActivity.this, ManageClassActivity.class)));

        btnAddClass.setOnClickListener(v -> startActivity(
                new Intent(ManageClassesActivity.this, ManageClassActivity.class)));

        btnPrevious.setOnClickListener(v -> finish());
    }

    private void loadClasses() {
        //Create a new document in the current user's collection.
        DocumentReference userDocRef = db.collection(getString(R.string.app_name))
                .document(user.getUid());


    }
}