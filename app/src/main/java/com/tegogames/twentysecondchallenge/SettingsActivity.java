package com.tegogames.twentysecondchallenge;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.tegogames.twentysecondchallenge.Language.AppCompat;
import com.tegogames.twentysecondchallenge.Language.LanguageManager;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompat {
    private int selectedLanguageIndex = 0; // المؤشر الافتراضي للغة العربية

    LanguageManager languageManager;
    String lang;
    LanguageManager manager;
    Button updataLang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        languageManager = new LanguageManager(this);
        lang= languageManager.getLang();
        manager = new LanguageManager(this);

        updataLang = findViewById(R.id.setting_btn_chang_lang);
        updataLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmationDialog();
            }
        });
    }


    public void updataLanguage(String lang) {
        if (lang.equals("ar")) {
            manager.updateResource("ar");
            recreate();


        } else if(lang.equals("en")) {
            manager.updateResource("en");
          recreate();

        }
    }
    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("تأكيد");
        builder.setMessage("اختر اللغة المطلوبة");

        // تضمين تخطيط القائمة من ملف التصميم dialog_language_selection.xml
        View view = getLayoutInflater().inflate(R.layout.dialog_language_selection, null);
        builder.setView(view);

        // الحصول على المكونات المطلوبة من التخطيط
        RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
        RadioButton radioButtonArabic = view.findViewById(R.id.radioButtonArabic);
        RadioButton radioButtonEnglish = view.findViewById(R.id.radioButtonEnglish);

        // الإجراء عند الضغط على زر "ذهاب"
        builder.setPositiveButton("ذهاب", (dialog, which) -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId == R.id.radioButtonArabic) {
                updataLanguage("ar");

                Toast.makeText(this, "تم اختيار اللغة العربية", Toast.LENGTH_SHORT).show();
            } else if (selectedId == R.id.radioButtonEnglish) {
                updataLanguage("en");


                Toast.makeText(this, "تم اختيار اللغة الإنجليزية", Toast.LENGTH_SHORT).show();
            }
            recreate();
        });

        // زر الإغلاق
        builder.setNegativeButton("الغاء", (dialog, which) -> {
            dialog.dismiss();
            recreate();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(getApplicationContext(),MainActivity.class));

    }
}