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
import android.widget.ImageButton;
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
    ImageButton theBell;
    String firstName ,secondName;

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
                player.seekTo(0);
                player.start();

            }
        });

        List<String> playerNames = dbHelper.getFirstAndSecondPlayerNames();

         firstName = playerNames.get(0);
         secondName = playerNames.get(1);
        player1Name.setText(firstName);
        player2Name.setText(secondName);


        btnPlayer1Strike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player1StrikeCount < 3) {
                    player1StrikeCount++;
                    String strike = getString(R.string.strike);

                    tvPlayer1Strike.setText(strike  + player1StrikeCount);




                    dbHelper.addPlayersStrikes(firstName, player1StrikeCount, secondName, player2StrikeCount);


                    currentQuestionIndex++;
                    Animation fadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

                    question.startAnimation(fadeInAnimation);
                    question.setText(questions.get(currentQuestionIndex));
                }else {
                    String gameOver = getString(R.string.game_over);
                    Toast.makeText(TheBellActivity.this, gameOver, Toast.LENGTH_SHORT).show();                }
                if (player1StrikeCount ==3){
                    String gameOver = getString(R.string.game_over);

                    Toast.makeText(TheBellActivity.this, gameOver, Toast.LENGTH_SHORT).show();
                    String nx = getString(R.string.nextquiz);

                    nextQuestion.setText(nx);



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

                    String strike = getString(R.string.strike);

                    tvPlayer2Strike.setText(strike + player2StrikeCount);
//                    Toast.makeText(WhatDoYouKnowActivity.this, player2StrikeCount+"dddd", Toast.LENGTH_SHORT).show();

                    dbHelper.addPlayersStrikes(firstName, player1StrikeCount, secondName, player2StrikeCount);

                    currentQuestionIndex++;
                    Animation fadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

                    question.startAnimation(fadeInAnimation);
                    question.setText(questions.get(currentQuestionIndex));

                }else {
                    String gameOver = getString(R.string.game_over);
                    Toast.makeText(TheBellActivity.this, gameOver, Toast.LENGTH_SHORT).show();                     }
                if (player2StrikeCount ==3){
                    String gameOver = getString(R.string.game_over);

                    Toast.makeText(TheBellActivity.this, gameOver, Toast.LENGTH_SHORT).show();
                    String nx = getString(R.string.nextquiz);

                    nextQuestion.setText(nx);



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
        String con = getString(R.string.result);

        builder.setTitle( con);
        builder.setMessage(firstName +" "+ player1Strikes1+"\n"+secondName +" " + player2Strikes2);
        String go = getString(R.string.go);
        builder.setPositiveButton(go, (dialog, which) -> {

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
        String cancel = getString(R.string.cancel);
        builder.setNegativeButton(cancel, (dialog, which) -> {
            // قم بالتصرف عند النقر على زر الغاء
            dialog.dismiss(); // إغلاق مربع الحوار
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}