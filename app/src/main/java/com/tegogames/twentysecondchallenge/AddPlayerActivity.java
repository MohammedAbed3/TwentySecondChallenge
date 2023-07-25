package com.tegogames.twentysecondchallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.tegogames.twentysecondchallenge.Database.DataBaseOperations;
import com.tegogames.twentysecondchallenge.Database.DatabaseHelper;

public class AddPlayerActivity extends AppCompatActivity {
    private TextInputEditText editTextPlayer1;
    private TextInputEditText editTextPlayer2;
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

                if (!player1Name.isEmpty()&&!player2Name.isEmpty()){
                    operations.addPlayers(player1Name, player2Name);

                    Intent intent = new Intent(getApplicationContext(),WhatDoYouKnowActivity.class);
                    startActivity(intent);

                }else {
                    Toast.makeText(AddPlayerActivity.this, "الرجاء ادخل الاسماء", Toast.LENGTH_SHORT).show();

                }





            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        operations.close();

    }
}