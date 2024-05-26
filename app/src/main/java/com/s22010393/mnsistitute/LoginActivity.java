package com.s22010393.mnsistitute;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase database reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference appDB = database.getReference("MNSInstituteData");

        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRegister = findViewById(R.id.btnSignUp);

        auth = FirebaseAuth.getInstance();

        //Login to the application
        btnLogin.setOnClickListener(v -> {
            EditText inputUserName = findViewById(R.id.inputUserName);
            EditText inputPassword = findViewById(R.id.inputPassword);
            String userName = inputUserName.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            if (userName.equals("") || password.equals("")) {
                Toast.makeText(LoginActivity.this,
                        "Your credential/s missing.",
                        Toast.LENGTH_SHORT).show();
            } else {
                auth.signInWithEmailAndPassword(userName, password)
                        .addOnCompleteListener(
                                authResult -> {
                                    if (authResult.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this,
                                                "Login successful.",
                                                Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LoginActivity.this,
                                                HomeActivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this,
                                                "Login Failed. Check your credentials.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
            }
        });


        //Navigate to the Signup screen.
        btnRegister.setOnClickListener(v -> {
            Intent intentRegister = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intentRegister);
        });
    }
}