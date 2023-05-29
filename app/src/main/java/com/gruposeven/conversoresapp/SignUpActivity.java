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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText edtNombre;
    EditText edtApodo;
    EditText edtCorreo;
    EditText edtContraseña;
    Button btnRegistrar;
    TextView btnmenu;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        edtNombre = findViewById(R.id.edtNombre);
        edtApodo = findViewById(R.id.edtApodo);
        edtCorreo = findViewById(R.id.edtCorreo);
        edtContraseña = findViewById(R.id.edtContraseña);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnmenu= findViewById(R.id.btnlogin);

        btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(login);
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = edtNombre.getText().toString().trim();
                String apodo = edtApodo.getText().toString().trim();
                String correo = edtCorreo.getText().toString().trim();
                String contraseña = edtContraseña.getText().toString().trim();

                registrarUsuario(nombre, apodo, correo, contraseña);
            }
        });


    }

    private void registrarUsuario(final String nombre, final String apodo, String correo, String contraseña) {
        mAuth.createUserWithEmailAndPassword(correo, contraseña)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String usuarioId = mAuth.getCurrentUser().getUid();

                            DatabaseReference usuarioRef = mDatabase.child("usuarios").child(usuarioId);
                            usuarioRef.child("nombre").setValue(nombre);
                            usuarioRef.child("apodo").setValue(apodo);

                            Toast.makeText(SignUpActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(SignUpActivity.this, "Error en el registro: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



}


