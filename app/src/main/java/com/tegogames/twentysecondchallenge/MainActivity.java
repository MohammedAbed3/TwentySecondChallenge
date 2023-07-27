package com.tegogames.twentysecondchallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tegogames.twentysecondchallenge.Database.DataBaseOperations;
import com.tegogames.twentysecondchallenge.Database.DatabaseHelper;
import com.tegogames.twentysecondchallenge.Language.AppCompat;
import com.tegogames.twentysecondchallenge.Language.LanguageManager;

public class MainActivity extends AppCompat {

    FloatingActionButton settings;
    LanguageManager languageManager;
    String lang;
    LanguageManager manager;
    Button play;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         languageManager = new LanguageManager(this);
         lang= languageManager.getLang();
        manager = new LanguageManager(this);



        DatabaseHelper dbHelper = new DatabaseHelper(this);

        // حذف جميع البيانات عند بدء التطبيق
        dbHelper.deleteAllData();

        play = findViewById(R.id.btn_play);
        settings = findViewById(R.id.settings);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddPlayerActivity.class);
                startActivity(intent);
            }
        });


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SettingsActivity.class));

            }
        });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finishAffinity();
    }


}