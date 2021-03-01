package com.somya.appdevproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class furnitureActive_Activity extends AppCompatActivity {
    private static final String TAG = "abc";
    FirebaseFirestore fstore=FirebaseFirestore.getInstance();
    CollectionReference tenderReff=fstore.collection("furniture");

    private tenderAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_furniture_active_);

        setUpRecyclerViewf();
    }

    private void setUpRecyclerViewf() {
        FirestoreRecyclerOptions<activeTender> options = new FirestoreRecyclerOptions.Builder<activeTender>()
                .setQuery(tenderReff, activeTender.class).build();
        adapter = new tenderAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.activeRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
