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
    private Sensor proximitySensor;
    private TextView proximityTextView;
    private TextView txtNombre;
    private TextView txtApodo;
    private TextView txtCorreo;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;




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
            DatabaseReference usuarioRef = mDatabase.child("usuarios").child(usuarioId);

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



        proximityTextView = findViewById(R.id.proximityTextView);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if (proximitySensor == null) {
            proximityTextView.setText("Sensor de proximidad no encontrado");
        }

    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float distance = event.values[0];
        if (distance < proximitySensor.getMaximumRange()) {
            proximityTextView.setText("Objeto cercano");
        } else {
            proximityTextView.setText("Objeto lejano");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No se utiliza en este ejemplo
    }


}