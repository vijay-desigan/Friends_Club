package com.example.friendsclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Objects;

public class Splash_screen extends AppCompatActivity {

    private static int SPLASH_SCREEN = 5000;
    TextView Splash_textOne,Splash_textTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Splash_textOne = findViewById(R.id.Splash_text_one);
        Splash_textTwo = findViewById(R.id.Splash_text_two);

        getWindow().setStatusBarColor(getResources().getColor(R.color.status_color));



       //requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}