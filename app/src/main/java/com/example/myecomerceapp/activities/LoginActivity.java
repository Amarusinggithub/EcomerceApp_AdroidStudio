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

public class LoginActivity extends AppCompatActivity {
    TextView signUpTv;
    EditText emailEt;
    EditText passwordEt;

    Button signInBtn;

     FirebaseAuth mAuth;

    String email;
    String password;

     FirebaseUser user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //Variables
        signUpTv=findViewById(R.id.signuptv);
        emailEt=findViewById(R.id.emailet);
        passwordEt=findViewById(R.id.passwordet);
        signInBtn=findViewById(R.id.signinbtn);


        signInBtn.setOnClickListener(v -> {

            if (isValidEmail(emailEt.getEditableText().toString())){
                email=emailEt.getEditableText().toString();
                try {
                    password=encryptPassword(passwordEt.getEditableText().toString());
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                user = mAuth.getCurrentUser();
                                updateUI(user);

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        });
            }else {

            }





            String mCustomToken = FirebaseAuth.getInstance().getUid();

            assert mCustomToken != null;
            mAuth.signInWithCustomToken(mCustomToken)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCustomToken:success");
                            user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCustomToken:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    });
        });

    }

    public String encryptPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest =null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException(e);
        }
        byte [] digest=messageDigest.digest(password.getBytes());

        BigInteger bigInteger=new BigInteger(1,digest);
        return bigInteger.toString(16);
    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    protected void onStart() {
        super.onStart();
         user = mAuth.getCurrentUser();
        updateUI(user);

    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Toast.makeText(this, "Welcome, " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("currentUser",(Serializable) user);
            startActivity(intent);
            finish(); // Finish the current activity to prevent returning back to the login screen
        } else {

            Toast.makeText(this, "Please sign in", Toast.LENGTH_SHORT).show();
        }
    }
}
