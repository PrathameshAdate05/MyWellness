package com.prathamesh.mywellness;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Splash extends AppCompatActivity {

    ImageView IV_Splash_Back, IV_Splash_Title;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

        IV_Splash_Back = findViewById(R.id.IV_Splash_Back);
        IV_Splash_Title = findViewById(R.id.IV_Splash_Title);

        Glide.with(this).load(R.drawable.splash_back).into(IV_Splash_Back);
        Glide.with(this).load(R.drawable.splash_title).into(IV_Splash_Title);

    }
}