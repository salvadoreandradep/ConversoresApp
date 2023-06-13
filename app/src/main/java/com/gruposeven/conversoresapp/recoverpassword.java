package com.gruposeven.conversoresapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class recoverpassword extends AppCompatActivity {

    private EditText editTextEmail;
    TextView txtlogins;
    private Button buttonRecuperarContraseña;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recoverpassword);

        firebaseAuth = FirebaseAuth.getInstance();

        // Obtener referencias de vistas
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonRecuperarContraseña = findViewById(R.id.buttonRecuperarContraseña);

        // Configurar el click listener del botón de recuperar contraseña
        buttonRecuperarContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recuperarContraseña();
            }
        });




        txtlogins=findViewById(R.id.txtloginsd);
        txtlogins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logins = new Intent(recoverpassword.this, LoginActivity.class);
                startActivity(logins);
            }
        });





    }

    private void recuperarContraseña() {
        String email = editTextEmail.getText().toString().trim();

        // Validar que el campo de email no esté vacío
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Por favor, ingresa tu correo electrónico", Toast.LENGTH_SHORT).show();
            return;
        }

        // Enviar correo de recuperación de contraseña
        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // El correo de recuperación de contraseña se envió exitosamente
                            Toast.makeText(recoverpassword.this, "Se ha enviado un correo electrónico para recuperar tu contraseña", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            // Hubo un error al enviar el correo de recuperación de contraseña
                            Toast.makeText(recoverpassword.this, "Error al enviar el correo de recuperación de contraseña. Por favor, intenta nuevamente.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}