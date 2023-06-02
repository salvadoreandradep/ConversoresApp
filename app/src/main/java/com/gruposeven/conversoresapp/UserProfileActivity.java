package com.gruposeven.conversoresapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;

    private TextView txtNombre;
    private TextView txtApodo;
    private TextView txtCorreo;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    private Sensor gyroscopeSensor, proximitySensor, lightSensor;
    private TextView gyroscopeTextView, proximityTextView, lightTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        txtNombre = findViewById(R.id.txtNombre);
        txtApodo = findViewById(R.id.txtApodo);
        txtCorreo = findViewById(R.id.txtCorreo);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String usuarioId = currentUser.getUid();
            DatabaseReference usuarioRef = mDatabase.child("usuarios_regsitrados").child(usuarioId);

            usuarioRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String nombre = dataSnapshot.child("nombre").getValue(String.class);
                        String apodo = dataSnapshot.child("apodo").getValue(String.class);
                        String biografia = dataSnapshot.child("biografia").getValue(String.class);

                        txtNombre.setText(nombre);
                        txtApodo.setText(apodo);
                        txtCorreo.setText(biografia);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Manejar el error de lectura de la base de datos
                }
            });
        }

        gyroscopeTextView = findViewById(R.id.gyroscopeTextView);
        proximityTextView = findViewById(R.id.proximityTextView);
        lightTextView = findViewById(R.id.lightTextView);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Sensor de giroscopio
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if (gyroscopeSensor == null) {
            gyroscopeTextView.setText("Sensor de giroscopio no encontrado");
        }

        // Sensor de proximidad
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if (proximitySensor == null) {
            proximityTextView.setText("Sensor de proximidad no encontrado");
        }

        // Sensor de luz
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (lightSensor == null) {
            lightTextView.setText("Sensor de luz no encontrado");
        }






    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        float[] values = event.values;

        if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float x = values[0];
            float y = values[1];
            float z = values[2];
            gyroscopeTextView.setText("Giroscopio:\nX: " + x + "\nY: " + y + "\nZ: " + z);
        } else if (sensor.getType() == Sensor.TYPE_PROXIMITY) {
            float proximityValue = values[0];
            proximityTextView.setText("Proximidad: " + proximityValue);
        } else if (sensor.getType() == Sensor.TYPE_LIGHT) {
            float lightValue = values[0];
            lightTextView.setText("Luz: " + lightValue);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }





}