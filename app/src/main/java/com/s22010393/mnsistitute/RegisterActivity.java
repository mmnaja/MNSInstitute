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
import com.google.firebase.firestore.FirebaseFirestore;

import utils.FirebaseUtils;


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
        EditText txtGrade = findViewById(R.id.inputGrade);
        EditText txtPostalAdd = findViewById(R.id.inputAddress);
        EditText txtUserName = findViewById(R.id.inputUserName);
        EditText txtPassword = findViewById(R.id.inputPassword);

        btnRegister.setOnClickListener(v -> {
            String strFullName = txtFullName.getText().toString().trim();
            String strPhoneNo = txtPhoneNo.getText().toString().trim();
            String strGrade = txtGrade.getText().toString().trim();
            String strPostalAdd = txtPostalAdd.getText().toString().trim();
            String strUserName = txtUserName.getText().toString().trim();
            String strPassword = txtPassword.getText().toString().trim();

            if (strFullName.isEmpty() || strPhoneNo.isEmpty() || strPostalAdd.isEmpty() ||
                    strGrade.isEmpty() || strPassword.isEmpty() || strUserName.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Fill the missing field/s.",
                        Toast.LENGTH_LONG).show();
            } else {
                saveUserData(strFullName, strPhoneNo, strGrade, strPostalAdd, strUserName,
                        strPassword);
            }
        });

        btnLogin.setOnClickListener(v -> startActivity(new Intent(
                RegisterActivity.this, LoginActivity.class)));
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
                    FirebaseUtils DBUtils = new FirebaseUtils();
                    DBUtils.saveUser(strFullName, strGrade, strPhoneNo, strUserName, strPostalAdd, "student");

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