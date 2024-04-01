package com.example.myecomerceapp.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myecomerceapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {
    TextView signUpTv;
    EditText emailEt,passwordEt;
    Button signInbtn;

    static FirebaseAuth mAuth;

    String email,password;

    public FirebaseUser user;

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
        signInbtn=findViewById(R.id.signinbtn);


        signInbtn.setOnClickListener(v -> {

            if (isValidEmail(emailEt.getEditableText().toString())){
                email=emailEt.getEditableText().toString();
                try {
                    password=encryotPassword(passwordEt.getEditableText().toString());
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
            }else {

            }


            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    });
        });

    }

    private String encryotPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md= MessageDigest.getInstance("SHA-256");
        byte[] messageDigest =md.digest(password.getBytes());
        BigInteger bigInteger=new BigInteger(1,messageDigest);

        return bigInteger.toString(16);
    }

    public final static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    protected void onStart() {
        super.onStart();
         user = mAuth.getCurrentUser();
    }
}
