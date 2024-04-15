package com.example.myecomerceapp.activities;



import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myecomerceapp.R;
import com.example.myecomerceapp.models.Product;
import com.example.myecomerceapp.models.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class CreateAccountActivity extends AppCompatActivity {

    EditText userNameEt;
    EditText emailEt;
    EditText passwordEt;
    EditText confirmPasswordEt;
    TextView signInTv;
    Button signUpBtn;

    String username;
    String email;
    String password;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_activity);




        //Variables
        userNameEt =findViewById(R.id.usernameet);
        emailEt =findViewById(R.id.usernameet);
        passwordEt =findViewById(R.id.passwordet);
        confirmPasswordEt =findViewById(R.id.confirmpasswordet);
        signInTv =findViewById(R.id.signintv);
        signUpBtn=findViewById(R.id.signupbtn);


        signInTv.setOnClickListener(v -> startActivity(
                new Intent(CreateAccountActivity.this,LoginActivity.class)));
        signUpBtn.setOnClickListener(v -> signUp());

    }

    private void signUp() {

            username= userNameEt.getEditableText().toString();
        String confirmPassword = confirmPasswordEt.getEditableText().toString();


            if (emailEt.getEditableText().toString().isEmpty() || passwordEt.getEditableText().toString().isEmpty()) {

                Toast.makeText(CreateAccountActivity.this, "Email and password are required.", Toast.LENGTH_SHORT).show();
            } else if (!confirmPassword.equals(passwordEt.getEditableText().toString())) {

                Toast.makeText(CreateAccountActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
            } else if (isValidEmail(emailEt.getEditableText().toString())) {

                email = emailEt.getEditableText().toString();
                try {
                    password = encryptPassword(passwordEt.getEditableText().toString());
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }


                User user = setUser();
                updateUI(user);

            }

    }

    @NonNull
    private User setUser() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference parentReference = firebaseDatabase.getReference("MyDatabase");
        DatabaseReference userReference = parentReference.child("users");

        User user = new User();
        HashMap<Product, Integer> products = new HashMap<>();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setProducts(products);

        userReference.setValue(user);
        return user;

    }


    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    public String encryptPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException(e);
        }
        byte [] digest=messageDigest.digest(password.getBytes());

        BigInteger bigInteger=new BigInteger(1,digest);
        return bigInteger.toString(16);
    }



  private void updateUI(User user) {
        if (user!=null) {
            Toast.makeText(this, "Welcome, " + user.getUsername(), Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(CreateAccountActivity.this, MainActivity.class);
            intent.putExtra("username",user.getUsername());
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, "Please Sign please", Toast.LENGTH_SHORT).show();

        }
    }

}
