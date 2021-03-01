package com.somya.appdevproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class clothingActivity extends AppCompatActivity {

    Button clothing,active;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing);
        clothing=findViewById(R.id.clothingBtn);
        active=findViewById(R.id.userclothactive);

        active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(clothingActivity.this,userClothActive.class));
            }
        });
        clothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(clothingActivity.this,createTenderActivity.class);
                intent.putExtra("type","clothing");
                startActivity(intent);
            }
        });
    }
}