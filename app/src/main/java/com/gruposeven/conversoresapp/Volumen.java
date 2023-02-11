package com.gruposeven.conversoresapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.gruposeven.conversoresapp.R.id;

public class Volumen extends AppCompatActivity {

    Button btConvert;
    Button atas;

    Spinner sp1;



    TextView tep;



    conversor miConversor = new conversor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volumen);


        atas=(Button)findViewById(id.tbatras) ;

        atas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent (Volumen.this, MainActivity.class);
                startActivity(a);
            }
        });


          btConvert=(Button)findViewById(R.id.btconverto);

        btConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tep = (TextView) findViewById(R.id.txtcanti);
                double cantidad = Double.parseDouble(tep.getText().toString());

                sp1 = findViewById(id.spinner);
                int de = sp1.getSelectedItemPosition();

                sp1 = findViewById(id.spinner2);
                int a = sp1.getSelectedItemPosition();

                tep = findViewById(R.id.txtres);
                tep.setText("Respuesta: "+ miConversor.convertir(0, de, a, cantidad));
            }
        });



    }
}

class conversor{
    double[][] volum = {
            {1, 3.51951, 35.195096903053, 0.26417218127411029593, 1000, 56.3121, 67.628, 0.001, 61.0237, 1728 },//volumenes

    };
    public double convertir(int opcion, int de, int a, double cantidad){
        return volum[opcion][a] / volum[opcion][de] * cantidad;
    }
}