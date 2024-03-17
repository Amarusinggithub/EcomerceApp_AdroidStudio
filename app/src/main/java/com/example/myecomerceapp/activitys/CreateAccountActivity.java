package com.example.myecomerceapp.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myecomerceapp.R;

public class CreateAccountActivity extends AppCompatActivity {
    EditText usernameet,emailet,passwordet,confirmpasswordet;
    TextView forgetPasswordtv,signIntv;
    Button signUpBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_activity);

        //Variables
        usernameet=findViewById(R.id.usernameet);
        emailet=findViewById(R.id.emailet);
        passwordet=findViewById(R.id.passwordet);
        confirmpasswordet=findViewById(R.id.confirmpasswordet);
        forgetPasswordtv=findViewById(R.id.forgetpasswordtv);
        signIntv=findViewById(R.id.signintv);

        signIntv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateAccountActivity.this,MainActivity.class));

            }
        });
    }
}
