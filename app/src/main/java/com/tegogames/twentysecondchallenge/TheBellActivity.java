package com.tegogames.twentysecondchallenge;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tegogames.twentysecondchallenge.Database.DataBaseOperations;
import com.tegogames.twentysecondchallenge.Database.PlayerModel;
import com.tegogames.twentysecondchallenge.Language.AppCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TheBellActivity extends AppCompat {


    TextView question, player1Name,player2Name,tvPlayer1Strike,tvPlayer2Strike;
    Button btnPlayer1Strike,btnPlayer2Strike,nextQuestion;
    ImageView theBell;

    int player1StrikeCount = 0 , player2StrikeCount = 0; // المتغير النقطي لعدد الضربات
    QuestionBank bank;
    List<PlayerModel> playerStatsList;
    private LinkedList<String> questions;
    DataBaseOperations dbHelper;
    private int currentQuestionIndex;

    List<Integer> player1Strikes,player2Strikes;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_bell);

        player1Strikes  = getIntent().getIntegerArrayListExtra("player1");
        player2Strikes = getIntent().getIntegerArrayListExtra("player2");
        Toast.makeText(this, player1Strikes+"", Toast.LENGTH_SHORT).show();

        dbHelper = new DataBaseOperations(this);
        dbHelper.deleteAllDataInColumn();

        MediaPlayer player= MediaPlayer.create(this,R.raw.bellring);


        int totalPlayer1Strikes = dbHelper.getTotalPlayer1Strikes();
        Log.d("ddd", String.valueOf(totalPlayer1Strikes));


       question= findViewById(R.id.tv_the_bell_question);
        player1Name= findViewById(R.id.tv_the_bell_player1_name);
        player2Name= findViewById(R.id.tv_the_bell_player2_name);
        tvPlayer1Strike= findViewById(R.id.tv_the_bell_strike_player1);
        tvPlayer2Strike= findViewById(R.id.tv_the_bell_strike_player2);
        btnPlayer1Strike= findViewById(R.id.btn_the_bell_player1_strike);
        btnPlayer2Strike= findViewById(R.id.btn_the_bell_player2_strike);
        nextQuestion= findViewById(R.id.btn_the_bell_nxt_question);
        theBell = findViewById(R.id.the_bell);

        bank = new QuestionBank();


        theBell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.start();

            }
        });

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
                    Toast.makeText(TheBellActivity.this, "dddd", Toast.LENGTH_SHORT).show();
                }
                if (player2StrikeCount ==3){
                    Toast.makeText(TheBellActivity.this, "Game over", Toast.LENGTH_SHORT).show();

                    nextQuestion.setText("Next Quiz");



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
            @Override
            public void onClick(View view) {
                if (player2StrikeCount < 3) {
                    player2StrikeCount++;
                    tvPlayer2Strike.setText("strike " + player2StrikeCount);
//                    Toast.makeText(WhatDoYouKnowActivity.this, player2StrikeCount+"dddd", Toast.LENGTH_SHORT).show();

                    dbHelper.addPlayersStrikes(firstName, player1StrikeCount, secondName, player2StrikeCount);

                }else {
                    Toast.makeText(TheBellActivity.this, "stark finsh", Toast.LENGTH_SHORT).show();
                }
                if (player2StrikeCount ==3){
                    Toast.makeText(TheBellActivity.this, "Game over", Toast.LENGTH_SHORT).show();

                    nextQuestion.setText("Next Quiz");



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
        questions = bank.theBellQuestion(this,firstName,secondName);

        Collections.shuffle(questions);

        currentQuestionIndex = 0;

        question.setText(questions.get(currentQuestionIndex));

        nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {






                if (currentQuestionIndex < 10 -1 ) {
                    // زيادة مؤشر السؤال الحالي وعرض السؤال التالي
                    currentQuestionIndex++;
                    Animation fadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

                    question.setAnimation(fadeInAnimation);
                    question.setText(questions.get(currentQuestionIndex));
                }else {


                    showConfirmationDialog();


                }
            }
        });


    }

    private void showConfirmationDialog() {
        List<Integer> player1Strikes1 = dbHelper.getPlayers1Strikes();
        List<Integer> player2Strikes2 = dbHelper.getPlayers2Strikes();



        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle( "confirm");
        builder.setMessage("player 1 :  " + player1Strikes1+"\nplayer 2 :   " + player2Strikes2);

        builder.setPositiveButton("ذهاب", (dialog, which) -> {

            Intent in = new Intent(getApplicationContext(),GuessThePlayerActivity.class);

            ArrayList<Integer> player1 = new ArrayList<>();
            player1.addAll(player1Strikes);
            player1.addAll(player1Strikes1);

            ArrayList<Integer> player2 = new ArrayList<>();
            player2.addAll(player2Strikes);
            player2.addAll(player2Strikes2);

            in.putIntegerArrayListExtra("player11", player1);
            in.putIntegerArrayListExtra("player22", player2);
            startActivity(in);

//            dialog.dismiss(); // إغلاق مربع الحوار
        });
        builder.setNegativeButton("الغاء", (dialog, which) -> {
            // قم بالتصرف عند النقر على زر الغاء
            dialog.dismiss(); // إغلاق مربع الحوار
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}