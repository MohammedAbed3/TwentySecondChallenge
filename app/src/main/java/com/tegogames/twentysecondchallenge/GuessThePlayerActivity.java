 package com.tegogames.twentysecondchallenge;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tegogames.twentysecondchallenge.Database.DataBaseOperations;
import com.tegogames.twentysecondchallenge.Database.PlayerModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

 public class GuessThePlayerActivity extends AppCompatActivity {
     List<Integer> player1Strikes,player2Strikes;

     TextView question, player1Name,player2Name,tvPlayer1Strike,tvPlayer2Strike;
     Button btnPlayer1Strike,btnPlayer2Strike,nextQuestion;
     int player1StrikeCount = 0 , player2StrikeCount = 0; // المتغير النقطي لعدد الضربات
     QuestionBank bank;
     List<PlayerModel> playerStatsList;
     private LinkedList<String> questions;
     DataBaseOperations dbHelper;
     List<Integer> player1Strikes11,player2Strikes22;


     private int currentQuestionIndex;

     @SuppressLint("MissingInflatedId")
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_the_player);
         dbHelper = new DataBaseOperations(this);
         player1Strikes11  = getIntent().getIntegerArrayListExtra("player11");
         player2Strikes22 = getIntent().getIntegerArrayListExtra("player22");
         dbHelper.deleteAllDataInColumn();

         question = findViewById(R.id.tv_guess_the_player_question);
         player1Name = findViewById(R.id.tv_guess_the_player_player1_name);
         player2Name = findViewById(R.id.tv_guess_the_player_player2_name);
         tvPlayer1Strike = findViewById(R.id.tv_guess_the_player_strike_player1);
         tvPlayer2Strike = findViewById(R.id.tv_guess_the_player_strike_player2);
         btnPlayer1Strike = findViewById(R.id.btn_guess_the_player_player1_strike);
         btnPlayer2Strike = findViewById(R.id.btn_guess_the_player_player2_strike);
         nextQuestion = findViewById(R.id.btn_guess_the_player_nxt_question);
         player1Strikes  = getIntent().getIntegerArrayListExtra("player11");
        player2Strikes = getIntent().getIntegerArrayListExtra("player22");
        Toast.makeText(this, player1Strikes+"", Toast.LENGTH_SHORT).show();


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
                     Toast.makeText(GuessThePlayerActivity.this, "dddd", Toast.LENGTH_SHORT).show();
                 }
                 if (player2StrikeCount ==3){
                     Toast.makeText(GuessThePlayerActivity.this, "Game over", Toast.LENGTH_SHORT).show();

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
                     Toast.makeText(GuessThePlayerActivity.this, "stark finsh", Toast.LENGTH_SHORT).show();
                 }
                 if (player2StrikeCount ==3){
                     Toast.makeText(GuessThePlayerActivity.this, "Game over", Toast.LENGTH_SHORT).show();

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
         questions = bank.whatDoYouKnowQuestion(this,firstName,secondName);

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
         List<Integer> player1Strikes = dbHelper.getPlayers1Strikes();
         List<Integer> player2Strikes = dbHelper.getPlayers2Strikes();



         AlertDialog.Builder builder = new AlertDialog.Builder(this);
         builder.setTitle( "confirm");
         builder.setMessage("player 1 :  " + player1Strikes+"\nplayer 2 :   " + player2Strikes);

         builder.setPositiveButton("ذهاب", (dialog, which) -> {

             Intent in = new Intent(getApplicationContext(),ResultActivity.class);

             ArrayList<Integer> player1 = new ArrayList<>();
             player1.addAll(player1Strikes);
             player1.addAll(player1Strikes11);

             ArrayList<Integer> player2 = new ArrayList<>();
             player2.addAll(player2Strikes);
             player2.addAll(player2Strikes22);

             in.putIntegerArrayListExtra("p1", player1);
             in.putIntegerArrayListExtra("p2", player2);
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