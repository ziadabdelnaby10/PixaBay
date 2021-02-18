package com.example.volleypicassorecycler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SplashScreen.this, "Welcome", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SplashScreen.this , MainActivity.class));
                finish();
            }
        } , 5000);


    }
}