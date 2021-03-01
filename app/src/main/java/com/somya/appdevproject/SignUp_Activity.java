package com.somya.appdevproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp_Activity extends AppCompatActivity {
    TextView signIn,forgot;
    EditText email,pwd;
    Button login;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        forgot=findViewById(R.id.loginForgot);
        email=findViewById(R.id.loginEmail);
        pwd=findViewById(R.id.loginPassword);
        login=findViewById(R.id.loginLoginButton);
        signIn=findViewById(R.id.loginSignUp);

        fAuth = FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();

       /*if(fAuth.getCurrentUser()!=null){
           String UserId=fAuth.getCurrentUser().getUid();
           if(UserId.equals("Provider")){
               startActivity(new Intent(getApplicationContext(),providerActivity.class));
               finish();
           }
           else{
               startActivity(new Intent(getApplicationContext(),requesterActivity.class));
               finish();
           }
       }*/




        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp_Activity.this,forgotPassword_Activity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = email.getText().toString().trim();
                String Password = pwd.getText().toString().trim();
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
                fAuth.signInWithEmailAndPassword(Email, Password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(SignUp_Activity.this, "Success", Toast.LENGTH_SHORT).show();
                        checkUserLevel(authResult.getUser().getUid());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SignUp_Activity.this,Sign_In_Activity.class);
                startActivity(i);
            }
        });
        getSupportActionBar().setTitle("Login");
    }


    private void checkUserLevel(String uid) {
        DocumentReference df=fstore.collection("users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","onSuccess"+documentSnapshot.getData());
                if(documentSnapshot.getString("userType").equals("Provider")){
                    Intent ii = new Intent(SignUp_Activity.this, providerActivity.class);
                    startActivity(ii);
                    finish();
                }
                else{
                    Intent ii = new Intent(SignUp_Activity.this, requesterActivity.class);
                    startActivity(ii);
                    finish();
                }
            }
        });
    }
}