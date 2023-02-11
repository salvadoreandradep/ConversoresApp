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
            {1,1000,100,0.001,39.3700787402,3.280839895, 1.0936132983,0.006213712,0.0005399568,39370.078740157 },//longitud

    };
    public double convertir(int opcion, int de, int a, double cantidad){
        return Longitud[opcion][a] / Longitud[opcion][de] * cantidad;
    }
}