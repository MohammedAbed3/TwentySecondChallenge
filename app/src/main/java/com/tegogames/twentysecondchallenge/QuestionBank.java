package com.tegogames.twentysecondchallenge;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class QuestionBank  {

    public LinkedList<String> whatDoYouKnowQuestion(Context context, String player1, String player2) {
        List<String> mixedQuestions = whatDoYouKnowQuestionShuffle(context);
        LinkedList<String> gameQuestions = new LinkedList<>();

        for (int i = 0; i < 10; i++) {
            // يتم دمج اللاعبين مع الأسئلة المختلطة لإنشاء سؤال اللعبة
            String question = mixedQuestions.get(i);
            String player = i % 2 == 0 ? player1 : player2;
            gameQuestions.add(player + "/" + question);
        }

        return gameQuestions;
    }
    public LinkedList<String> theBellQuestion(Context context, String player1, String player2) {
        List<String> mixedQuestions = theBellQuestionShuffle(context);
        LinkedList<String> gameQuestions = new LinkedList<>();

        for (int i = 0; i < 10; i++) {
            // يتم دمج اللاعبين مع الأسئلة المختلطة لإنشاء سؤال اللعبة
            String question = mixedQuestions.get(i);
            String player = i % 2 == 0 ? player1 : player2;
            gameQuestions.add(player + "/" + question);
        }

        return gameQuestions;
    }
    private List<String> whatDoYouKnowQuestionShuffle(Context context){
        List<String> footballQuestions = new LinkedList<>();

        footballQuestions.add(context.getString(R.string.what_do_you_know_question1));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question2));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question3));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question4));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question5));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question6));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question7));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question8));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question9));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question10));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question11));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question12));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question13));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question14));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question15));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question16));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question17));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question18));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question19));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question20));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question21));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question22));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question23));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question24));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question25));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question26));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question27));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question28));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question29));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question30));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question31));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question32));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question33));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question34));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question35));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question36));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question37));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question38));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question39));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question40));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question41));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question42));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question43));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question44));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question45));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question46));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question47));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question48));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question49));
        footballQuestions.add(context.getString(R.string.what_do_you_know_question50));


        Collections.shuffle(footballQuestions);

        return footballQuestions;

    }
    private List<String> theBellQuestionShuffle(Context context){
        List<String> footballQuestions = new LinkedList<>();

        footballQuestions.add(context.getString(R.string.the_bell_question1));
        footballQuestions.add(context.getString(R.string.the_bell_question2));
        footballQuestions.add(context.getString(R.string.the_bell_question3));
        footballQuestions.add(context.getString(R.string.the_bell_question4));
        footballQuestions.add(context.getString(R.string.the_bell_question5));
        footballQuestions.add(context.getString(R.string.the_bell_question6));
        footballQuestions.add(context.getString(R.string.the_bell_question7));
        footballQuestions.add(context.getString(R.string.the_bell_question8));
        footballQuestions.add(context.getString(R.string.the_bell_question9));
        footballQuestions.add(context.getString(R.string.the_bell_question10));
        footballQuestions.add(context.getString(R.string.the_bell_question11));
        footballQuestions.add(context.getString(R.string.the_bell_question12));
        footballQuestions.add(context.getString(R.string.the_bell_question13));
        footballQuestions.add(context.getString(R.string.the_bell_question14));
        footballQuestions.add(context.getString(R.string.the_bell_question15));
        footballQuestions.add(context.getString(R.string.the_bell_question16));
        footballQuestions.add(context.getString(R.string.the_bell_question17));
        footballQuestions.add(context.getString(R.string.the_bell_question18));
        footballQuestions.add(context.getString(R.string.the_bell_question19));
        footballQuestions.add(context.getString(R.string.the_bell_question20));
        footballQuestions.add(context.getString(R.string.the_bell_question21));
        footballQuestions.add(context.getString(R.string.the_bell_question22));
        footballQuestions.add(context.getString(R.string.the_bell_question23));
        footballQuestions.add(context.getString(R.string.the_bell_question24));
        footballQuestions.add(context.getString(R.string.the_bell_question25));
        footballQuestions.add(context.getString(R.string.the_bell_question26));
        footballQuestions.add(context.getString(R.string.the_bell_question27));
        footballQuestions.add(context.getString(R.string.the_bell_question28));
        footballQuestions.add(context.getString(R.string.the_bell_question29));
        footballQuestions.add(context.getString(R.string.the_bell_question30));
        footballQuestions.add(context.getString(R.string.the_bell_question31));
        footballQuestions.add(context.getString(R.string.the_bell_question32));
        footballQuestions.add(context.getString(R.string.the_bell_question33));
        footballQuestions.add(context.getString(R.string.the_bell_question34));
        footballQuestions.add(context.getString(R.string.the_bell_question35));
        footballQuestions.add(context.getString(R.string.the_bell_question36));
        footballQuestions.add(context.getString(R.string.the_bell_question37));
        footballQuestions.add(context.getString(R.string.the_bell_question38));
        footballQuestions.add(context.getString(R.string.the_bell_question39));
        footballQuestions.add(context.getString(R.string.the_bell_question40));
        footballQuestions.add(context.getString(R.string.the_bell_question41));
        footballQuestions.add(context.getString(R.string.the_bell_question42));
        footballQuestions.add(context.getString(R.string.the_bell_question43));
        footballQuestions.add(context.getString(R.string.the_bell_question44));
        footballQuestions.add(context.getString(R.string.the_bell_question45));
        footballQuestions.add(context.getString(R.string.the_bell_question46));
        footballQuestions.add(context.getString(R.string.the_bell_question47));
        footballQuestions.add(context.getString(R.string.the_bell_question48));
        footballQuestions.add(context.getString(R.string.the_bell_question49));
        footballQuestions.add(context.getString(R.string.the_bell_question50));
        Collections.shuffle(footballQuestions);

        return footballQuestions;

    }


}
