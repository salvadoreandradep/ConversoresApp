package com.gruposeven.conversoresapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

public class LoginActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    Button buttonLogin;
    TextView textViewSignUp, txtrecovery;
    FirebaseAuth firebaseAuth;

    private Handler handler;
    private Runnable runnable;
    private NotificationManager notificationManager;
    private TextView textView;

       String miToken= "";









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNotifications();
            }
        });

        Button stopButton = findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopNotifications();
            }
        });



        firebaseAuth = FirebaseAuth.getInstance();

        // Verificar si el usuario ya ha iniciado sesión
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            // Si el usuario ya ha iniciado sesión, redirigir a la siguiente actividad
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSignUp = findViewById(R.id.textViewSignUp);
        txtrecovery=findViewById(R.id.txtrecovery);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                      return;
                    }

                    // Obtén el token de registro del dispositivo
                    String mitoken = task.getResult();
                    Toast.makeText(getApplicationContext(),"mi token" +  mitoken, Toast.LENGTH_LONG).show();

                    // Puedes guardar el token en tu servidor para enviar notificaciones push más adelante
                });

        txtrecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, recoverpassword.class));
            }
        });


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
        handler = new Handler();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        textView = findViewById(R.id.textView);

        // Configurar botones


    }

    private void loginUser() {


        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public void ObtekerToken(){

        AtomicReference<String> token = null;
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                return;


            }


            String miToken = task.getResult();
            Toast.makeText(getApplicationContext(), "Token"+ miToken, Toast.LENGTH_LONG).show();

        });


    }

    private void startNotifications() {
        // Configurar el Runnable para que se ejecute cada hora
        runnable = new Runnable() {
            @Override
            public void run() {

                handler.postDelayed(this, 50); // 1 hora = 3600000 milisegundos
                showNotification();
            }
        };

        // Iniciar el Runnable
        handler.post(runnable);
    }

    private void stopNotifications() {
        // Detener el Runnable
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }

    private void showNotification() {
        // Crear la notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Notificación")
                .setContentText("¡Esta es una notificación push!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // Mostrar la notificación
        int notificationId = (int) new Date().getTime(); // Generar un ID único para la notificación
        notificationManager.notify(notificationId, builder.build());
    }




}