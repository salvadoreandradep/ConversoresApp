package com.gruposeven.conversoresapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button monedas;
    Button volumen;

    Button longitud;

    Button datostbtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        datostbtn=(Button)findViewById(R.id.btnDatos) ;

        datostbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent d = new Intent(MainActivity.this, Datos.class);
                startActivity(d);
            }
        });



        longitud=(Button)findViewById(R.id.btnlongitud);

        longitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent l = new Intent (MainActivity.this, Longitud.class);
                startActivity(l);

            }
        });




        monedas = (Button)findViewById(R.id.btnmonedas);

        monedas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent m = new Intent (MainActivity.this, Monedas.class);
                startActivity(m);

            }
        });

        volumen = (Button)findViewById(R.id.btnvolumen);

        volumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent v = new Intent (MainActivity.this, Volumen.class);
                startActivity(v);

            }
        });







    }
}