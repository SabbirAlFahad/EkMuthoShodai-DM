package com.itbl.ekmuthoshodai;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

public class Splash extends Activity {
    Animation topAnim, botAnim;
    ImageView image;
    TextView itbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        botAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        image = findViewById(R.id.logo);
        itbl = findViewById(R.id.itbl);

        itbl.setTypeface(ResourcesCompat.getFont(this, R.font.bree_serif));

        image.setAnimation(topAnim);
        itbl.setAnimation(botAnim);

        Thread t=new Thread() {
            public void run() {
                try {
                    sleep(5000);
                    Intent i=new Intent(Splash.this, LoginActivity.class);
                    startActivity(i);
                    finish();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        t.start();
    }
}






