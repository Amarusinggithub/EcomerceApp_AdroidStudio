package com.amar.myecomerceapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amar.myecomerceapp.R;
import com.amar.myecomerceapp.activities.LoginActivity;
import com.amar.myecomerceapp.activities.MainActivity;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class AccountFragment extends Fragment {
    TextView account;
    TextView notification;
    TextView privacy;
    TextView about;
    ImageView accountNp;
    ImageView notificationNp;
    ImageView privacyNp;
    ImageView aboutNp;
    TextView support;
    ImageView supportNp;

    TextView logOut;
    ImageView logOutNp;
    FirebaseAuth mAuth;

    FirebaseUser currentUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_account, container, false);
        initializeViewElements(view);
        return view;
    }

    private void initializeViewElements(View view) {
        mAuth = FirebaseAuth.getInstance();
        account=view.findViewById(R.id.accountTv);
        notification=view.findViewById(R.id.notificationTv);
        privacy=view.findViewById(R.id.privacyTv);
        about=view.findViewById(R.id.aboutTv);
        support=view.findViewById(R.id.supportTv);
        logOut=view.findViewById(R.id.logoutTv);


        accountNp=view.findViewById(R.id.accountbackbtn);
        notificationNp=view.findViewById(R.id.notificationbackbtn);
        privacyNp=view.findViewById(R.id.privacybackbtn);
        aboutNp=view.findViewById(R.id.aboutbackbtn);
        supportNp=view.findViewById(R.id.supportbackbtn);
        logOutNp=view.findViewById(R.id.logoutbackbtn);

        loadNextPageBtn();
        setupOnclickListiner();
    }

    private void setupOnclickListiner() {
        account.setOnClickListener(v -> {

        });

        accountNp.setOnClickListener(v -> {

        });

        notification.setOnClickListener(v -> {

        });

        notificationNp.setOnClickListener(v -> {

        });

        privacy.setOnClickListener(v -> {

        });

        privacyNp.setOnClickListener(v -> {

        });

        about.setOnClickListener(v -> {

        });

        aboutNp.setOnClickListener(v -> {

        });

        support.setOnClickListener(v -> {

        });

        supportNp.setOnClickListener(v -> {

        });

        logOut.setOnClickListener(v -> {
            currentUser=mAuth.getCurrentUser();
            mAuth.signOut();
            startActivity(new Intent(getContext(), LoginActivity.class));
            MainActivity.finishActivity();
        });

        logOutNp.setOnClickListener(v -> {
            currentUser=mAuth.getCurrentUser();
            mAuth.signOut();
            startActivity(new Intent(getContext(), LoginActivity.class));
            MainActivity.finishActivity();
        });


    }

    private void loadNextPageBtn() {

        Glide.with(this)
                .load(R.drawable.nextpage)
                .fitCenter()
                .into( aboutNp);

        Glide.with(this)
                .load(R.drawable.nextpage)
                .fitCenter()
                .into( accountNp);

        Glide.with(this)
                .load(R.drawable.nextpage)
                .fitCenter()
                .into( privacyNp);

        Glide.with(this)
                .load(R.drawable.nextpage)
                .fitCenter()
                .into( notificationNp);

        Glide.with(this)
                .load(R.drawable.nextpage)
                .fitCenter()
                .into( supportNp);

        Glide.with(this)
                .load(R.drawable.nextpage)
                .fitCenter()
                .into( logOutNp);
    }
}