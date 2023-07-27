package com.tegogames.twentysecondchallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tegogames.twentysecondchallenge.Language.AppCompat;

import java.util.List;

public class ResultActivity extends AppCompat {

    TextView tv_player1,tv_player2;
    Button playAgain;
    List<Integer> player1Strikes11,player2Strikes22;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        tv_player1 = findViewById(R.id.tv_player1_score);
        tv_player2 = findViewById(R.id.tv_player2_score);
        playAgain= findViewById(R.id.buttonPlayAgain);





        player1Strikes11  = getIntent().getIntegerArrayListExtra("p1");
        player2Strikes22 = getIntent().getIntegerArrayListExtra("p2");

        int sum1 = 0;
        for (int number : player1Strikes11) {
            sum1 += number;
        }

        int sum2 = 0;
        for (int number : player2Strikes22) {
            sum2 += number;
        }

        tv_player1.setText(String.valueOf(sum1));
        tv_player2.setText(String.valueOf(sum2));

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }
}