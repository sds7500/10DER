package com.somya.appdevproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class educationActivity extends AppCompatActivity {

    Button education;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        education=findViewById(R.id.educationBtn);

        education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(educationActivity.this,createTenderActivity.class);
                intent.putExtra("type","education");
                startActivity(intent);
            }
        });
    }
}