package com.gruposeven.conversoresapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.zip.Inflater;

public class Catalogo extends AppCompatActivity {

    Bundle parametros = new Bundle();

    BD db_catalogo;

    ListView lts;

    Cursor cProductos;

    FloatingActionButton btn;

    @Override
    protected void onCreate(Bundle instance) {
        super.onCreate(instance);
        setContentView(R.layout.activity_catalogo);



        btn = findViewById(R.id.btnagragarnew);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void abrirAgregarProductos (Bundle parametros){
        Intent iAgregarPro = new Intent( Catalogo.this, MainActivity.class);
        iAgregarPro.putExtra(parametros);
        startActivity(iAgregarPro);


    }

    public void OnCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mimenu, menu);

    }


}