package com.gruposeven.conversoresapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

public class Notifications extends AppCompatActivity {

    private Handler handler;
    private Runnable runnable;
    private NotificationManager notificationManager;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        handler = new Handler();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        textView = findViewById(R.id.textView);

        // Configurar botones
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


    }

    private void startNotifications() {
        // Configurar el Runnable para que se ejecute cada hora
        runnable = new Runnable() {
            @Override
            public void run() {
                showNotification();
                handler.postDelayed(this, 30000); // 1 hora = 3600000 milisegundos
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