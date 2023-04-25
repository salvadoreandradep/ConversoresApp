package com.gruposeven.conversoresapp;

import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
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

    ProgressDialog progreso;
    JSONArray datosJSON;
    JSONObject jsonObject;

    int posicion=0;



    InputStreamReader isReader;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        obtenerDatos myAsync = new obtenerDatos();
        myAsync.execute();

        FloatingActionButton btn = (FloatingActionButton) findViewById(R.id.btnAgregar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parametros.putString("accion", "nuevo");
                nuevo_catalogo();
            }
        });

    }

    public void  nuevo_catalogo(){
        Intent agregar_catalogo = new Intent(MainActivity.this, agregaronline.class);
        agregar_catalogo.putExtras(parametros);
        startActivity(agregar_catalogo);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        try {

            datosJSON.getJSONObject(info.position);
            menu.setHeaderTitle(datosJSON.getJSONObject(info.position).getJSONObject("value").getString("nombre").toString());
            posicion = info.position;

        }catch (Exception e){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }


    }

    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()){

            case R.id.mnxAgregar:
                parametros.putString("accion","nuevo");
                nuevo_catalogo();
                return true;


            case R.id.mnxModificar:
                parametros.putString("accion","modificar");
            try {
                parametros.putString("valores", datosJSON.getJSONObject(posicion).getJSONObject("value").toString());
                nuevo_catalogo();
            }catch (Exception e){
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
            return true;


         case R.id.mnxEliminar:
             JSONObject miData = new JSONObject();
             try {
                 miData.put("_id",datosJSON.getJSONObject(posicion).getJSONObject("value").getString("_id"));
             }catch (Exception e){
                 Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
             }
             eliminarDatos objEliminar = new eliminarDatos();
             objEliminar.execute(miData.toString());
             return true;
        }

        return super.onContextItemSelected(item);



    }









    private class obtenerDatos extends AsyncTask<Void, Void, String> {
        HttpURLConnection urlConnection;

        @Override
        protected String doInBackground(Void... params) {
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
                Log.e("Mi Error", "Error", ex);
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

                ListView lstCatalogo = (ListView) findViewById(R.id.ltscatalogo);

                final ArrayList<String> alAgenda = new ArrayList<String>();
                final ArrayAdapter<String> aaAgenda = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, alAgenda);

             lstCatalogo.setAdapter(aaAgenda);

             for (int i=0; i<datosJSON.length(); i++){
                 alAgenda.add(datosJSON.getJSONObject(i).getJSONObject("value").getString("nombre").toString());

                }
                aaAgenda.notifyDataSetChanged();;
                registerForContextMenu(lstCatalogo);


            } catch (Exception ex) {
                Toast.makeText(MainActivity.this, "Error" + ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
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
