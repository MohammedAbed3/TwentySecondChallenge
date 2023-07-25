package com.tegogames.twentysecondchallenge;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tegogames.twentysecondchallenge.Database.DataBaseOperations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class AlmazadActivity extends AppCompatActivity {

    private  TextView player1Name,player2Name,question,tvPlayer1Strike,tvPlayer2Strike;
   private Button btnPlayer1Strike,btnPlayer2Strike,nextQuestion,startTimer,addPlayer1,addPlayer2;
    private int player1StrikeCount = 0 , player2StrikeCount = 0,addPlayer1Count =0,addPlayer2Count =0;
    private  DataBaseOperations dbHelper;
    private CountDownTimer timer;
    private static final long TIMER_DURATION = 20000; // مدة التايمر بالميلي ثانية (20 ثانية)

    private static final long DELAY_DURATION = 3000; // 3 ثوانٍ (3000 مللي ثانية)
    QuestionBank bank;
    private LinkedList<String> questions;

    private int currentQuestionIndex;

    MediaPlayer player;
    List<Integer> player1Strikes,player2Strikes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_almazad);

        dbHelper = new DataBaseOperations(this);
        dbHelper.deleteAllDataInColumn();

        int totalPlayer1Strikes = dbHelper.getTotalPlayer1Strikes();
        Log.d("ddd", String.valueOf(totalPlayer1Strikes));

//        Log.d("ddddddddd",wa.toString());
//        Log.d("ddddddddd",wa.size()+"");


        player1Strikes  = getIntent().getIntegerArrayListExtra("player1Strikes");
        player2Strikes = getIntent().getIntegerArrayListExtra("player2Strikes");
        Toast.makeText(this, player1Strikes+"", Toast.LENGTH_SHORT).show();


        player1Name = findViewById(R.id.almazad_tv_player1_name);
        player2Name = findViewById(R.id.almazad_tv_player2_name);
        question = findViewById(R.id.tv_almazad_question);
        tvPlayer1Strike = findViewById(R.id.almazad_tv_strike_player1);
        tvPlayer2Strike = findViewById(R.id.almazad_tv_strike_player2);
        btnPlayer1Strike = findViewById(R.id.almazad_btn_player1_strike);
        btnPlayer2Strike = findViewById(R.id.almazad_btn_player2_strike);
        nextQuestion = findViewById(R.id.almazad_btn_nxt_question);
        startTimer = findViewById(R.id.almazad_btn_start_timer);
        addPlayer1 = findViewById(R.id.almazad_btn_player1_add);
        addPlayer2 = findViewById(R.id.almazad_btn_player2_add);




        List<String> playerNames = dbHelper.getFirstAndSecondPlayerNames();

        String firstName = playerNames.get(0);
        String secondName = playerNames.get(1);
        player1Name.setText(firstName);
        player2Name.setText(secondName);

         player = MediaPlayer.create(this,R.raw.timeover);


        bank = new QuestionBank();
        questions = bank.whatDoYouKnowQuestion(this,firstName,secondName); //بدنا نغير الاسئلة

        Collections.shuffle(questions);

        currentQuestionIndex = 0;

        question.setText(questions.get(currentQuestionIndex));


        btnPlayer1Strike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player1StrikeCount < 3) {
                    player1StrikeCount++;
                    tvPlayer1Strike.setText("strike " + player1StrikeCount);



                    dbHelper.addPlayersStrikes(firstName, player1StrikeCount, secondName, player2StrikeCount);
                }else {
                    Toast.makeText(AlmazadActivity.this, "dddd", Toast.LENGTH_SHORT).show();
                }
                if (player1StrikeCount ==3){
                    Toast.makeText(AlmazadActivity.this, "Game over", Toast.LENGTH_SHORT).show();
                    addPlayer1.setEnabled(false);
                    addPlayer2.setEnabled(false);
//                    nextQuestion.setEnabled(false);
                    btnPlayer2Strike.setEnabled(false);
                    startTimer.setEnabled(false);
                    nextQuestion.setText("Next Quiz");

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
                    Toast.makeText(AlmazadActivity.this, "stark finsh", Toast.LENGTH_SHORT).show();
                }

                if (player2StrikeCount ==3){
                    Toast.makeText(AlmazadActivity.this, "Game over", Toast.LENGTH_SHORT).show();
                    addPlayer1.setEnabled(false);
                    addPlayer2.setEnabled(false);
                    startTimer.setEnabled(false);
                    nextQuestion.setText("Next Quiz");



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

        addPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPlayer1Count++;
                addPlayer1.setText(addPlayer1Count+"");
            }
        });

        addPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPlayer2Count++;
                addPlayer2.setText(addPlayer2Count+"");
            }
        });

        startTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler = new Handler();

                if (addPlayer1Count > 0 &&addPlayer2Count > 0 &&addPlayer1Count!=addPlayer2Count){
                    startTimer.setText("استعد");
                    startTimer.setEnabled(false);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            startTimerFunc();




                        }
                    }, DELAY_DURATION);


                }else {
                    Toast.makeText(AlmazadActivity.this, "you hava to add count", Toast.LENGTH_SHORT).show();
                }
            }
        });

        nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                if (addPlayer1Count !=0 || addPlayer2Count !=0){
                    if (currentQuestionIndex < 10 -1 ) {
                        // زيادة مؤشر السؤال الحالي وعرض السؤال التالي
                        addPlayer1.setText("Add");
                        addPlayer1Count = 0;
                        addPlayer2Count = 0;
                        addPlayer2.setText("Add");
                        currentQuestionIndex++;
                        Animation fadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

                        question.setAnimation(fadeInAnimation);
                        question.setText(questions.get(currentQuestionIndex));
                    }else {


                        showConfirmationDialog();


                    }

                }else {
                    Toast.makeText(AlmazadActivity.this, "You must start the auction", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void startTimerFunc() {

        timer = new CountDownTimer(TIMER_DURATION, 1000) { // الوقت الكلي والفاصل الزمني للتايمر
            public void onTick(long millisUntilFinished) {
                // يتم استدعاء هذه الدالة بكل ثانية تمر على التايمر (للحصول على تحديثات)
                long secondsRemaining = millisUntilFinished / 1000;
                // قم بتحديث الواجهة المستخدمة لعرض الوقت المتبقي إذا كنت بحاجة إلى ذلك
                startTimer.setText("الوقت المتبقي: " + secondsRemaining + " ثانية");
                addPlayer1.setEnabled(false);
                addPlayer2.setEnabled(false);
                nextQuestion.setEnabled(false);
                btnPlayer1Strike.setEnabled(false);
                btnPlayer2Strike.setEnabled(false);
            }

            public void onFinish() {

//                player.start();
                addPlayer1.setEnabled(true);
                addPlayer2.setEnabled(true);
                nextQuestion.setEnabled(true);
                btnPlayer1Strike.setEnabled(true);
                btnPlayer2Strike.setEnabled(true);
                startTimer.setEnabled(true);

                startTimer.setText("انتهى الوقت !!!!");
                Handler h =new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        startTimer.setText("Start Timer");


                    }
                }, 2000);

                addPlayer1.setText("Add");
                addPlayer1Count = 0;
                addPlayer2Count = 0;
                addPlayer2.setText("Add");

            }
        }.start(); // تشغيل التايمر

    }

    private void showConfirmationDialog() {
        List<Integer> player1Strikes1 = dbHelper.getPlayers1Strikes();
        List<Integer> player2Strikes2 = dbHelper.getPlayers2Strikes();



        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle( "confirm");
        builder.setMessage("player 1 :  " + player1Strikes1+"\nplayer 2 :   " + player2Strikes2);

        builder.setPositiveButton("ذهاب", (dialog, which) -> {

        Intent intent = new Intent(getApplicationContext(), TheBellActivity.class);

            ArrayList<Integer> player1 = new ArrayList<>();
            player1.addAll(player1Strikes);
            player1.addAll(player1Strikes1);

            ArrayList<Integer> player2 = new ArrayList<>();
            player2.addAll(player2Strikes);
            player2.addAll(player2Strikes2);

            intent.putIntegerArrayListExtra("player1", player1);
            intent.putIntegerArrayListExtra("player2", player2);

        startActivity(intent);

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