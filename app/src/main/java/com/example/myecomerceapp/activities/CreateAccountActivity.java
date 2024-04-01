package com.example.myecomerceapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myecomerceapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateAccountActivity extends AppCompatActivity {
    EditText usernameet,emailet,passwordet,confirmpasswordet;
    TextView forgetPasswordtv,signIntv;
    Button signUpBtn;
    private FirebaseAuth mAuth;

    String email,password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_activity);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        //Variables
        usernameet=findViewById(R.id.usernameet);
        emailet=findViewById(R.id.emailet);
        passwordet=findViewById(R.id.passwordet);
        confirmpasswordet=findViewById(R.id.confirmpasswordet);
        forgetPasswordtv=findViewById(R.id.forgetpasswordtv);
        signIntv=findViewById(R.id.signintv);

        signUpBtn=findViewById(R.id.signupbtn);

        signIntv.setOnClickListener(v -> {
            startActivity(new Intent(CreateAccountActivity.this,LoginActivity.class));

        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=emailet.getEditableText().toString();
                password=passwordet.getEditableText().toString();


                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();

                            } else {


                                Toast.makeText(CreateAccountActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        });

            }
        });
    }
}
