package com.somya.appdevproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.Year;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class createTenderActivity extends AppCompatActivity {
    EditText startdate,enddate,title,desc,tandc;
    Button create;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tender);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        startdate=findViewById(R.id.startDate);
        enddate=findViewById(R.id.endDate);
        title=findViewById(R.id.tenderTitle);
        desc=findViewById(R.id.tenderDescription);
        tandc=findViewById(R.id.tenderTc);
        final String domain=getIntent().getStringExtra("type");
        create=findViewById(R.id.btnCreate);

        Calendar scalendar=Calendar.getInstance();
        final int syear=scalendar.get(Calendar.YEAR);
        final int smonth=scalendar.get(Calendar.MONTH);
        final int sdate=scalendar.get(Calendar.DAY_OF_MONTH);

        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(createTenderActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1=i1+1;
                        String date=i2+"/"+i1+"/"+i;
                        startdate.setText(date);
                    }
                },syear,smonth,sdate);
                datePickerDialog.show();
            }
        });

        Calendar ecalendar=Calendar.getInstance();
        final int eyear=ecalendar.get(Calendar.YEAR);
        final int emonth=ecalendar.get(Calendar.MONTH);
        final int edate=ecalendar.get(Calendar.DAY_OF_MONTH);

        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(createTenderActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1=i1+1;
                        String date=i2+"/"+i1+"/"+i;
                        enddate.setText(date);
                    }
                },eyear,emonth,edate);
                datePickerDialog.show();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Ttitle=title.getText().toString().trim();
                String Tdesc=desc.getText().toString().trim();
                String Ttc=tandc.getText().toString().trim();
                String Tsdate=startdate.getText().toString().trim();
                String Tedate=enddate.getText().toString().trim();
                String UserId = firebaseAuth.getCurrentUser().getUid();


                if (TextUtils.isEmpty(Ttitle)) {
                    title.setError("*cannot be empty*");
                    return;
                }
                if (TextUtils.isEmpty(Tdesc)) {
                    desc.setError("*cannot be empty*");
                    return;
                }
                if (TextUtils.isEmpty(Ttc)) {
                    tandc.setError("*cannot be empty*");
                    return;
                }
                if (TextUtils.isEmpty(Tsdate)) {
                    startdate.setError("*cannot be empty*");
                    return;
                }
                if (TextUtils.isEmpty(Tedate)) {
                    enddate.setError("*cannot be empty*");
                    return;
                }
                if(domain.equals("clothing")) {
                    DocumentReference documentReference = firebaseFirestore.collection("clothing").document();
                    Map<String, Object> Clothing = new HashMap<>();
                    Clothing.put("UserId", UserId);
                    Clothing.put("Title", Ttitle);
                    Clothing.put("startDate", Tsdate);
                    Clothing.put("endDate", Tedate);
                    Clothing.put("Description", Tdesc);
                    Clothing.put("Tandc", Ttc);
                    Clothing.put("id", documentReference.getId());


                    documentReference.set(Clothing).addOnSuccessListener(new OnSuccessListener<Void>() {

                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(createTenderActivity.this, "data added", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(createTenderActivity.this,clothingActivity.class));
                            finish();
                        }
                    });
                }
                else if(domain.equals("furniture")) {
                    DocumentReference documentReference = firebaseFirestore.collection("furniture").document();
                    Map<String, Object> Clothing = new HashMap<>();
                    Clothing.put("UserId", UserId);
                    Clothing.put("Title", Ttitle);
                    Clothing.put("startDate", Tsdate);
                    Clothing.put("endDate", Tedate);
                    Clothing.put("Description", Tdesc);
                    Clothing.put("Tandc", Ttc);
                    Clothing.put("id", documentReference.getId());


                    documentReference.set(Clothing).addOnSuccessListener(new OnSuccessListener<Void>() {

                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(createTenderActivity.this, "data added", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(createTenderActivity.this,furnitureActivity.class));
                            finish();
                        }
                    });
                }
                else if(domain.equals("education")) {
                    DocumentReference documentReference = firebaseFirestore.collection("education").document();
                    Map<String, Object> Clothing = new HashMap<>();
                    Clothing.put("UserId", UserId);
                    Clothing.put("Title", Ttitle);
                    Clothing.put("startDate", Tsdate);
                    Clothing.put("endDate", Tedate);
                    Clothing.put("Description", Tdesc);
                    Clothing.put("Tandc", Ttc);
                    Clothing.put("id", documentReference.getId());


                    documentReference.set(Clothing).addOnSuccessListener(new OnSuccessListener<Void>() {

                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(createTenderActivity.this, "data added", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(createTenderActivity.this,educationActivity.class));
                            finish();
                        }
                    });
                }
                else if(domain.equals("gadget")) {
                    DocumentReference documentReference = firebaseFirestore.collection("gadget").document();
                    Map<String, Object> Clothing = new HashMap<>();
                    Clothing.put("UserId", UserId);
                    Clothing.put("Title", Ttitle);
                    Clothing.put("startDate", Tsdate);
                    Clothing.put("endDate", Tedate);
                    Clothing.put("Description", Tdesc);
                    Clothing.put("Tandc", Ttc);
                    Clothing.put("id", documentReference.getId());


                    documentReference.set(Clothing).addOnSuccessListener(new OnSuccessListener<Void>() {

                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(createTenderActivity.this, "data added", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(createTenderActivity.this,gadgetActivity.class));
                            finish();
                        }
                    });
                }


            }
        });
    }
}