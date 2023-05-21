package com.gruposeven.conversoresapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserProfileActivity extends AppCompatActivity {

    TextView nombreTextView, correoTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        nombreTextView = findViewById(R.id.nombreTextView);
        correoTextView = findViewById(R.id.correoTextView);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Obtén el nombre y correo electrónico del usuario
            String nombre = user.getDisplayName();
            String correo = user.getEmail();

            // Muestra los datos en los TextView
            nombreTextView.setText(nombre);
            correoTextView.setText(correo);
        }
    }
}