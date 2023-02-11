package com.gruposeven.conversoresapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class Monedas extends AppCompatActivity {

    Button convertirM;

    Spinner spn;

    TextView temp;

    conversores miConversor = new conversores();


    Button Menu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monedas);

        convertirM=(Button)findViewById(R.id.btnconvertmoney);
        convertirM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp = (TextView) findViewById(R.id.txtcantidad);
                double cantidad = Double.parseDouble(temp.getText().toString());

                spn = findViewById(R.id.spn1);
                int de = spn.getSelectedItemPosition();

                spn = findViewById(R.id.spn2);
                int a = spn.getSelectedItemPosition();

                temp = findViewById(R.id.txtrespu);
                temp.setText("Respuesta: "+ miConversor.convertir(0, de, a, cantidad));

            }
        });





        Menu = (Button)findViewById(R.id.btnat);
        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent m = new Intent (Monedas.this, MainActivity.class);
                startActivity(m);
            }
        });

    }
}

class conversores{
    double[][] money = {
            {1, 7.84, 24.63, 36.51, 581.78, 8.75, 0.93, 130.54, 82.52, 0.82},//monedas

    };
    public double convertir(int opcion, int de, int a, double cantidad){
        return money[opcion][a] / money[opcion][de] * cantidad;
    }
}