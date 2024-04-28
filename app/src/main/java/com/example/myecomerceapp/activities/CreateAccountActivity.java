package com.example.myecomerceapp.activities;


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
import com.example.myecomerceapp.models.Product;
import com.example.myecomerceapp.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Objects;

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

    private FirebaseAuth mAuth;
    private  User user;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_activity);

        //Variables
        mAuth = FirebaseAuth.getInstance();
        userNameEt =findViewById(R.id.usernameet);
        emailEt =findViewById(R.id.emailet);
        passwordEt =findViewById(R.id.passwordet);
        confirmPasswordEt =findViewById(R.id.confirmpasswordet);
        signInTv =findViewById(R.id.signintv);
        signUpBtn=findViewById(R.id.signupbtn);

        signInTv.setOnClickListener(v -> {
            startActivity(new Intent(CreateAccountActivity.this,LoginActivity.class));
            finish();
        });
        signUpBtn.setOnClickListener(v -> {
            try {
                signUp();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        });

    }

    private void signUp() throws NoSuchAlgorithmException {
        username = userNameEt.getEditableText().toString().trim();
        String confirmPassword = confirmPasswordEt.getEditableText().toString().trim();
        email=emailEt.getEditableText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(passwordEt.getEditableText().toString().trim())||TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(CreateAccountActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(email)) {
            Toast.makeText(CreateAccountActivity.this, "Invalid email address.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!confirmPassword.equals(passwordEt.getEditableText().toString().trim())) {
            Toast.makeText(CreateAccountActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
            return;
        }

        password=encryptPassword(passwordEt.getEditableText().toString().trim());

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(CreateAccountActivity.this, "Registration successful.",
                                Toast.LENGTH_SHORT).show();
                        setUser();
                        updateUI(mAuth.getCurrentUser());
                        // Redirect to log in activity or any other activity
                    } else {
                        // If registration fails, display a message to the user.
                        Toast.makeText(CreateAccountActivity.this, "Registration failed. " + Objects.requireNonNull(task.getException()).getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });



    }


    @NonNull
    private void setUser() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference parentReference = firebaseDatabase.getReference("MyDatabase");
        DatabaseReference usersReference = parentReference.child("users");


         user = new User();
        ArrayList<Product> products = new ArrayList<>();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setProducts(products);
        DatabaseReference userReference = usersReference.child(user.getUsername());

        userReference.setValue(user);


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



  private void updateUI(FirebaseUser user) {
        if (user!=null) {
            Intent intent=new Intent(CreateAccountActivity.this, MainActivity.class);
            intent.putExtra("email",user.getEmail());
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, "Please Signup ", Toast.LENGTH_SHORT).show();

                Log.d("CreateAccountActivity","The user object is null");

        }
    }

}
