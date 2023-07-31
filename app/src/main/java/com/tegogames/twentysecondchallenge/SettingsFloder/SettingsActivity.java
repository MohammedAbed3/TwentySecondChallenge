package com.tegogames.twentysecondchallenge.SettingsFloder;

import androidx.appcompat.app.AlertDialog;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.tegogames.twentysecondchallenge.Language.AppCompat;
import com.tegogames.twentysecondchallenge.Language.LanguageManager;
import com.tegogames.twentysecondchallenge.MainActivity;
import com.tegogames.twentysecondchallenge.R;

public class SettingsActivity extends AppCompat {
    private int selectedLanguageIndex = 0; // المؤشر الافتراضي للغة العربية

    LanguageManager languageManager;
    String lang;
    LanguageManager manager;
    Button updataLang,about,connect_us, shareButton,rateButton;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        languageManager = new LanguageManager(this);
        lang= languageManager.getLang();
        manager = new LanguageManager(this);

        updataLang = findViewById(R.id.setting_tv_lang);
        about = findViewById(R.id.btn_about);
        connect_us = findViewById(R.id.btn_connect_us);
         rateButton = findViewById(R.id.btn_rate_app);

        updataLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmationDialog();
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AboutActivity.class));
            }
        });
        connect_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ConnectUsActivity.class));

            }
        });

        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateApp();
            }
        });

         shareButton = findViewById(R.id.btn_share_app);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareApp();
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
        builder.setTitle("اختر اللغة المطلوبة");

        // تضمين تخطيط القائمة من ملف التصميم dialog_language_selection.xml
        View view = getLayoutInflater().inflate(R.layout.dialog_language_selection, null);
        builder.setView(view);

        // الحصول على المكونات المطلوبة من التخطيط
        RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
        RadioButton radioButtonArabic = view.findViewById(R.id.radioButtonArabic);
        RadioButton radioButtonEnglish = view.findViewById(R.id.radioButtonEnglish);

        // الإجراء عند الضغط على زر "ذهاب"
        builder.setPositiveButton("تأكيد", (dialog, which) -> {
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

    private void shareApp() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "My App"); // عنوان المشاركة
        intent.putExtra(Intent.EXTRA_TEXT, "Check out this amazing app!"); // نص المشاركة
        startActivity(Intent.createChooser(intent, "Share via")); // افتح واجهة المشاركة واختر التطبيق الذي ترغب في المشاركة من خلاله
    }

    private void rateApp() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent rateIntent = new Intent(Intent.ACTION_VIEW, uri);

        try {
            startActivity(rateIntent);
        } catch (ActivityNotFoundException e) {
            // إذا كان لم يتم العثور على متجر التطبيقات (مثل Google Play)، قم بفتح متصفح الويب لصفحة تطبيقك على متجر التطبيقات
            Uri webUri = Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName());
            Intent webRateIntent = new Intent(Intent.ACTION_VIEW, webUri);
            startActivity(webRateIntent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(getApplicationContext(), MainActivity.class));

    }

}