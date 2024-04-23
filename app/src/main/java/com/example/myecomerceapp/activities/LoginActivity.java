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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";

   TextView signUpTv;
    private EditText usernameEditText;
    private EditText passwordEditText;
     Button signInBtn;

    private FirebaseAuth mAuth;

    private FirebaseUser currentUser;


    String finalPassword;
    private  User user;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        mAuth = FirebaseAuth.getInstance();

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
        Query checkUser = userRef.orderByChild(EMAIL).equalTo(username);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        user = userSnapshot.getValue(User.class);
                        if (user != null && user.getPassword().equals(finalPassword)) {
                            saveUserDetails(username, finalPassword);
                            firebaseAuth(user);


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

    public void firebaseAuth(User user){
        mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(LoginActivity.this, "Authentication successful.",
                                Toast.LENGTH_SHORT).show();
                        // Redirect to dashboard or any other activity
                        updateUI(currentUser);
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveUserDetails(String username, String password) {
        SharedPreferences preferences = getSharedPreferences(USER_DETAILS_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(EMAIL, username);
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
        currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }



    private void updateUI(FirebaseUser currentUser) {
       if (currentUser!=null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra(EMAIL,currentUser.getEmail());
            startActivity(intent);
            finish();
        }else{
           Toast.makeText(this, "Please sign In " , Toast.LENGTH_SHORT).show();
        }
    }
}
