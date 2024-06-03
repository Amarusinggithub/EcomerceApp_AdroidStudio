package com.amar.myecomerceapp.activities;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amar.myecomerceapp.R;
import com.amar.myecomerceapp.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final String EMAIL = "email";

    TextView signUpTv;
    EditText usernameEditText;
    EditText passwordEditText;
    Button signInBtn;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    String finalPassword;
    User user;
    FirebaseFirestore db;
    private boolean isPasswordVisible=false;


    @SuppressLint("ClickableViewAccessibility")
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

        passwordEditText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                Drawable drawableEnd = passwordEditText.getCompoundDrawables()[2];
                if (drawableEnd != null && event.getRawX() >= (passwordEditText.getRight() - drawableEnd.getBounds().width())) {

                    if (isPasswordVisible) {
                        passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    } else {
                        passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }
                    isPasswordVisible = !isPasswordVisible;
                    passwordEditText.setSelection(passwordEditText.getText().length());
                    return true;
                }
            }
            return false;
        });
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


        db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                             user = document.toObject(User.class);
                            if (user.getPassword().equals(finalPassword)) {
                                firebaseAuth(user);
                                return;
                            }
                        }
                        Toast.makeText(LoginActivity.this, "Incorrect password. Please try again.", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                        Toast.makeText(LoginActivity.this, "Error retrieving user data.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void firebaseAuth(User user){
        mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {

                        Toast.makeText(LoginActivity.this, "Authentication successful.",
                                Toast.LENGTH_SHORT).show();

                        currentUser = mAuth.getCurrentUser();
                        updateUI(currentUser);
                    } else {

                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
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
