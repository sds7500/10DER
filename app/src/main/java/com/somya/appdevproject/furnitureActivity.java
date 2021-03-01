package com.somya.appdevproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class furnitureActivity extends AppCompatActivity {

    Button furniture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_furniture);
        furniture=findViewById(R.id.furnitureBtn);

        furniture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(furnitureActivity.this,createTenderActivity.class);
                intent.putExtra("type","furniture");
                startActivity(intent);
            }
        });
    }
}