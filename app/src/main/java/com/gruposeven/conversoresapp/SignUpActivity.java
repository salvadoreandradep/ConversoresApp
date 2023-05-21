package com.gruposeven.conversoresapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpActivity extends AppCompatActivity {

    EditText nombreEditText, correoEditText, contraseñaEditText;

    TextView regresarlogin;
    Button registrarButton;
    ;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();
        nombreEditText = findViewById(R.id.nombreEditText);
        correoEditText = findViewById(R.id.correoEditText);
        contraseñaEditText = findViewById(R.id.contraseñaEditText);
        registrarButton = findViewById(R.id.registrarButton);
        regresarlogin=findViewById(R.id.textViewLogin);

        regresarlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Homer1 = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(Homer1);
            }
        });


        registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = nombreEditText.getText().toString();
                String correo = correoEditText.getText().toString();
                String contraseña = contraseñaEditText.getText().toString();

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(correo, contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()){

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user != null) {
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(nombre)
                                        .build();
                                user.updateProfile(profileUpdates);
                            }
                            Intent Homer = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(Homer);
                            // Realiza acciones adicionales después del registro exitoso, si es necesario
                            Toast.makeText(SignUpActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(SignUpActivity.this, "Error al registrar el usuario: " + task.getException(), Toast.LENGTH_SHORT).show();

                        }

                    }
                });
            }
        });






    }



}


