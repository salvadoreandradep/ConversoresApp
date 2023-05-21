package com.gruposeven.conversoresapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    Button buttonLogout;
    FirebaseAuth firebaseAuth;
    TextView profile;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        firebaseAuth = FirebaseAuth.getInstance();

        profile= findViewById(R.id.txtprofile);
        buttonLogout = findViewById(R.id.buttonLogout);



        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(HomeActivity.this, UserProfileActivity.class);
                startActivity(profile);
            }
        });
        buttonLogout.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                firebaseAuth.signOut();
                Intent Home = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(Home);
                finish();


            }
        });


    }
}