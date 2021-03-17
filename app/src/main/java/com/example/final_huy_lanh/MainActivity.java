    package com.example.final_huy_lanh;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MediaPlayer player;
    private String text ;
    private static final int RESULT = 1 ;
    ImageButton vn , en ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final LottieAnimationView btSay= (LottieAnimationView) findViewById(R.id.logo_voice);
        vn = (ImageButton) findViewById(R.id.vn);
        en = (ImageButton) findViewById(R.id.en);
        vn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLanguge("vn");
            }
        });
        en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLanguge("en");
            }
        });
        btSay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSpeech();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT && resultCode == RESULT_OK){
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            //Uppercase String
            text = matches.get(0).toString().toLowerCase();
            System.out.println(text);
            openAnother(text);
        }
    }
    public void setLanguge(String localtion){
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            configuration.setLocale(new Locale(localtion.toLowerCase()));
        }else{
            configuration.locale = new Locale(localtion.toLowerCase());
        }
        resources.updateConfiguration(configuration, dm);
    }
    //getSpeech
    public void getSpeech(){
        Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL , RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech");
        startActivityForResult(speechIntent , RESULT);
    }
    // Check name app want open
    public void openAnother(String speechText){
        switch (speechText){
                // 1.Open FaceBook
            case "open facebook" :
            case "facebook" :
            case "mở facebook":
                setAudio(R.raw.vn_openfacebook , R.raw.en_openfacebook ,"com.facebook.katana");
                break;
                   // 2.Open Mail
            case "open mail" :
            case "mail" :
            case "Mở mail" :
                setAudio(R.raw.vn_mail , R.raw.en_mail ,"com.google.android.gm");
                break;
                //3.Open Youtube
            case "mở youtube" :
            case "open youtube" :
            case "youtube" :
                setAudio(R.raw.vn_youtube , R.raw.en_youtube, "com.google.android.youtube");
                break;
                // 4.Open Camera
            case "camera":
            case "mở camera":
            case "open camera":
                openCamera();
                break;
                // 5.open Setting
            case "cài đặt":
            case "mở cài đặt":
            case "open setting":
            case "setting":
                openSetting();
                break;
                //6.Open Instagram
            case "mở instagram":
            case "open instagram":
            case "instagram":
                setAudio(R.raw.vn_insta , R.raw.en_insta,"com.instagram.android");
                break;
                //7. Open Messnger
            case "mở messenger":
            case "open messenger":
            case "messenger":
                setAudio(R.raw.vn_mess , R.raw.en_mess,"com.facebook.orca");
                break;
                //8. Open SMS
            case "open sms":
            case "sms":
            case "tin nhắn":
            case "mở tin nhắn":
                openSms();
                break;
                //9.
            case "call":
            case "phone":
            case "gọi điện":
            case "open phone":
            case "mở bàn phím":
                openCall();
                break;
            default:
                player = MediaPlayer.create(MainActivity.this , R.raw.toichuahojc);
                player.start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run(){
                        getSpeech();
                    }
                }, 4500);
        }
    }
    // Get audio before open app
    public void setAudio(int audioVN , int audioEN , final String nameApp){
        checkAudio(audioVN, audioEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run(){
                setName(nameApp);
            }
        }, 2000);
    }

    // Get packageManager in folder app when call an app
    public void setName(String nameApp){
        Intent openApp = getPackageManager().getLaunchIntentForPackage(nameApp);
        startActivity(openApp);
    }
    //Click call id card follow name card
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting : openSetting(); break;
            case R.id.camera : openCamera(); break;
            case R.id.call : openCall(); break;
            case R.id.mess : setAudio(R.raw.vn_mess, R.raw.en_mess ,"com.facebook.orca"); break;
            case R.id.facebook : setAudio(R.raw.vn_openfacebook , R.raw.en_openfacebook ,"com.facebook.katana"); break;
            case R.id.youtube : setAudio(R.raw.vn_youtube , R.raw.en_youtube ,"com.google.android.youtube"); break;
            case R.id.mail : setAudio(R.raw.vn_mail , R.raw.en_mail,"com.google.android.gm"); break;
            case R.id.ig : setAudio(R.raw.vn_insta, R.raw.en_insta ,"com.instagram.android"); break;
            case R.id.sms: openSms(); break;
            default:break;
        }
    }
    public void openCamera(){
        checkAudio(R.raw.vn_camera , R.raw.en_camera);
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);
    }
    public void openSetting(){
        checkAudio(R.raw.vn_setting, R.raw.en_setting);
        startActivity(new Intent(Settings.ACTION_SETTINGS));
    }
    public void openCall(){
        checkAudio(R.raw.vn_phone , R.raw.en_phone);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        startActivity(intent);
    }
    public void openSms(){
        checkAudio(R.raw.vn_sms , R.raw.en_sms);
        String getsms = "Đồ án cuối kì !!!";
        Intent intent = new Intent(Intent.ACTION_SENDTO , Uri.parse("sms: 0369950323" ));
        intent.putExtra(intent.EXTRA_TEXT , getsms);
        startActivity(intent);
    }
    public void checkAudio(int audioVN , int audioEN){
        if(Locale.getDefault().getDisplayLanguage().toString().equals("English")){
            player = MediaPlayer.create(this , audioEN);
            player.start();
        }else {
            player = MediaPlayer.create(this , audioVN);
            player.start();
        }
    }

}