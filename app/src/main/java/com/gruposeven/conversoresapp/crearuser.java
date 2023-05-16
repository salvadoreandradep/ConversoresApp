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
                                damecodeerror(errorcode);
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
    private void damecodeerror(String error){

    }
}