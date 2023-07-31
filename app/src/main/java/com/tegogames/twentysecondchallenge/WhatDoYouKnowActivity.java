package com.tegogames.twentysecondchallenge;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tegogames.twentysecondchallenge.Database.DataBaseOperations;
import com.tegogames.twentysecondchallenge.Database.PlayerModel;
import com.tegogames.twentysecondchallenge.Language.AppCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class WhatDoYouKnowActivity extends AppCompat {

    TextView  question, player1Name,player2Name,tvPlayer1Strike,tvPlayer2Strike;
    Button btnPlayer1Strike,btnPlayer2Strike,nextQuestion;
     int player1StrikeCount = 0 , player2StrikeCount = 0; // المتغير النقطي لعدد الضربات
    QuestionBank bank;
    List<PlayerModel> playerStatsList;
    private LinkedList<String> questions;
    DataBaseOperations dbHelper;

    private List<Integer> player1Strikes;

    // متغير لتخزين قائمة السترايكات للاعب الثاني
    private List<Integer> player2Strikes;
    String firstName,secondName;

//    int player1Strikes ,player2Strikes;
    private int currentQuestionIndex;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_do_you_know);

        dbHelper = new DataBaseOperations(this);

        int totalPlayer1Strikes = dbHelper.getTotalPlayer1Strikes();
        Log.d("ddd", String.valueOf(totalPlayer1Strikes));




        question = findViewById(R.id.tv_what_do_you_know_question);
        player1Name = findViewById(R.id.tv_player1_name);
        player2Name = findViewById(R.id.tv_player2_name);
        tvPlayer1Strike = findViewById(R.id.tv_strike_player1);
        tvPlayer2Strike = findViewById(R.id.tv_strike_player2);
        btnPlayer1Strike = findViewById(R.id.btn_player1_strike);
        btnPlayer2Strike = findViewById(R.id.btn_player2_strike);
        nextQuestion = findViewById(R.id.btn_nxt_question);


        List<String> playerNames = dbHelper.getFirstAndSecondPlayerNames();

         firstName = playerNames.get(0);
         secondName = playerNames.get(1);
        player1Name.setText(firstName);
        player2Name.setText(secondName);

        btnPlayer1Strike.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if (player1StrikeCount < 3) {
                    player1StrikeCount++;

                    String strike = getString(R.string.strike);


                    tvPlayer1Strike.setText(strike + player1StrikeCount);




                    dbHelper.addPlayersStrikes(firstName, player1StrikeCount, secondName, player2StrikeCount);

                    currentQuestionIndex++;
                    Animation fadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

                    question.startAnimation(fadeInAnimation);
                    question.setText(questions.get(currentQuestionIndex));
                }else {
                    Toast.makeText(WhatDoYouKnowActivity.this, "", Toast.LENGTH_SHORT).show();
                }
                if (player1StrikeCount ==3){
                    Toast.makeText(WhatDoYouKnowActivity.this, "Game over", Toast.LENGTH_SHORT).show();

                    String next_question = getString(R.string.next_question);

                    nextQuestion.setText(next_question);


                    btnPlayer2Strike.setEnabled(false);
                    btnPlayer1Strike.setEnabled(false);
                    nextQuestion.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showConfirmationDialog();

                        }
                    });

                    showConfirmationDialog();
                }
            }
        });


        btnPlayer2Strike.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if (player2StrikeCount < 3) {
                    player2StrikeCount++;

                    String strike = getString(R.string.strike);

                    tvPlayer2Strike.setText(strike + player2StrikeCount);

                    dbHelper.addPlayersStrikes(firstName, player1StrikeCount, secondName, player2StrikeCount);

                    currentQuestionIndex++;
                    Animation fadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

                    question.startAnimation(fadeInAnimation);
                    question.setText(questions.get(currentQuestionIndex));

                }else {
                    Toast.makeText(WhatDoYouKnowActivity.this, "", Toast.LENGTH_SHORT).show();
                }
                if (player2StrikeCount ==3){
                    Toast.makeText(WhatDoYouKnowActivity.this, "Game over", Toast.LENGTH_SHORT).show();

                    String next_question = getString(R.string.next_question);

                    nextQuestion.setText(next_question);



                    btnPlayer1Strike.setEnabled(false);
                    btnPlayer2Strike.setEnabled(false);
                    nextQuestion.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showConfirmationDialog();

                        }
                    });

                    showConfirmationDialog();
                }
            }
        });


        bank = new QuestionBank();
        questions = bank.whatDoYouKnowQuestion(this,firstName,secondName);


        currentQuestionIndex = 0;

        question.setText(questions.get(currentQuestionIndex));

        nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {






                if (currentQuestionIndex < 10 -1 ) {
                    // زيادة مؤشر السؤال الحالي وعرض السؤال التالي
                    currentQuestionIndex++;
                    Animation fadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

                    question.startAnimation(fadeInAnimation);
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



        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String con = getString(R.string.result);

        builder.setTitle( con);

        builder.setMessage(firstName +" "+ player1Strikes+"\n"+secondName +" " + player2Strikes);
        String go = getString(R.string.go);

        builder.setPositiveButton(go, (dialog, which) -> {

            Intent in = new Intent(getApplicationContext(),AlmazadActivity.class);
            in.putIntegerArrayListExtra("player1Strikes", (ArrayList<Integer>) player1Strikes);
            in.putIntegerArrayListExtra("player2Strikes", (ArrayList<Integer>) player2Strikes);

            startActivity(in);

//            dialog.dismiss(); // إغلاق مربع الحوار
        });
        String cancel = getString(R.string.cancel);

        builder.setNegativeButton(cancel, (dialog, which) -> {
            // قم بالتصرف عند النقر على زر الغاء
            dialog.dismiss(); // إغلاق مربع الحوار
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}