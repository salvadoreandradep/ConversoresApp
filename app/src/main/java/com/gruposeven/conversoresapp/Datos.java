package com.gruposeven.conversoresapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class Datos extends AppCompatActivity {


    Button dataconvert;
    Button datamenu;

    Spinner spdata;

    TextView datatext;

    convertdata miConversor = new convertdata();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);

        dataconvert=(Button)findViewById(R.id.btdatacont);
        dataconvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datatext = (TextView) findViewById(R.id.txtdatacon);
                double cantidad = Double.parseDouble(datatext.getText().toString());

                spdata = findViewById(R.id.spinnerD3);
                int de = spdata.getSelectedItemPosition();

                spdata = findViewById(R.id.spinnerD4);
                int a = spdata.getSelectedItemPosition();

                datatext = findViewById(R.id.txtdatares);
                datatext.setText("Respuesta: "+ miConversor.convertir(0, de, a, cantidad));

            }
        });


        datamenu=(Button)findViewById(R.id.btbadaa);
        datamenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent dd = new Intent (Datos.this, MainActivity.class);
                startActivity(dd);

            }
        });




    }
}

class convertdata{
    double[][] data = {
            { 1, 0.001, 0.000125, 0.000976563, 1.000000512, 1.25000064, 9.536748046875, 1.000000512, 1.25000064, 9.313230514526, 1.00000051199996071, 1.250000639999950887, 9.094951674341797576},//datos

    };
    public double convertir(int opcion, int de, int a, double cantidad){
        return data[opcion][a] / data[opcion][de] * cantidad;
    }



}