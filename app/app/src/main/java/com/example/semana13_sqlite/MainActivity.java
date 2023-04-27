package com.example.semana13_sqlite;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton btnFloat;
    DB miBD;
    ListView ltsProducto;
    Cursor datosProductosCursor = null;
    ArrayList<Productos> productArrayList =new ArrayList<Productos>();
    ArrayList<Productos> productArrayListCopy =new ArrayList<Productos>();
    Productos misProductos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFloat = findViewById(R.id.btnAgregarAmigos);
        btnFloat.setOnClickListener(v->{
           agregarProductos("nuevo", new String[]{});
        });
        obtenerDatosProductos();
        buscarRepuestos();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_repuestos, menu);

        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo)menuInfo;
        datosProductosCursor.moveToPosition(adapterContextMenuInfo.position);
        menu.setHeaderTitle(datosProductosCursor.getString(1));
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        try {
            switch (item.getItemId()) {
                case R.id.mnxAgregar:
                    agregarProductos("nuevo", new String[]{});
                    break;
                case R.id.mnxModificar:
                    String[] datos = {
                            datosProductosCursor.getString(0),//idProducto
                            datosProductosCursor.getString(1),//nombre
                            datosProductosCursor.getString(2),//categoria
                            datosProductosCursor.getString(3),//precio
                            datosProductosCursor.getString(4), //marca
                            datosProductosCursor.getString(5) //url photo
                    };
                    agregarProductos("modificar", datos);
                    break;
                case R.id.mnxEliminar:
                    eliminarProducto();
                    break;
            }
        }catch (Exception ex){
            mostrarMsgToask(ex.getMessage());
        }
        return super.onContextItemSelected(item);
    }
    private void eliminarProducto(){
        try {
            AlertDialog.Builder confirmacion = new AlertDialog.Builder(MainActivity.this);
            confirmacion.setTitle("Esta seguro de eliminar el producto?");
            confirmacion.setMessage(datosProductosCursor.getString(1));
            confirmacion.setPositiveButton("Si", (dialog, which) -> {
                miBD = new DB(getApplicationContext(), "", null, 1);
                datosProductosCursor = miBD.administracion_Productos("eliminar", new String[]{datosProductosCursor.getString(0)});//idAmigo
                obtenerDatosProductos();
                mostrarMsgToask("Eliminaste un producto");
                dialog.dismiss(); //cerrar el cuadro de dialogo
            });
            confirmacion.setNegativeButton("No", (dialog, which) -> {
                mostrarMsgToask("Eliminacion cancelada");
                dialog.dismiss(); //cerrar el cuadro de dialogo
            });
            confirmacion.create().show();
        }catch (Exception ex){
            mostrarMsgToask(ex.getMessage());
        }
    }
    private void buscarRepuestos() {
        TextView tempVal = findViewById(R.id.txtBuscarAmigos);
        tempVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    productArrayList.clear();
                    if(tempVal.getText().toString().trim().length()<1){ //si no esta escribiendo, mostramos todos los registros
                        productArrayList.addAll(productArrayListCopy);
                    }else { //si esta buscando entonces filtramos los datos
                        for (Productos am : productArrayListCopy){
                            String nombre = am.getNombre();
                            String categoria = am.getCategoria();
                            String precio = am.getPrecio();
                            String marca = am.getMarca();

                            String buscando = tempVal.getText().toString().trim().toLowerCase(); //escribe en la caja de texto...

                            if(nombre.toLowerCase().trim().contains(buscando) ||
                                    categoria.trim().contains(buscando) ||
                                    precio.trim().toLowerCase().contains(buscando) ||
                                    marca.trim().toLowerCase().contains(buscando)
                            ){
                                productArrayList.add(am);
                            }
                        }
                    }
                    adaptadorImagenes adaptadorImagenes = new adaptadorImagenes(getApplicationContext(), productArrayList);
                    ltsProducto.setAdapter(adaptadorImagenes);
                }catch (Exception e){
                    mostrarMsgToask(e.getMessage());
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void agregarProductos(String accion, String[] datos){
        try {
            Bundle parametrosProductos = new Bundle();
            parametrosProductos.putString("accion", accion);
            parametrosProductos.putStringArray("datos", datos);

            Intent agregarProductos = new Intent(getApplicationContext(), AgregarProducto.class);
            agregarProductos.putExtras(parametrosProductos);
            startActivity(agregarProductos);
        }catch (Exception e){
            mostrarMsgToask(e.getMessage());
        }
    }
    private void obtenerDatosProductos(){
        miBD = new DB(getApplicationContext(),"",null,1);
        datosProductosCursor = miBD.administracion_Productos("consultar",null);
        if(datosProductosCursor.moveToFirst()){ //si hay datos que mostrar
            mostrarDatosProductos();
        }else{ //sino que llame para agregar nuevos amigos...
            mostrarMsgToask("No hay datos de productos, por favor agregue nuevos");
            agregarProductos("nuevo", new String[]{});
        }
    }
    private void mostrarDatosProductos(){
        ltsProducto = findViewById(R.id.ltsamigos);
        productArrayList.clear();
        productArrayListCopy.clear();
        do{
            misProductos = new Productos(
                    datosProductosCursor.getString(0),//idAmigo
                    datosProductosCursor.getString(1),//nombre
                    datosProductosCursor.getString(2),//telefono
                    datosProductosCursor.getString(3),//direccion
                    datosProductosCursor.getString(4),//email
                    datosProductosCursor.getString(5) //urlPhoto
            );
            productArrayList.add(misProductos);
        }while(datosProductosCursor.moveToNext());
        adaptadorImagenes adaptadorImagenes = new adaptadorImagenes(getApplicationContext(), productArrayList);
        ltsProducto.setAdapter(adaptadorImagenes);

        registerForContextMenu(ltsProducto);

        productArrayListCopy.addAll(productArrayList);
    }
    private void mostrarMsgToask(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }

}

class Productos {
    String idProducto;
    String nombre;
    String categoria;
    String precio;
    String marca;
    String urlImg;

    public Productos(String idProducto, String nombre, String categoria, String precio, String marca, String urlImg) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.marca = marca;
        this.urlImg = urlImg;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }
}