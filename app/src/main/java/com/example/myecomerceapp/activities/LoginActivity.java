package com.example.myecomerceapp.activities;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
import java.util.Objects;

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



        signInBtn.setOnClickListener(v ->signIn() );
        signUpTv.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class)));
    }

    private void signIn() {

        username= usernameEt.getEditableText().toString();



        if (username.isEmpty() || passwordEt.getEditableText().toString().isEmpty()) {

            Toast.makeText(LoginActivity.this, "Email and password are required.", Toast.LENGTH_SHORT).show();
        } else  {

            checkDatabaseForUser();

        }

    }

    private void checkDatabaseForUser() {

        try {
            password = encryptPassword(passwordEt.getEditableText().toString());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference parentReference = firebaseDatabase.getReference("MyDatabase");
        DatabaseReference userReference = parentReference.child("users");
        Query checkUserInDatabase=userReference.orderByChild("username").equalTo(username);

        checkUserInDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    user=snapshot.getValue(User.class);
                    if(user!=null){
                        if(Objects.equals(password, user.getPassword())){
                            updateUI(user);
                        }
                    }else{
                        Log.d(TAG, "User is null");
                    }

                }else {
                    Log.e(TAG,"Snapshot does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG,error.getDetails());
            }
        });
        updateUI(user);
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

        }
    }


}
