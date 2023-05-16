package com.gruposeven.conversoresapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    TextView btntext, btntex2;
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btntext= findViewById(R.id.btnregister);
        btntext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(MainActivity.this, crearuser.class);
                startActivity(register);

            }
        });

        btntex2= findViewById(R.id.btnrecuperar);
        btntex2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recuperar = new Intent(MainActivity.this, recuperar.class);
                startActivity(recuperar);

            }
        });


    }
}