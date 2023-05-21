package com.gruposeven.conversoresapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.core.view.View;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    Button buttonLogout;
    FirebaseAuth firebaseAuth;
    ListView postsListView;
    Button createPostButton;
    List<String> postsList;
    PostAdapter postAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        firebaseAuth = FirebaseAuth.getInstance();

        buttonLogout = findViewById(R.id.buttonLogout);

        postsListView = findViewById(R.id.postsListView);
        createPostButton = findViewById(R.id.createPostButton);

        postsList = new ArrayList<>();
        postAdapter = new PostAdapter(this, postsList);
        postsListView.setAdapter(postAdapter);

        createPostButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Toast.makeText(HomeActivity.this, "Create Post Clicked", Toast.LENGTH_SHORT).show();

            }
        });

        postsList.add("Publicación 1");
        postsList.add("Publicación 2");
        postsList.add("Publicación 3");

        // Notificar al adaptador que los datos han cambiado
        postAdapter.notifyDataSetChanged();





        buttonLogout.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                firebaseAuth.signOut();
                finish();
            }
        });


    }
}