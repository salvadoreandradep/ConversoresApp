package com.gruposeven.conversoresapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

public class MainActivity extends AppCompatActivity {

        TabHost tbthost;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tbthost= (TabHost) findViewById(R.id.tabhost);
        tbthost.setup();

       TabHost.TabSpec tab1 = tbthost.newTabSpec("tab1");
       tab1.setIndicator("Conversor");
       tab1.setContent(R.id.tab1);

        TabHost.TabSpec tab2 = tbthost.newTabSpec("tab2");
        tab2.setIndicator("Lectura");
        tab2.setContent(R.id.tab2);

        tbthost.addTab(tab1);
        tbthost.addTab(tab2);



    }
}