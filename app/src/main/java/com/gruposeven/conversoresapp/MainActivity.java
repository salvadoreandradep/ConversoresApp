package com.gruposeven.conversoresapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {

        Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btncrear);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DbHelper dbHelper = new DbHelper(MainActivity.this);
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                if(db !=null){
                    Toast.makeText(MainActivity.this, "BASE CREADA", Toast.LENGTH_LONG).show();

                }else {

                    Toast.makeText(MainActivity.this, "BASE NO CREADA", Toast.LENGTH_LONG).show();
                }

            }
        });



        }

        public boolean onCreateOptionMenu(Menu menu ){

            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_principal, menu);
            return true;



        }


        public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.menunuevo:
                nuedoRegistro();

                return true;

            default:
                return  super.onOptionsItemSelected(item);

        }
        }

        private void nuedoRegistro(){

        Intent intent = new Intent(this, NewActivity.class);
        startActivity(intent);
        }
    }
