package com.tegogames.twentysecondchallenge.SettingsFloder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tegogames.twentysecondchallenge.R;

public class ConnectUsActivity extends AppCompatActivity {

    EditText message;
    Button sent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_us);


        message = findViewById(R.id.feedbackEditText);
        sent = findViewById(R.id.submitButton);

        sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String content = message.getText().toString();
                if (content.isEmpty()){
                    Toast.makeText(ConnectUsActivity.this, "Plese Enter You Masseg", Toast.LENGTH_SHORT).show();
                }else {
                    sentEmail(content);
                }

            }
        });
    }
    public void sentEmail(String text){

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"muslim.mate.info@gmail.com\n"});
        intent.putExtra(Intent.EXTRA_SUBJECT,"from user");
        intent.putExtra(Intent.EXTRA_TEXT,text);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"Choose email client"));

    }

}