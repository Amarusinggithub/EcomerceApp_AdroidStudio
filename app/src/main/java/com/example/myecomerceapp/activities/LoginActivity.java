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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myecomerceapp.R;
import com.example.myecomerceapp.models.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class LoginActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 100;
    TextView signUpTv;
    EditText emailEt;
    EditText passwordEt;
    Button signInBtn;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    GoogleSignInClient googleSignInClient;
    SignInButton googleSignInBtn;
    User user;
    String password;
    String email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);




        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //Variables
        googleSignInBtn=findViewById(R.id.google_sign_in);
        signUpTv=findViewById(R.id.signuptv);
        emailEt=findViewById(R.id.emailet);
        passwordEt=findViewById(R.id.passwordet);
        signInBtn=findViewById(R.id.signinbtn);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Initialize sign in client
        googleSignInClient = GoogleSignIn.getClient(LoginActivity.this, googleSignInOptions);

        googleSignInBtn.setOnClickListener(view -> {
            // Initialize sign in intent
            Intent intent = googleSignInClient.getSignInIntent();
            // Start activity for result
            startActivityForResult(intent, REQUEST_CODE);
        });

        signInBtn.setOnClickListener(v -> signInWithEmalAndPassword());

    }

    private void signInWithEmalAndPassword() {

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
                           currentUser= mAuth.getCurrentUser();
                            // Create User instance
                            user = new User();

                            // Update UI with the created User
                            updateUI(currentUser);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    });
        }else {
            Toast.makeText(LoginActivity.this,"Enter a valid Email",Toast.LENGTH_SHORT).show();
        }

        String mCustomToken = FirebaseAuth.getInstance().getUid();

        assert mCustomToken != null;
        mAuth.signInWithCustomToken(mCustomToken)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCustomToken:success");
                        currentUser = mAuth.getCurrentUser();
                        // Extract user information from GoogleSignInAccount
                        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
                        assert googleSignInAccount != null;
                        String firstName = googleSignInAccount.getGivenName();
                        String lastName = googleSignInAccount.getFamilyName();
                         email = googleSignInAccount.getEmail();

                        // You can get the profile image URL using googleSignInAccount.getPhotoUrl()

                        // Create User instance
                        user = new User();

                        // Update UI with the created User
                        updateUI(currentUser);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCustomToken:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                });
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

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        assert googleSignInAccount != null;
        String firstName = googleSignInAccount.getGivenName();
        String lastName = googleSignInAccount.getFamilyName();
         String email = googleSignInAccount.getEmail();

        // You can get the profile image URL using googleSignInAccount.getPhotoUrl()

        // Create User instance
        user = new User();

        currentUser=mAuth.getCurrentUser();

        // Update UI with the created User
        updateUI(currentUser);

    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Toast.makeText(this, "Welcome, " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Finish the current activity to prevent returning back to the login screen
        } else {

            Toast.makeText(this, "Please sign in", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            // When request code is equal to 100 initialize task
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            // check condition
            if (signInAccountTask.isSuccessful()) {
                // When google sign in successful initialize string
                String s = "Google sign in successful";
                // Display Toast
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
                // Initialize sign in account
                try {
                    // Initialize sign in account
                    GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                    // Check condition
                    if (googleSignInAccount != null) {
                        // When sign in account is not equal to null initialize auth credential
                        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                        // Check credential
                        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, task -> {
                            // Check condition
                            if (task.isSuccessful()) {
                                GoogleSignInAccount googleSignInAccount1 = GoogleSignIn.getLastSignedInAccount(LoginActivity.this);
                                assert googleSignInAccount1 != null;
                                String firstName = googleSignInAccount1.getGivenName();
                                String lastName = googleSignInAccount1.getFamilyName();
                                String email = googleSignInAccount1.getEmail();

                                // You can get the profile image URL using googleSignInAccount.getPhotoUrl()

                                // Create User instance
                                user = new User();
                                // Update UI with the created User
                                updateUI(task.getResult().getUser());
                                Toast.makeText(LoginActivity.this, "Firebase authentication successful", Toast.LENGTH_SHORT).show();
                            } else {
                                // When task is unsuccessful display Toast
                                Toast.makeText(LoginActivity.this, "Authentication Failed :" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}




