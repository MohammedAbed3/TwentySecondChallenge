package com.tegogames.twentysecondchallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tegogames.twentysecondchallenge.Database.DataBaseOperations;
import com.tegogames.twentysecondchallenge.Database.DatabaseHelper;

public class AddPlayerActivity extends AppCompatActivity {
    private EditText editTextPlayer1;
    private EditText editTextPlayer2;
    private Button btnConfirm;
    DataBaseOperations operations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);
        operations  = new DataBaseOperations(this);
        operations.open();


        editTextPlayer1 = findViewById(R.id.editTextPlayer1);
        editTextPlayer2 = findViewById(R.id.editTextPlayer2);
        btnConfirm = findViewById(R.id.btnConfirm);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String player1Name = editTextPlayer1.getText().toString();
                String player2Name = editTextPlayer2.getText().toString();



                operations.addPlayers(player1Name, player2Name);

                Intent intent = new Intent(getApplicationContext(),WhatDoYouKnowActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        operations.close();

    }
}