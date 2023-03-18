package com.gruposeven.conversoresapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewActivity extends AppCompatActivity {

    EditText txtnombre, txtmarca, txtpres, txtprecio;
    Button btnguardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        txtnombre = findViewById(R.id.txtnombre);
        txtmarca = findViewById(R.id.txtmarca);
        txtpres = findViewById(R.id.txtpres);
        txtprecio = findViewById(R.id.txtprecio);


        btnguardar.findViewById(R.id.btncrearR);

        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbProducto dbProducto = new DbProducto(NewActivity.this);
               long id = dbProducto.insertaProducto(txtnombre.getText().toString(), txtmarca.getText().toString(), txtpres.getText().toString(), txtprecio.toString().toString());

               if (id > 0){
                   Toast.makeText(NewActivity.this, "Producto guardado", Toast.LENGTH_SHORT).show(); limpiar();
               }else {

                   Toast.makeText(NewActivity.this, "Producto No guardado", Toast.LENGTH_SHORT).show(); limpiar();
               }
            }


        });
    }

    private void limpiar (){

        txtnombre.setText("");
        txtmarca.setText("");
        txtprecio.setText("");
        txtpres.setText("");
    }
}