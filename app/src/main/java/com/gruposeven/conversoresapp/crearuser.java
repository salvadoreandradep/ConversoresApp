package com.gruposeven.conversoresapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class crearuser extends AppCompatActivity {


    TextView btnregres;

    Button btncrear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearuser);


        btnregres=findViewById(R.id.btnregresar);
        btnregres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent regresar = new Intent(crearuser.this, MainActivity.class);
                startActivity(regresar);

            }
        });





    }
}