package com.gruposeven.conversoresapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingReceiver extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgReceiver";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Aquí puedes agregar lógica adicional para manejar la recepción de mensajes de notificación
        super.onMessageReceived(remoteMessage);
    }

    @Override
    public void onNewToken(String token) {
        // Aquí puedes manejar la obtención de un nuevo token de registro
        super.onNewToken(token);

        // Registrar el token en el servidor o realizar otras acciones necesarias
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        // Aquí puedes enviar el token de registro al servidor
        Log.d(TAG, "Token: " + token);
    }

    public static boolean isGooglePlayServicesAvailable(Context context) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(context);
        return resultCode == ConnectionResult.SUCCESS;
    }

    public static String getToken(Context context) {
        if (isGooglePlayServicesAvailable(context)) {
            String token = FirebaseMessaging.getInstance().getToken().getResult();
            Log.d(TAG, "Token: " + token);
            return token;
        }
        return null;
    }
}