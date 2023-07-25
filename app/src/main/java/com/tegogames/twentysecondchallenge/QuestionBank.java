package com.tegogames.twentysecondchallenge;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.ArrayList;
import java.util.LinkedList;

public class QuestionBank  {

    public LinkedList<String> whatDoYouKnowQuestion(Context context, String player1, String player2){
        LinkedList<String> footballQuestions = new LinkedList<>();

        String question1 = context.getString(R.string.aa);

        // إضافة الأسئلة إلى القائمة
        footballQuestions.add(player1 + "/" + question1);
        footballQuestions.add(player2+"/"+"من هو أفضل هدَّاف في تاريخ كأس العالم لكرة القدم؟");
        footballQuestions.add(player1+"في أي دولة ابتكرت كرة القدم؟");
        footballQuestions.add(player2+"ما هي المساحة الإجمالية لملعب كرة القدم القياسي؟");
        footballQuestions.add(player1+"من هو أشهر لاعب في تاريخ كرة القدم؟");
        footballQuestions.add(player2+"ما هي فترة مباراة كرة القدم الرسمية؟");
        footballQuestions.add(player1+"ما هي عدد اللاعبين المسموح بهم في فريق كرة القدم أثناء المباراة؟");
        footballQuestions.add(player2+"ما هو اسم الكأس الممنوحة للفريق الفائز بالدوري في الدوريات الكروية؟");
        footballQuestions.add(player1+"من هو الفريق الأكثر فوزًا بدوري أبطال أوروبا؟");
        footballQuestions.add(player2+"ما هي المسابقة التي تجمع بين برشلونة وريال مدريد في كرة القدم وتعرف بالكلاسيكو؟");


        return footballQuestions;
    }

}
