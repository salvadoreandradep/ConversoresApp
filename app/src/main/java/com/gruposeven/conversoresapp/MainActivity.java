package com.gruposeven.conversoresapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {

    BD db_catalogo;

    String accion = "Nuevo";
    String id="";
    Button btn;
    TextView temp;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btnagregar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardar_catalogo();
            }
        });
        fab = findViewById(R.id.fabagregarpro);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
       mostrar_productos();
    }

    void mostrar_productos (){
        Bundle parametros = getIntent().getExtras();
        accion = parametros.getString("accion");
        if (accion.equals("modificar")){
            String catalogo[] = parametros.getStringArray("catalogo");
            id = catalogo[0];

            temp = findViewById(R.id.txtnombre);
            temp.setText(catalogo[1]);

            temp = findViewById(R.id.txtmarca);
            temp.setText(catalogo[2]);

            temp = findViewById(R.id.txtpres);
            temp.setText(catalogo[3]);

            temp = findViewById(R.id.txtprecio);
            temp.setText(catalogo[4]);


        }
    }

    void guardar_catalogo () {
        try {
            temp = (TextView) findViewById(R.id.txtnombre);
            String producto = temp.getText().toString();

            temp = (TextView) findViewById(R.id.txtmarca);
            String marca = temp.getText().toString();

            temp = (TextView) findViewById(R.id.txtpres);
            String precentacion = temp.getText().toString();

            temp = (TextView) findViewById(R.id.txtprecio);
            String precio = temp.getText().toString();


            db_catalogo = new BD(MainActivity.this, "", null, 1);
            String resultado = db_catalogo.administrar_catalogo(id, producto, marca, precentacion, precio, accion);
            String msg = resultado;
            if (resultado.equals("ok")){
                msg = "Producto guardado";
                regresarlistaproductos();
            }
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error al guardar producto: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
        void regresarlistaproductos(){
            Intent ilistaproducto = new Intent(MainActivity.this, Catalogo.class);
            startActivity(ilistaproducto);

        }
    }
