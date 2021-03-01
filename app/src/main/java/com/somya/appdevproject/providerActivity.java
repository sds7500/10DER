package com.somya.appdevproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class providerActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottom;
    private static final String TAG = "providerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        getSupportActionBar().setTitle("Provider");

        bottom=findViewById(R.id.bottomNav);

        bottom.setOnNavigationItemSelectedListener(this);
        bottom.setSelectedItemId(R.id.Home);
    }

    contact_fragment contact=new contact_fragment();
    provide_fragment create=new provide_fragment();
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