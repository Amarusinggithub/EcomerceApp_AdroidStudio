package com.example.myecomerceapp.activities;



import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        // Check if username already exists
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("MyDatabase").child("users").child(username);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Toast.makeText(CreateAccountActivity.this, "Username already exists. Please choose a different one.", Toast.LENGTH_SHORT).show();
                } else {

                    User user = setUser();
                    updateUI(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CreateAccountActivity.this, "Error checking username availability.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @NonNull
    private User setUser() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference parentReference = firebaseDatabase.getReference("MyDatabase");
        DatabaseReference usersReference = parentReference.child("users");


        User user = new User();
        HashMap<Product, Integer> products = new HashMap<>();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setProducts(products);
        DatabaseReference userReference = usersReference.child(user.getUsername());

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
