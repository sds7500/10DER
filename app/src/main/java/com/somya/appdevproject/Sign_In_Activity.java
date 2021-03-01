package com.somya.appdevproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Sign_In_Activity extends AppCompatActivity {
    public static final String TAG = "Tag";
    Spinner spinner;
    TextView login;
    EditText name,email,number,pwd,cpwd;
    Button Register;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String UserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        name=findViewById(R.id.signUsername);
        number=findViewById(R.id.signMobileNo);
        email=findViewById(R.id.signEmail);
        pwd=findViewById(R.id.signPassword);
        cpwd=findViewById(R.id.signConfirmPassword);
        Register=findViewById(R.id.signButton);
        login=findViewById(R.id.signLogin);
        spinner=findViewById(R.id.signSpinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.usertype,R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        fAuth = FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();

        /*if(fAuth.getCurrentUser()!=null){
            Intent ii=new Intent(Sign_In_Activity.this,providerActivity.class);
            startActivity(ii);
            finish();
        }*/


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Sign_In_Activity.this,SignUp_Activity.class);
                startActivity(i);
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String item=spinner.getSelectedItem().toString();
                final String Email = email.getText().toString().trim();
                String Password = pwd.getText().toString().trim();
                String ConfirmPassword = cpwd.getText().toString().trim();
                final String Number = number.getText().toString().trim();
                final String Name = name.getText().toString().trim();
                if (TextUtils.isEmpty(Name)) {
                    name.setError("*cannot be empty*");
                    return;
                }
                if (TextUtils.isEmpty(Number)) {
                    number.setError("*cannot be empty*");
                    return;
                }
                if (Number.length() != 10 || Number.charAt(0) == '0') {
                    number.setError("*Invalid Number*");
                }
                if (TextUtils.isEmpty(Email)) {
                    email.setError("*cannot be empty*");
                    return;
                }
                if (TextUtils.isEmpty(Password)) {
                    pwd.setError("*cannot be empty*");
                    return;
                }
                if (Password.length() < 6) {
                    pwd.setError("*must be atleast of 6 char*");
                    return;
                }
                if (!(ConfirmPassword.equals(Password))) {
                    cpwd.setError("*Does not match*");
                    return;
                }
                fAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Sign_In_Activity.this, "Provider Created", Toast.LENGTH_SHORT).show();
                            UserId = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fstore.collection("users").document(UserId);
                            Map<String, Object> user = new HashMap<>();
                            user.put("userName", Name);
                            user.put("mobileNumber", Number);
                            user.put("emailId", Email);
                            user.put("userType",item);
                            if(item.equals("Provider")){
                                String aoi="";
                                user.put("Area of Intrest",aoi);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: Provider created " + UserId);
                                    }
                                });
                                Intent ii = new Intent(Sign_In_Activity.this, provideroActivity.class);
                                startActivity(ii);
                                finish();
                            }
                            else{
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: requester created " + UserId);
                                    }
                                });
                                Intent ii = new Intent(Sign_In_Activity.this, requesterActivity.class);
                                startActivity(ii);
                                finish();
                            }
                        } else {
                            Toast.makeText(Sign_In_Activity.this, "Error!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Sign_In_Activity.this,SignUp_Activity.class);
                startActivity(intent);
            }
        });
    }
}