package com.gruposeven.conversoresapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class Longitud extends AppCompatActivity {


    Button btL;

    Button btall;

    Spinner spL;

    TextView temL;

    ConverturL miConversor = new ConverturL();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_longitud);

        btall=(Button)findViewById(R.id.btnaL);

        btall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent (Longitud.this, MainActivity.class);
                startActivity(a);

            }
        });



        btL=(Button) findViewById(R.id.btnconL);

        btL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temL = (TextView) findViewById(R.id.txtcantL);
                double cantidad = Double.parseDouble(temL.getText().toString());

                spL = findViewById(R.id.spinnerL3);
                int de = spL.getSelectedItemPosition();

                spL = findViewById(R.id.spinnerL4);
                int a = spL.getSelectedItemPosition();

                temL = findViewById(R.id.txtresL);
                temL.setText("Respuesta: "+ miConversor.convertir(0, de, a, cantidad));
            }
        });



    }
}

class ConverturL{
    double[][] Longitud = {
            { 1, 0.1, 0.0001, 0.000001, 0.39370, 0.003280, 0.001093, 6.21371192e-7, 5.39956803e-7, 39.37007},//longitud

    };
    public double convertir(int opcion, int de, int a, double cantidad){
        return Longitud[opcion][a] / Longitud[opcion][de] * cantidad;
    }
}