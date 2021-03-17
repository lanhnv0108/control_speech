package com.example.final_huy_lanh;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class SplashScreen extends AppCompatActivity {
    Button btn_start;
    MediaPlayer player ;
    ImageView Ima;
    TextView tittle1 , tittle2 , tittle3 ;
    Animation topAni1, topAni2 , topAni3 , main , buttonAni ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        btn_start = (Button) findViewById(R.id.button_start);
        Ima = (ImageView) findViewById(R.id.imagevi);
        setAnima();
        Ima.setAnimation(topAni1);
        tittle1.setAnimation(topAni2);
        tittle2.setAnimation(topAni3);
        tittle3.setAnimation(topAni3);
        btn_start.setAnimation(buttonAni);
        playAudio();
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        nextActivity();
                    }
                }, 100);
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        player.stop();
        finish();
    }
    public void nextActivity(){
        Intent it = new Intent(SplashScreen.this , MainActivity.class);
        startActivity(it);
        finish();
    }
    public void playAudio(){
        if(Locale.getDefault().getDisplayLanguage().toString().equals("English")){
            player = MediaPlayer.create(SplashScreen.this , R.raw.hiiam);
            player.start();
        }else {
            player = MediaPlayer.create(SplashScreen.this , R.raw.chaobanj);
            player.start();
        }
    }
    public void setAnima(){
        tittle1 = (TextView) findViewById(R.id.title1);
        tittle2 = (TextView) findViewById(R.id.title2);
        tittle3 = (TextView) findViewById(R.id.title3);
        topAni1 = AnimationUtils.loadAnimation(this , R.anim.top);
        topAni2 = AnimationUtils.loadAnimation(this , R.anim.top2);
        topAni3 = AnimationUtils.loadAnimation(this , R.anim.top3);
        buttonAni = AnimationUtils.loadAnimation(this , R.anim.bottom);

    }
}