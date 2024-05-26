package com.s22010393.mnsistitute;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        Button btnRegister = findViewById(R.id.btnRegister);
        Button btnLogin = findViewById(R.id.btnLogin);
        EditText txtFullName = findViewById(R.id.inputFullName);
        EditText txtPhoneNo =   findViewById(R.id.inputPhoneNo);
        EditText txtEmail = findViewById(R.id.inputEmail);
        EditText txtPostalAdd = findViewById(R.id.inputAddress);
        EditText txtUserName = findViewById(R.id.inputUserName);
        EditText txtPassword = findViewById(R.id.inputPassword);

        btnRegister.setOnClickListener(v -> {
            String strFullName = txtFullName.getText().toString().trim();
            String strPhoneNo = txtPhoneNo.getText().toString().trim();
            String strEmail = txtEmail.getText().toString().trim();
            String strPostalAdd = txtPostalAdd.getText().toString().trim();
            String strUserName = txtUserName.getText().toString().trim();
            String strPassword = txtPassword.getText().toString().trim();

            if (strFullName.isEmpty() || strPhoneNo.isEmpty() || strPostalAdd.isEmpty() ||
                    strEmail.isEmpty() || strPassword.isEmpty() || strUserName.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Fill the missing field/s.",
                        Toast.LENGTH_LONG).show();
            } else {
                saveUserData(strFullName, strPhoneNo, strEmail, strPostalAdd, strUserName,
                        strPassword);
            }
        });

        btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });
    }

    //Saves the user data other than email and password.
    private void saveUserData(String strFullName, String strPhoneNo, String strGrade,
                              String strPostalAdd, String strUserName, String strPassword) {
        auth.createUserWithEmailAndPassword(strUserName, strPassword)
            .addOnCompleteListener(RegisterActivity.this, task -> {
                if (task.isSuccessful()) {
                    db = FirebaseFirestore.getInstance();
                    user = auth.getCurrentUser();

                    Toast.makeText(RegisterActivity.this,
                            "User registering success.", Toast.LENGTH_LONG).show();
                    //Create a new document in the current user's collection.
                    DocumentReference userDocRef = db.collection(getString(R.string.app_name))
                            .document(user.getUid());
                    //Set user data to the hashMap
                    Map<String, Object> userData = new HashMap<>();
                    userData.put("fullName", strFullName);
                    userData.put("grade", strGrade);
                    userData.put("phoneNumber", strPhoneNo);
                    userData.put("postalAdd", strPostalAdd);
                    //Save the data to the user
                    userDocRef.set(userData).addOnCompleteListener(RegisterActivity.this,
                            userTask -> {
                                if (userTask.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this,
                                        "User data saved successfully.", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(RegisterActivity.this,
                                            "User data not saved.", Toast.LENGTH_LONG).show();
                                }
                            });

                    startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this,
                            "User registering Failed.", Toast.LENGTH_LONG).show();
                    Log.i("Auth", task.getException().toString());
                }
            });
    }
}