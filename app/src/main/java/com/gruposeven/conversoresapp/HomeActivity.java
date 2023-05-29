package com.gruposeven.conversoresapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.gruposeven.conversoresapp.SQLite.MainActivity;

public class HomeActivity extends AppCompatActivity {


    FirebaseAuth firebaseAuth;
    TextView profile;


   TextView buttonLogout;









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);








        firebaseAuth = FirebaseAuth.getInstance();

        profile= findViewById(R.id.txtprofile);
        buttonLogout = findViewById(R.id.buttonLogout);

        buttonLogout.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {

                Intent Home = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(Home);
                firebaseAuth.signOut();
                finish();


            }
        });



        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(profile);
            }
        });



    }
}