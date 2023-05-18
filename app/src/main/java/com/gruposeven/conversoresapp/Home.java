package com.gruposeven.conversoresapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Home extends AppCompatActivity {



    ImageView users, info, añadir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        users= findViewById(R.id.btnuser);
        users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent users = new Intent(Home.this, com.gruposeven.conversoresapp.users.class);
                startActivity(users);

            }
        });



        info=findViewById(R.id.btninspeccionar);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pro = new Intent(Home.this, products.class);
                startActivity(pro);

            }
        });




    }
}