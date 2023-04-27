package com.gruposeven.conversoresapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AgregarProducto extends AppCompatActivity {



    String id = "";
    String rev = "";
    JSONObject datosJSON;
    String resp;
    FloatingActionButton btnAtras;
    ImageView imgFotoAmigo;
    Intent tomarFotoIntent;
    String urlCompletaImg, idProducto, accion="nuevo";
    Button btn;
    DB miBD;
    TextView tempVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_repuesto);
        btn = findViewById(R.id.btnSaveRep);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                TextView temp = (TextView) findViewById(R.id.txtNombre);
                String nombre2 = temp.getText().toString();

                temp = (TextView) findViewById(R.id.txtCategoria);
                String direccion2 = temp.getText().toString();

                temp = (TextView) findViewById(R.id.txtMarca);
                String telefono2 = temp.getText().toString();

                temp = (TextView) findViewById(R.id.txtPrecio);
                String dui2 = temp.getText().toString();



                JSONObject miData = new JSONObject();
                try {
                    if (accion.equals("modificar")){
                        miData.put("_id", id);
                        miData.put("_rev", rev);
                    }

                    miData.put("nombre", nombre2);
                    miData.put("direccion", direccion2);
                    miData.put("telefono", telefono2);
                    miData.put("dui", dui2);

                    AgregarProducto.enviardatos objenviar = new enviardatos();
                    objenviar.execute(miData.toString());



                }catch (Exception ex) {
                    Toast.makeText(AgregarProducto.this, "Error", Toast.LENGTH_SHORT).show();

                }
            }
        });

        Bundle parametros = getIntent().getExtras();
        accion = parametros.getString("accion");

        if (accion.equals("modificar")){
            try {
                datosJSON = new JSONObject(parametros.getString("valores"));



                TextView temp = (TextView) findViewById(R.id.txtNombre);
                temp.setText(datosJSON.getString("nombre"));

                temp = (TextView) findViewById(R.id.txtCategoria);
                temp.setText(datosJSON.getString("marca"));

                temp = (TextView) findViewById(R.id.txtMarca);
                temp.setText(datosJSON.getString("presentacion"));

                temp = (TextView) findViewById(R.id.txtPrecio);
                temp.setText(datosJSON.getString("precio"));

                id = datosJSON.getString("_id");
                rev = datosJSON.getString("_rev");

            }catch (Exception e) {
                Toast.makeText(this, "Error al recuperar datos: " + e.getMessage(), Toast.LENGTH_SHORT).show();

            }







            Button btnregresar = (Button) findViewById(R.id.btnSaveRep);
            btnregresar.setOnClickListener(view1 -> {
                Intent r = new Intent(AgregarProducto.this, MainActivity.class);
                startActivity(r);


            });

        }
        miBD = new DB(getApplicationContext(),"",null,1);
        btnAtras = findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(v->{
            mostrarVistaPrincipal();
        });
        imgFotoAmigo = findViewById(R.id.imgFotoAmigo);
        imgFotoAmigo.setOnClickListener(v->{
            tomarFotoAmigo();
        });
        btn = findViewById(R.id.btnSaveRep);
        btn.setOnClickListener(v->{
            tempVal = findViewById(R.id.txtNombre);
            String nombre = tempVal.getText().toString();

            tempVal = findViewById(R.id.txtCategoria);
            String categoria = tempVal.getText().toString();

            tempVal = findViewById(R.id.txtPrecio);
            String precio = tempVal.getText().toString();

            tempVal = findViewById(R.id.txtMarca);
            String marca = tempVal.getText().toString();

            String[] datos = {idProducto, nombre, categoria, precio, marca, urlCompletaImg};
            miBD.administracion_Productos(accion, datos);
            mostrarMsgToast("Registro guardado con exito.");

            mostrarVistaPrincipal();
        });
        mostrarDatosRepuestos();
    }
    private void mostrarDatosRepuestos() {
        try{
            Bundle recibirParametros = getIntent().getExtras();
            accion = recibirParametros.getString("accion");
            if(accion.equals("modificar")){
                String[] datos = recibirParametros.getStringArray("datos");

                idProducto = datos[0];

                tempVal = findViewById(R.id.txtNombre);
                tempVal.setText(datos[1]);

                tempVal = findViewById(R.id.txtCategoria);
                tempVal.setText(datos[2]);

                tempVal = findViewById(R.id.txtPrecio);
                tempVal.setText(datos[3]);

                tempVal = findViewById(R.id.txtMarca);
                tempVal.setText(datos[4]);

                urlCompletaImg = datos[5];
                Bitmap bitmap = BitmapFactory.decodeFile((urlCompletaImg));
                imgFotoAmigo.setImageBitmap(bitmap);
            }
        }catch (Exception e){
            mostrarMsgToast(e.getMessage());
        }
    }
    private void mostrarVistaPrincipal(){
        Intent iprincipal = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(iprincipal);
    }
    private void tomarFotoAmigo(){
        tomarFotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(tomarFotoIntent.resolveActivity(getPackageManager())!=null){
            File photoAmigo = null;
            try{
                photoAmigo = crearImagenAmigo();
            }catch (Exception e){
                mostrarMsgToast(e.getMessage());
            }
            if( photoAmigo!=null ){
                try{
                    Uri uriPhotoAmigo = FileProvider.getUriForFile(AgregarProducto.this, "com.example.semana13_sqlite.fileprovider", photoAmigo);
                    tomarFotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriPhotoAmigo);
                    startActivityForResult(tomarFotoIntent,1);
                }catch (Exception e){
                    mostrarMsgToast(e.getMessage());
                }
            } else {
                mostrarMsgToast("No fue posible tomar la foto");
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            if(requestCode==1 && resultCode==RESULT_OK){
                Bitmap imagenBitmap = BitmapFactory.decodeFile(urlCompletaImg);
                imgFotoAmigo.setImageBitmap(imagenBitmap);
            }
        }catch (Exception e){
            mostrarMsgToast(e.getMessage());
        }
    }
    private File crearImagenAmigo() throws Exception {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nombreImagen = "imagen_"+ timeStamp +"_";
        File dirAlmacenamiento = getExternalFilesDir(Environment.DIRECTORY_DCIM);
        if(dirAlmacenamiento.exists()==false){
            dirAlmacenamiento.mkdirs();
        }
        File image = File.createTempFile(nombreImagen,".jpg",dirAlmacenamiento);
        urlCompletaImg = image.getAbsolutePath();
        return image;
    }
    private void mostrarMsgToast(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }


    private class enviardatos extends AsyncTask<String, String, String> {
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

                    return  "";
                }

                JsonResponse = buffer.toString();

                Log.i(id, JsonResponse);

                return JsonResponse;

            }catch (Exception ex){
                ex.printStackTrace();


            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getBoolean("ok")){

                    Toast.makeText(AgregarProducto.this, "Registro enviado", Toast.LENGTH_SHORT).show();
                    Intent regresar = new Intent(AgregarProducto.this, MainActivity.class);
                    startActivity(regresar);


                }else {
                    Toast.makeText(AgregarProducto.this, "Error al intentar almacenar el registro...", Toast.LENGTH_SHORT).show();
                }

            }catch (Exception e) {
                Toast.makeText(AgregarProducto.this, "Error al enviar a la red" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();

            }
        }
    }
}