package com.Arid2760.fsshop;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {
    private static int SPLASH = 3000;
    Animation topAn, bottomAn;
    ImageView imageView;
    TextView txt1, txt2;
    SessionManagement sessionManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        setup();
        topAn = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAn = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        txt1.setAnimation(topAn);
        txt2.setAnimation(bottomAn);
        timeOut();
    }

    void setup() {
        imageView = (ImageView) findViewById(R.id.image);
        txt1 = (TextView) findViewById(R.id.text);
        txt2 = (TextView) findViewById(R.id.text1);
    }

    void timeOut() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                sessionManagement = new SessionManagement(getApplicationContext());
                if (sessionManagement.get_userLoggenIn()) {

                    Intent i = new Intent(Splash.this, Home_Page.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(Splash.this, login.class);
                    startActivity(i);
                    finish();
                }
            }
        }, SPLASH);
    }
}