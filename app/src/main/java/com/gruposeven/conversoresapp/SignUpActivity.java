package com.gruposeven.conversoresapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText edtNombre;
    EditText edtApodo;
    EditText edtCorreo;
    EditText edtContraseña;
    EditText edtBio;
    Button btnRegistrar;
    TextView btnmenu;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    AwesomeValidation awesomeValidation;

    String miToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.edtCorreo, Patterns.EMAIL_ADDRESS,R.string.invalid_mail);
        awesomeValidation.addValidation(this, R.id.edtContraseña,".{6,}",R.string.invalid_pass);

        edtNombre = findViewById(R.id.edtNombre);
        edtApodo = findViewById(R.id.edtApodo);
        edtCorreo = findViewById(R.id.edtCorreo);
        edtBio = findViewById(R.id.edtBio);
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
                String biografia = edtBio.getText().toString().trim();
                String contraseña = edtContraseña.getText().toString().trim();

                registrarUsuario(nombre, apodo, correo, biografia, contraseña);
            }
        });


    }

    private void registrarUsuario(final String nombre, final String apodo, String correo,String Bio, String contraseña) {

        try {
            mAuth.createUserWithEmailAndPassword(correo, contraseña)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                String mitoken = task.getResult().toString();
                                String usuarioId = mAuth.getCurrentUser().getUid();

                                DatabaseReference usuarioRef = mDatabase.child("usuarios_regsitrados").child(usuarioId);
                                usuarioRef.child("nombre").setValue(nombre);
                                usuarioRef.child("apodo").setValue(apodo);
                                usuarioRef.child("correo").setValue(correo);
                                usuarioRef.child("biografia").setValue(Bio);
                                usuarioRef.child("token").setValue(mitoken);

                                Toast.makeText(SignUpActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(SignUpActivity.this, "Error en el registro: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                String errorcode = ((FirebaseAuthException)task.getException()).getErrorCode();
                                dameToastdeerror(errorcode);
                            }
                        }
                    });

        }catch (Exception e ){
            Toast.makeText(SignUpActivity.this, "Rellena los campos", Toast.LENGTH_SHORT).show();
        }

    }

    private void dameToastdeerror(String error) {

        switch (error) {

            case "ERROR_INVALID_CUSTOM_TOKEN":
                Toast.makeText(SignUpActivity.this, "El formato del token personalizado es incorrecto. Por favor revise la documentación", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                Toast.makeText(SignUpActivity.this, "El token personalizado corresponde a una audiencia diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_CREDENTIAL":
                Toast.makeText(SignUpActivity.this, "La credencial de autenticación proporcionada tiene un formato incorrecto o ha caducado.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_EMAIL":
                Toast.makeText(SignUpActivity.this, "Algo salio mal.", Toast.LENGTH_LONG).show();
                edtCorreo.setError("Revisa tu correo.");
                edtCorreo.requestFocus();
                break;

            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(SignUpActivity.this, "La contraseña no es válida o el usuario no tiene contraseña.", Toast.LENGTH_LONG).show();
                edtContraseña.setError("la contraseña es incorrecta ");
                edtContraseña.requestFocus();
                edtContraseña.setText("");
                break;

            case "ERROR_USER_MISMATCH":
                Toast.makeText(SignUpActivity.this, "Las credenciales proporcionadas no corresponden al usuario que inició sesión anteriormente..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_REQUIRES_RECENT_LOGIN":
                Toast.makeText(SignUpActivity.this,"Esta operación es sensible y requiere autenticación reciente. Inicie sesión nuevamente antes de volver a intentar esta solicitud.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                Toast.makeText(SignUpActivity.this, "Ya existe una cuenta con la misma dirección de correo electrónico pero diferentes credenciales de inicio de sesión. Inicie sesión con un proveedor asociado a esta dirección de correo electrónico.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(SignUpActivity.this, "La dirección de correo electrónico ya está siendo utilizada por otra cuenta..   ", Toast.LENGTH_LONG).show();
                edtCorreo.setError("La dirección de correo electrónico ya está siendo utilizada por otra cuenta.");
                edtCorreo.requestFocus();
                break;

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                Toast.makeText(SignUpActivity.this, "Esta credencial ya está asociada con una cuenta de usuario diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_DISABLED":
                Toast.makeText(SignUpActivity.this, "La cuenta de usuario ha sido inhabilitada por un administrador..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_TOKEN_EXPIRED":
                Toast.makeText(SignUpActivity.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_NOT_FOUND":
                Toast.makeText(SignUpActivity.this, "No hay ningún registro de usuario que corresponda a este identificador. Es posible que se haya eliminado al usuario.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_USER_TOKEN":
                Toast.makeText(SignUpActivity.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                Toast.makeText(SignUpActivity.this, "Esta operación no está permitida. Debes habilitar este servicio en la consola.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(SignUpActivity.this, "La contraseña proporcionada no es válida..", Toast.LENGTH_LONG).show();
                edtContraseña.setError("La contraseña no es válida, debe tener al menos 6 caracteres");
                edtContraseña.requestFocus();
                break;

        }

    }



}


