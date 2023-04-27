package com.gruposeven.conversoresapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Bundle parametros = new Bundle();
    String accion="nuevo";
    String id="";
    String rev="";
    String idUnico;

    TextView temp;
    JSONArray datosJSON;
    JSONObject jsonObject;

    int posicion=0;



    InputStreamReader isReader;
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


        obtenerDatos myAsync = new obtenerDatos();
        myAsync.execute();

        FloatingActionButton btn = (FloatingActionButton) findViewById(R.id.btnAgregarAmigos);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parametros.putString("accion", "nuevo");
                nuevo_catalogo();
            }
        });




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
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        try {

            datosJSON.getJSONObject(info.position);
            menu.setHeaderTitle(datosJSON.getJSONObject(info.position).getJSONObject("value").getString("nombre").toString());
            posicion = info.position;

        }catch (Exception e){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
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

    public void  nuevo_catalogo(){
        Intent agregar_catalogo = new Intent(MainActivity.this, AgregarProducto.class);
        agregar_catalogo.putExtras(parametros);
        startActivity(agregar_catalogo);
    }



    private class obtenerDatos extends AsyncTask<String, Void, String> {
        HttpURLConnection urlConnection;

        @Override
        protected String doInBackground(String... voids) {
            StringBuilder result = new StringBuilder();

            try {
                URL url = new URL("http://192.168.100.3:5984/db_catalogo/_design/catalogo/_view/mi_catalogo");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                InputStream in = new BufferedInputStream(urlConnection.getErrorStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null)
                {
                    result.append(line);
                }

            } catch (Exception ex) {
                Log.e("Mi Error", "Error",ex);
                ex.printStackTrace();

            } finally {
                urlConnection.disconnect();
            }


            return result.toString();
        }


        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                jsonObject = new JSONObject(s);
                datosJSON = jsonObject.getJSONArray("rows");

                ListView lstCatalogo = (ListView) findViewById(R.id.ltsamigos);

                final ArrayList<String> alAgenda = new ArrayList<String>();
                final ArrayAdapter<String> aaAgenda = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, alAgenda);

                lstCatalogo.setAdapter(aaAgenda);

                for (int i=0; i<datosJSON.length(); i++){
                    alAgenda.add(datosJSON.getJSONObject(i).getJSONObject("nombre").getString("value").toString());

                }
                aaAgenda.notifyDataSetChanged();;
                registerForContextMenu(lstCatalogo);


            } catch (Exception ex) {
                Toast.makeText(MainActivity.this, "Error " + ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }

        }

    }

    private class eliminarDatos extends AsyncTask<String, String, String>{
        HttpURLConnection urlConnection;

        @Override
        protected String doInBackground(String... params) {
            StringBuilder result = new StringBuilder();


            String JsonResponse = null;
            String JsonDATA = params[0];
            BufferedReader reader = null;

            try{
                String uri = "http://192.168.100.3:5984/db_catalogo/"+
                        datosJSON.getJSONObject(posicion).getJSONObject("value").getString("_id")+ "?rev="+ datosJSON.getJSONObject(posicion).getJSONObject("value").getString("_rev");
                URL url = new URL(uri);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("DELETE");

                InputStream in = new BufferedInputStream(urlConnection.getErrorStream());
                reader = new BufferedReader(new InputStreamReader(in));
                String line ;
                while ((line = reader.readLine()) !=null){
                    result.append(line);

                }



            } catch (Exception ex){

                Log.e("Mi Error", "Error", ex);
                ex.printStackTrace();

            }finally {
                urlConnection.disconnect();
            }
            return result.toString();
        }


        protected void onPostExecute(String s){
            super.onPostExecute(s);

            try {
                JSONObject jsonObject1 = new JSONObject(s);

                if (jsonObject1.getBoolean("ok")){
                    Toast.makeText(MainActivity.this, "Registro Eliminado", Toast.LENGTH_SHORT).show();
                    Intent regresar = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(regresar);
                }else {
                    Toast.makeText(MainActivity.this, "Error al Eliminar Registro...", Toast.LENGTH_SHORT).show();
                }

            }catch (Exception ex){
                Toast.makeText(MainActivity.this, "Error al enviar a la red", Toast.LENGTH_SHORT).show();
            }

        }
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