package com.ad97.customcircularview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class NextActivity extends AppCompatActivity {


    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        textView = findViewById(R.id.textView);
        textView.append(getIntent().getStringExtra("type"));
    }
}
