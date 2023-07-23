package com.tegogames.twentysecondchallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tegogames.twentysecondchallenge.Database.DataBaseOperations;
import com.tegogames.twentysecondchallenge.Database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {


    Button play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        // حذف جميع البيانات عند بدء التطبيق
        dbHelper.deleteAllData();

        play = findViewById(R.id.btn_play);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddPlayerActivity.class);
                startActivity(intent);
            }
        });




    }
}