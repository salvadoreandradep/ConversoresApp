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

public class crearuser extends AppCompatActivity {


    TextView btnregres;

    Button btncrear;

    EditText mail, pasw;


    FirebaseAuth firebaseAuth;

    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearuser);

        firebaseAuth = FirebaseAuth.getInstance();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.etmail, Patterns.EMAIL_ADDRESS,R.string.invalid_mail);
        awesomeValidation.addValidation(this, R.id.etpass,".{6,}",R.string.invalid_pass);

        mail=findViewById(R.id.etmail);
        pasw=findViewById(R.id.etpass);
        btncrear=findViewById(R.id.btn_register);
        btncrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mail.getText().toString();
                String epass = pasw.getText().toString();

                if (awesomeValidation.validate()){
                    firebaseAuth.createUserWithEmailAndPassword(email,epass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(crearuser.this,"Cuenta creada", Toast.LENGTH_LONG).show();
                                finish();
                            }else {
                                String errorcode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                dameToastdeerror(errorcode);
                            }
                        }
                    });
                }else {
                    Toast.makeText(crearuser.this, "Completa todos los datos", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnregres=findViewById(R.id.btnregresar);
        btnregres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent regresar = new Intent(crearuser.this, MainActivity.class);
                startActivity(regresar);

            }
        });





    }
    private void dameToastdeerror(String error) {

        switch (error) {

            case "ERROR_INVALID_CUSTOM_TOKEN":
                Toast.makeText(crearuser.this, "El formato del token personalizado es incorrecto. Por favor revise la documentación", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                Toast.makeText(crearuser.this, "El token personalizado corresponde a una audiencia diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_CREDENTIAL":
                Toast.makeText(crearuser.this, "La credencial de autenticación proporcionada tiene un formato incorrecto o ha caducado.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_EMAIL":
                Toast.makeText(crearuser.this, "La dirección de correo electrónico está mal formateada.", Toast.LENGTH_LONG).show();
                mail.setError("La dirección de correo electrónico está mal formateada.");
                mail.requestFocus();
                break;

            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(crearuser.this, "La contraseña no es válida o el usuario no tiene contraseña.", Toast.LENGTH_LONG).show();
                pasw.setError("la contraseña es incorrecta ");
                pasw.requestFocus();
                pasw.setText("");
                break;

            case "ERROR_USER_MISMATCH":
                Toast.makeText(crearuser.this, "Las credenciales proporcionadas no corresponden al usuario que inició sesión anteriormente..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_REQUIRES_RECENT_LOGIN":
                Toast.makeText(crearuser.this,"Esta operación es sensible y requiere autenticación reciente. Inicie sesión nuevamente antes de volver a intentar esta solicitud.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                Toast.makeText(crearuser.this, "Ya existe una cuenta con la misma dirección de correo electrónico pero diferentes credenciales de inicio de sesión. Inicie sesión con un proveedor asociado a esta dirección de correo electrónico.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(crearuser.this, "La dirección de correo electrónico ya está siendo utilizada por otra cuenta..   ", Toast.LENGTH_LONG).show();
                mail.setError("La dirección de correo electrónico ya está siendo utilizada por otra cuenta.");
                mail.requestFocus();
                break;

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                Toast.makeText(crearuser.this, "Esta credencial ya está asociada con una cuenta de usuario diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_DISABLED":
                Toast.makeText(crearuser.this, "La cuenta de usuario ha sido inhabilitada por un administrador..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_TOKEN_EXPIRED":
                Toast.makeText(crearuser.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_NOT_FOUND":
                Toast.makeText(crearuser.this, "No hay ningún registro de usuario que corresponda a este identificador. Es posible que se haya eliminado al usuario.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_USER_TOKEN":
                Toast.makeText(crearuser.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                Toast.makeText(crearuser.this, "Esta operación no está permitida. Debes habilitar este servicio en la consola.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(crearuser.this, "La contraseña proporcionada no es válida..", Toast.LENGTH_LONG).show();
                pasw.setError("La contraseña no es válida, debe tener al menos 6 caracteres");
                pasw.requestFocus();
                break;

        }

    }
}