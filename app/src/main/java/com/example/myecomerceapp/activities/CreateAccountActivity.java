package com.example.myecomerceapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myecomerceapp.R;
import com.example.myecomerceapp.models.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CreateAccountActivity extends AppCompatActivity {

    EditText emailEt;
    EditText passwordEt;
    EditText confirmPasswordEt;
    TextView signInTv;
    Button signUpBtn;
    FirebaseAuth mAuth;
    String email;
    String password;
    public static User user;

    private static final int RC_SIGN_IN = 123;

    private Button googleSignInBtn;

    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseUser currentUser;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_activity);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        //Variables

        emailEt =findViewById(R.id.emailet);
        passwordEt =findViewById(R.id.passwordet);
        confirmPasswordEt =findViewById(R.id.confirmpasswordet);
        signInTv =findViewById(R.id.signintv);
        signUpBtn=findViewById(R.id.signupbtn);


        signInTv.setOnClickListener(v -> startActivity(new Intent(CreateAccountActivity.this,LoginActivity.class)));

        signUpBtn.setOnClickListener(v -> signUpWithEmail());

    }

    private void signUpWithEmail() {


            String emailText = emailEt.getEditableText().toString();
            String passwordText = passwordEt.getEditableText().toString();
            String confirmPasswordText = confirmPasswordEt.getEditableText().toString();


            if (emailText.isEmpty() || passwordText.isEmpty()) {

                Toast.makeText(CreateAccountActivity.this, "Email and password are required.", Toast.LENGTH_SHORT).show();
            } else if (!confirmPasswordText.equals(passwordText)) {

                Toast.makeText(CreateAccountActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
            } else if (isValidEmail(emailText)) {

                email = emailText;
                try {
                    password = encryptPassword(passwordText);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }

                // Create user in Firebase Authentication
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                currentUser = mAuth.getCurrentUser();
                                updateUI(currentUser);
                            } else {
                                // Handle authentication failure
                                Toast.makeText(CreateAccountActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        });

            }

    }

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign-In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign-In failed, update UI appropriately
                Toast.makeText(this, "Google sign in failed.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                       currentUser = mAuth.getCurrentUser();
                        updateUI(currentUser);

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(CreateAccountActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "Welcome, " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(CreateAccountActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Finish the current activity to prevent returning back to the login screen
        } else {

            Toast.makeText(this, "Please sign in", Toast.LENGTH_SHORT).show();
        }
    }
}
