package com.gruposeven.conversoresapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

public class agregar_catalogo extends AppCompatActivity {
    String accion = "nuevo";
    String id = "";
    String rev = "";
    JSONObject datosJSON;
    String resp;

    TextView temp;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_catalogo);

        Bundle parametros = getIntent().getExtras();
        accion = parametros.getString("accion");

        if (accion.equals("modificar")){
            try {
                datosJSON = new JSONObject(parametros.getString("valores"));

                TextView temp = (TextView) findViewById(R.id.)
            }catch (Exception e) {


            }


        }
    }
}