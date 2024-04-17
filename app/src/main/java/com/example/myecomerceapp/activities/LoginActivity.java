package com.example.myecomerceapp.activities;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class LoginActivity extends AppCompatActivity {
    TextView signUpTv;

    EditText passwordEt;
    Button signInBtn;
    EditText usernameEt;
    private String username;
    private User user;
    private String password;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        //Variables
        signUpTv = findViewById(R.id.signuptv);
        usernameEt = findViewById(R.id.usernameet);
        passwordEt = findViewById(R.id.passwordet);
        signInBtn = findViewById(R.id.signinbtn);

        signInBtn.setOnClickListener(v -> {
            try {
                signIn();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        });
        signUpTv.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class));
            finish();
        });
    }

    private void signIn() throws NoSuchAlgorithmException {
         username = usernameEt.getText().toString().trim();
         password = encryptPassword(passwordEt.getText().toString().trim());

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Query database to check for user
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("MyDatabase").child("users");
        Query checkUser = userRef.orderByChild("username").equalTo(username);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                         user = userSnapshot.getValue(User.class);
                        if (user != null && user.getPassword().equals(password)) {
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






    public String encryptPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException(e);
        }
        byte[] digest = messageDigest.digest(password.getBytes());

        BigInteger bigInteger = new BigInteger(1, digest);
        return bigInteger.toString(16);
    }


    @Override
    protected void onStart() {
        super.onStart();
        updateUI(user);

    }

    private void updateUI(User user) {
        if (user!=null) {
            Toast.makeText(this, "Welcome, " + user.getUsername(), Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("username",user.getUsername());
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, "Please Sign please", Toast.LENGTH_SHORT).show();
            Log.d(TAG,"User is null");
        }
    }


}
