package com.example.mathexlist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                if(mAuth.getCurrentUser() != null)
                {
                    Intent intent = new Intent(LoadingActivity.this, StatisticsActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                Intent intent = new Intent(LoadingActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                }
            }, 1000);


    }

}