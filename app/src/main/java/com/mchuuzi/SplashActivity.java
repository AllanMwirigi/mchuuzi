package com.mchuuzi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Explode;
import android.transition.Slide;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_DURATION = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logo = findViewById(R.id.img_logo);
        Button btnAppTitle = findViewById(R.id.btn_splash_text);

        Animation fromTop = AnimationUtils.loadAnimation(this, R.anim.spash_animation_fromtop);
        Animation fromBottom = AnimationUtils.loadAnimation(this, R.anim.spash_animation_frombottom);

        logo.setAnimation(fromTop);
        btnAppTitle.setAnimation(fromBottom);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                // Check if we're running on Android 5.0 or higher
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // set an exit transition // Apply activity transition
                    startActivity(i, ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this).toBundle());
                } else {
                    // Swap without transition
                    startActivity(i);
                }
//                finish(); // this messes up the transition
            }
        }, SPLASH_DURATION);

    }
}
