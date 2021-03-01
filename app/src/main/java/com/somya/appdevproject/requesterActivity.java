package com.somya.appdevproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class requesterActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottom;
    private static final String TAG = "requesterActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requester);
        getSupportActionBar().setTitle("Requester");

        bottom=findViewById(R.id.bottomNav);

        bottom.setOnNavigationItemSelectedListener(this);
        bottom.setSelectedItemId(R.id.Home);

    }

    contact_fragment contact=new contact_fragment();
    create_fragment create=new create_fragment();
    home_fragment home=new home_fragment();
    profile_fragment profile=new profile_fragment();


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.Home:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,home).commit();
                return true;

            case R.id.Tender:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,create).commit();
                return true;

            case R.id.Contact:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,contact).commit();
                return true;

            case R.id.Profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,profile).commit();
                return true;
        }
        return true;
    }
}