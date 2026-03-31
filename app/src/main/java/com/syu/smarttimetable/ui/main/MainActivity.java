package com.syu.smarttimetable.ui.main;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.syu.smarttimetable.R;
import com.syu.smarttimetable.ui.recommendation.RecommendationActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button btnGoRecommendation = findViewById(R.id.btn_go_recommendation);
        btnGoRecommendation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RecommendationActivity.class));
            }
        });
    }
}