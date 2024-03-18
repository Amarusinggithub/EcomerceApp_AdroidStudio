package com.example.myecomerceapp.activitys;

import android.os.Bundle;
import android.view.View;
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

public class LoginActivity extends AppCompatActivity {
    TextView signUpTv;
    EditText emailEt,passwordEt;
    Button signInbtn;

    private FirebaseAuth mAuth;

    String email,password;

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

            email=emailEt.getEditableText().toString();
            password=passwordEt.getEditableText().toString();


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
}
