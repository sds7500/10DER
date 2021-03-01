package com.somya.appdevproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int Splash=5000;

    Animation topanim,bottomanim;
    ImageView logo;
    TextView name,slogan1,slogan2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        getSupportActionBar().hide();

        topanim= AnimationUtils.loadAnimation(this,R.anim.top_anim);
        bottomanim=AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

        logo=findViewById(R.id.image1);
        name=findViewById(R.id.textView1);
        slogan1=findViewById(R.id.textView2);
        slogan2=findViewById(R.id.textView3);

        logo.setAnimation(topanim);
        name.setAnimation(bottomanim);
        slogan1.setAnimation(bottomanim);
        slogan2.setAnimation(bottomanim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(MainActivity.this,SignUp_Activity.class);
                startActivity(i);
                finish();
            }
        },Splash);
    }
}