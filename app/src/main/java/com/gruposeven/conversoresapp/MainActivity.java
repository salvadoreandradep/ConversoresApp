package com.gruposeven.conversoresapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

        TabHost tbthost;

        Button btnconvertir;

        TextView temp;

        Spinner spn;

        conversor miConversor = new conversor();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tbthost= (TabHost) findViewById(R.id.tabhost);
        tbthost.setup();

       TabHost.TabSpec tab1 = tbthost.newTabSpec("tab1");
       tab1.setIndicator("Conversor");
       tab1.setContent(R.id.tab1);

        TabHost.TabSpec tab2 = tbthost.newTabSpec("tab2");
        tab2.setIndicator("Lectura");
        tab2.setContent(R.id.tab2);

        tbthost.addTab(tab1);
        tbthost.addTab(tab2);


        btnconvertir = (Button) findViewById(R.id.btnconvert);
        btnconvertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                temp = (TextView) findViewById(R.id.txtcantidad);
                double cantidad = Double.parseDouble(temp.getText().toString());

                spn =findViewById(R.id.spn1);
                int de = spn.getSelectedItemPosition();

                spn =findViewById(R.id.spn2);
                int a = spn.getSelectedItemPosition();

                temp = findViewById(R.id.txtresultado);
                temp.setText("Resultado"+ miConversor.conversor(0,de, a, cantidad));

            }
        });






    }
}

class conversor{
    double [][] superficie = {
        {1, 0.1111, 0.111111, 0.09290304, 0.0001477465648855, 0.00001317, 9.2903e-6}

    };
    public double conversor(int opcion, int de, int a, double cantidad){
        return superficie[opcion][a]/ superficie[opcion][de]* cantidad;
    }



}

//Salvador Ernesto Andrade Peña
//Maydelin Alicia Guevara Perdomo
//Maria Estefany Salgado Osorio
//Emilio José Urias Reyes