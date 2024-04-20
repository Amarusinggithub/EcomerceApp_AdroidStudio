package com.example.myecomerceapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myecomerceapp.R;
import com.example.myecomerceapp.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final String USER_DETAILS_PREF = "user_details";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private TextView signUpTv;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button signInBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        signUpTv = findViewById(R.id.signuptv);
        usernameEditText = findViewById(R.id.usernameet);
        passwordEditText = findViewById(R.id.passwordet);
        signInBtn = findViewById(R.id.signinbtn);

        signInBtn.setOnClickListener(v -> signIn());
        signUpTv.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class)));
    }

    private void signIn() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String finalPassword;

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            finalPassword = encryptPassword(password);
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "Encryption failed", e);
            Toast.makeText(LoginActivity.this, "An error occurred, please try again later.", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("MyDatabase").child("users");
        Query checkUser = userRef.orderByChild(USERNAME).equalTo(username);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        User user = userSnapshot.getValue(User.class);
                        if (user != null && user.getPassword().equals(finalPassword)) {
                            saveUserDetails(username, finalPassword);
                            updateUI(user);
                            return;
                        }
                    }
                    Toast.makeText(LoginActivity.this, "Incorrect password. Please try again.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Username not found. Please try again or create an account.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginActivity.this, "Error retrieving user data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUserDetails(String username, String password) {
        SharedPreferences preferences = getSharedPreferences(USER_DETAILS_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USERNAME, username);
        editor.putString(PASSWORD, password);
        editor.apply();
    }

    private String encryptPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] digest = messageDigest.digest(password.getBytes());
        return new java.math.BigInteger(1, digest).toString(16);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String username = getUsernameFromPreferences();
        if (!TextUtils.isEmpty(username)) {
            checkUserInDatabase(username);
        } else {
            Toast.makeText(this, "Please sign in", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "No user signed in");
        }
    }

    private void checkUserInDatabase(String username) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("MyDatabase").child("users");
        Query checkUser = userRef.orderByChild(USERNAME).equalTo(username);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    handleUserSnapshot(snapshot, username);
                } else {
                    handleUserNotFound();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginActivity.this, "Error retrieving user data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleUserSnapshot(DataSnapshot snapshot, String username) {
        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
            User user = userSnapshot.getValue(User.class);
            if (user != null && user.getUsername().equals(username)) {
                updateUI(user);
                return;
            }
        }
    }

    private void handleUserNotFound() {
        Toast.makeText(LoginActivity.this, "Username not found. Please try again or create an account.", Toast.LENGTH_SHORT).show();
    }


    private String getUsernameFromPreferences() {
        SharedPreferences preferences = getSharedPreferences(USER_DETAILS_PREF, MODE_PRIVATE);
        return preferences.getString(USERNAME, "");
    }

    private void updateUI(User user) {
        if (user != null) {
            Toast.makeText(this, "Welcome, " + user.getUsername(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra(USERNAME, user.getUsername());
            startActivity(intent);
            finish();
        }
    }
}
