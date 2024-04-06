package com.example.myecomerceapp.activities;

import static android.content.ContentValues.TAG;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CreateAccountActivity extends AppCompatActivity {
    EditText firstNameEt;
    EditText lastNameEt;
    EditText emailEt;
    EditText passwordEt;
    EditText confirmPasswordEt;
    TextView signInTv;
    Button signUpBtn;
    FirebaseAuth mAuth;
    FirebaseUser user;

    String email;
    String password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_activity);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        //Variables
        firstNameEt =findViewById(R.id.firstnameet);
        lastNameEt =findViewById(R.id.lastnameet);
        emailEt =findViewById(R.id.emailet);
        passwordEt =findViewById(R.id.passwordet);
        confirmPasswordEt =findViewById(R.id.confirmpasswordet);
        signInTv =findViewById(R.id.signintv);
        signUpBtn=findViewById(R.id.signupbtn);


        signInTv.setOnClickListener(v -> startActivity(new Intent(CreateAccountActivity.this,LoginActivity.class)));

        signUpBtn.setOnClickListener(v -> {

            String emailText = emailEt.getEditableText().toString();
            String passwordText = passwordEt.getEditableText().toString();
            String confirmPasswordText = confirmPasswordEt.getEditableText().toString();

            if (emailText.isEmpty() || passwordText.isEmpty()) {
                // Handle case where email or password is empty
            } else if (!confirmPasswordText.equals(passwordText)) {
                // Handle case where passwords do not match
            } else if (isValidEmail(emailText)) {
                // Proceed with creating user account
                email = emailText;
                try {
                    password = encryptPassword(passwordText);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // Handle authentication failure
                                Toast.makeText(CreateAccountActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });


            }


        });
    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public String encryptPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest= null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException(e);
        }
        byte [] digest=messageDigest.digest(password.getBytes());

        BigInteger bigInteger=new BigInteger(1,digest);
        return bigInteger.toString(16);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Toast.makeText(this, "Welcome, " + user.getEmail(), Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(CreateAccountActivity.this, MainActivity.class);
            intent.putExtra("currentUser",(Serializable) user);
            startActivity(intent);
            finish(); // Finish the current activity to prevent returning back to the login screen
        } else {

            Toast.makeText(this, "Please sign in", Toast.LENGTH_SHORT).show();
        }
    }
}
