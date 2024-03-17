package com.example.myecomerceapp.activitys;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myecomerceapp.R;

public class LoginActivity extends AppCompatActivity {
    TextView signUpTv;
    EditText emailEt,passswordEt;
    Button signInbtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        //Variables
        signUpTv=findViewById(R.id.signuptv);
        emailEt=findViewById(R.id.emailet);
        passswordEt=findViewById(R.id.passwordet);
        signInbtn=findViewById(R.id.signinbtn);



    }
}
