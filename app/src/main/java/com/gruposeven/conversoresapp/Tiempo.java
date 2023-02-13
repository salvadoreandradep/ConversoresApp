package com.gruposeven.conversoresapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class Tiempo extends AppCompatActivity {



    Button btntimer;

    Spinner spntime;

    TextView temptime;

    ConverturT miConversor = new ConverturT();
    Button btmenuT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiempo);



        btmenuT=(Button)findViewById(R.id.txttimeMenu);

        btmenuT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent (Tiempo.this, MainActivity.class);
                startActivity(a);

            }
        });



        btntimer=(Button) findViewById(R.id.tcttimecon);

        btntimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temptime = (TextView) findViewById(R.id.txttimecan);
                double cantidad = Double.parseDouble(temptime.getText().toString());

                spntime = findViewById(R.id.spinnerT);
                int de = spntime.getSelectedItemPosition();

                spntime = findViewById(R.id.spinnerT2);
                int a = spntime.getSelectedItemPosition();

                temptime = findViewById(R.id.txtrestime);
                temptime.setText("Respuesta: "+ miConversor.convertir(0, de, a, cantidad));
            }
        });


    }
}

class ConverturT{
    double[][] Tiempo = {
            {1,0.001,0.0000166667,2.77777778e-7, 1.15740741e-8, 1.65343915e-9, 3.805166667092337265e-10, 3.170975697614998689e-11 },//longitud

    };
    public double convertir(int opcion, int de, int a, double cantidad){
        return Tiempo[opcion][a] / Tiempo[opcion][de] * cantidad;
    }
}