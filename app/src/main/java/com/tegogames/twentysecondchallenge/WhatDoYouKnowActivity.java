package com.tegogames.twentysecondchallenge;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tegogames.twentysecondchallenge.Database.DataBaseOperations;
import com.tegogames.twentysecondchallenge.Database.PlayerModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class WhatDoYouKnowActivity extends AppCompatActivity {

    TextView  question, player1Name,player2Name,tvPlayer1Strike,tvPlayer2Strike;
    Button btnPlayer1Strike,btnPlayer2Strike,nextQuestion;
     int player1StrikeCount = 0 , player2StrikeCount = 0; // المتغير النقطي لعدد الضربات
    QuestionBank bank;
    List<PlayerModel> playerStatsList;
    private LinkedList<String> questions;
    DataBaseOperations dbHelper;


//    int player1Strikes ,player2Strikes;
    private int currentQuestionIndex;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_do_you_know);

        dbHelper = new DataBaseOperations(this);

        question = findViewById(R.id.tv_what_do_you_know_question);
        player1Name = findViewById(R.id.tv_player1_name);
        player2Name = findViewById(R.id.tv_player2_name);
        tvPlayer1Strike = findViewById(R.id.tv_strike_player1);
        tvPlayer2Strike = findViewById(R.id.tv_strike_player2);
        btnPlayer1Strike = findViewById(R.id.btn_player1_strike);
        btnPlayer2Strike = findViewById(R.id.btn_player2_strike);
        nextQuestion = findViewById(R.id.btn_nxt_question);


        List<String> playerNames = dbHelper.getFirstAndSecondPlayerNames();

        String firstName = playerNames.get(0);
        String secondName = playerNames.get(1);
        player1Name.setText(firstName);
        player2Name.setText(secondName);

        btnPlayer1Strike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player1StrikeCount < 3) {
                    player1StrikeCount++;
                    tvPlayer1Strike.setText("strike " + player1StrikeCount);




                    dbHelper.addPlayersStrikes(firstName, player1StrikeCount, secondName, player2StrikeCount);
                }else {
                    Toast.makeText(WhatDoYouKnowActivity.this, "dddd", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnPlayer2Strike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player2StrikeCount < 3) {
                    player2StrikeCount++;
                    tvPlayer2Strike.setText("strike " + player2StrikeCount);
//                    Toast.makeText(WhatDoYouKnowActivity.this, player2StrikeCount+"dddd", Toast.LENGTH_SHORT).show();

                    dbHelper.addPlayersStrikes(firstName, player1StrikeCount, secondName, player2StrikeCount);

                }else {
                    Toast.makeText(WhatDoYouKnowActivity.this, "stark finsh", Toast.LENGTH_SHORT).show();
                }
            }
        });


        bank = new QuestionBank();
        questions = bank.qq();

        Collections.shuffle(questions);

        currentQuestionIndex = 0;

        question.setText(questions.get(currentQuestionIndex));

        nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                List<Integer> player1StrikesList = dbHelper.getPlayers1Strikes();
                List<Integer> player2StrikesList = dbHelper.getPlayers2Strikes();

// استدعاء قيمة السترايك للاعب الأول
                int player1Strikes = player1StrikesList.get(0);


// استدعاء قيمة السترايك للاعب الثاني
                int player2Strikes = player2StrikesList.get(0);

                Log.d("fffff",player1Strikes+"");
//                Log.d("fffff11",playerStrikesList.size()+"");

                List<Integer> player1Strikes11 = dbHelper.getPlayers1Strikes();

                Toast.makeText(getApplicationContext(), player1Strikes11+"", Toast.LENGTH_SHORT).show();

                if (currentQuestionIndex < 10 -1 ) {
                    // زيادة مؤشر السؤال الحالي وعرض السؤال التالي
                    currentQuestionIndex++;
                    question.setText(questions.get(currentQuestionIndex));
                }else {


                    showConfirmationDialog();


                }
            }
        });

    }

    private void showConfirmationDialog() {
        List<Integer> player1Strikes = dbHelper.getPlayers1Strikes();
        List<Integer> player2Strikes = dbHelper.getPlayers2Strikes();

//// عرض عدد سترايكات اللاعب الأول
//        for (int strikes : player1Strikes) {
//            Log.d("Player1 Strikes", "عدد سترايكات اللاعب الأول: " + strikes);
//        }
//
//// عرض عدد سترايكات اللاعب الثاني
//        for (int strikes : player2Strikes) {
//            Log.d("Player2 Strikes", "عدد سترايكات اللاعب الثاني: " + strikes);
//        }


//        List<Integer> player1Strikes = dbHelper.getPlayers1Strikes();
//        List<Integer> player2Strikes = dbHelper.getPlayers2Strikes();
//        int player1Strikes1 = player1Strikes.get(0);
//        int player2Strikes2 = player2Strikes.get(0);

// الآن يمكنك استخدام قيم player1Strikes و player2Strikes في واجهة المستخدم أو أي مكان آخر بحسب احتياجك

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle( "confirm");
        builder.setMessage("player 1 :  " + player1Strikes+"\nplayer 2 :   " + player2Strikes);

        builder.setPositiveButton("ذهاب", (dialog, which) -> {
            // قم بالتصرف عند النقر على زر الذهاب
            dialog.dismiss(); // إغلاق مربع الحوار
            // انتقل إلى السؤال التالي أو اتخذ الإجراء الذي تحتاجه هنا
        });
        builder.setNegativeButton("الغاء", (dialog, which) -> {
            // قم بالتصرف عند النقر على زر الغاء
            dialog.dismiss(); // إغلاق مربع الحوار
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}