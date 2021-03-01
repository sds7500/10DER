package com.somya.appdevproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class gadgetActivity extends AppCompatActivity {

    Button gadget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gadget);
        gadget=findViewById(R.id.gadgetBtn);

        gadget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(gadgetActivity.this,createTenderActivity.class);
                intent.putExtra("type","gadget");
                startActivity(intent);
            }
        });
    }
}