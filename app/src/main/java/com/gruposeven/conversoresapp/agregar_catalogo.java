package com.gruposeven.conversoresapp;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

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

                TextView temp = (TextView) findViewById(R.id.txtcodigo);
                temp.setText(datosJSON.getString("codigo"));

                temp = (TextView) findViewById(R.id.txtnombre);
                temp.setText(datosJSON.getString("nombre"));

                temp = (TextView) findViewById(R.id.txtdireccion);
                temp.setText(datosJSON.getString("marca"));

                temp = (TextView) findViewById(R.id.txttelefono);
                temp.setText(datosJSON.getString("presentacion"));

                 temp = (TextView) findViewById(R.id.txtdui);
                temp.setText(datosJSON.getString("precio"));

                id = datosJSON.getString("_id");
                rev = datosJSON.getString("_rev");
                
            }catch (Exception e) {
                Toast.makeText(this, "Error al recuperar datos: " + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        Button btn = (Button) findViewById(R.id.btnguardar);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView temp = (TextView) findViewById(R.id.txtcodigo);
                    String codigo = temp.getText().toString();

                    temp = (TextView) findViewById(R.id.txtnombre);
                    String nombre = temp.getText().toString();

                    temp = (TextView) findViewById(R.id.txtdireccion);
                    String direccion = temp.getText().toString();

                    temp = (TextView) findViewById(R.id.txttelefono);
                    String telefono = temp.getText().toString();

                    temp = (TextView) findViewById(R.id.txtdui);
                    String dui = temp.getText().toString();



                    JSONObject miData = new JSONObject();
                    try {
                        if (accion.equals("modificar")){
                            miData.put("_id", id);
                            miData.put("_rev", rev);
                        }
                        miData.put("codigo", codigo);
                        miData.put("nombre", nombre);
                        miData.put("direccion", direccion);
                        miData.put("telefono", telefono);
                        miData.put("dui", dui);

                        enviardatos objenviar = new enviardatos();
                        objenviar.execute(miData.toString());



                    }catch (Exception ex) {
                        Toast.makeText(agregar_catalogo.this, "Error", Toast.LENGTH_SHORT).show();

                    }

                }
            });
            btn.setOnClickListener(view -> {






            });

            FloatingActionButton btnregresar = (FloatingActionButton) findViewById(R.id.btnRegresar);
            btnregresar.setOnClickListener(view1 -> {
                                    Intent r = new Intent(agregar_catalogo.this, MainActivity.class);
                                    startActivity(r);


            });

        }



        }
    private class enviardatos extends AsyncTask<String, String, String>{
        HttpURLConnection urlConnection;
        @Override
        protected String doInBackground(String... params) {
            StringBuilder result = new StringBuilder();

            String JsonResponse = null;
            String JsonDATA = params[0];
            BufferedReader reader = null;

            try {
                URL url = new URL("http://192.168.100.3:5984/db_catalogo/%22");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);

                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");


                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(),"UTF-8"));
                writer.write(JsonDATA);
                writer.close();

                InputStream inputStream = urlConnection.getInputStream();

                if (inputStream == null){

                    return  null;

                }

                reader = new BufferedReader(new InputStreamReader(inputStream));
                resp = reader.toString();
                String inputLine;
                StringBuffer buffer = new StringBuffer();
                while ((inputLine = reader.readLine()) != null );
                buffer.append(inputLine + "\n");
                
                    if (buffer.length() == 0){

                        return  null; 
                    }
                    
                    JsonResponse = buffer.toString();
              
                Log.i(id, JsonResponse);   
                
                return JsonResponse;
                
            }catch (Exception ex){
                ex.printStackTrace();


            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getBoolean("ok")){

                    Toast.makeText(agregar_catalogo.this, "Registro enviado", Toast.LENGTH_SHORT).show();
                    Intent regresar = new Intent(agregar_catalogo.this, MainActivity.class);
                    startActivity(regresar);


                }else {
                    Toast.makeText(agregar_catalogo.this, "Error al intentar almacenar el registro...", Toast.LENGTH_SHORT).show();
                }

            }catch (Exception e) {
                Toast.makeText(agregar_catalogo.this, "Error al enviar a la red" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();

            }
        }
    }
}