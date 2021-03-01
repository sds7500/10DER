package com.somya.appdevproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class provideroActivity extends AppCompatActivity {

    Spinner aoi;
    Button baoi;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providero);

        fAuth = FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();


        baoi=findViewById(R.id.aoibtn);
        aoi=findViewById(R.id.aoin);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.Aoi,R.layout.support_simple_spinner_dropdown_item);
        aoi.setAdapter(adapter);

        baoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String area=aoi.getSelectedItem().toString();
                String UserId=fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fstore.collection("users").document(UserId);

                Map<String,Object> user=new HashMap<>();
                user.put("Area of Intrest",area);
                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
                startActivity(new Intent(provideroActivity.this,providerActivity.class));
            }
        });
    }
}