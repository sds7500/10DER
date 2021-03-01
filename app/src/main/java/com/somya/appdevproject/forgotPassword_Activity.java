package com.somya.appdevproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPassword_Activity extends AppCompatActivity {

    EditText forgotEmail;
    Button resetButton;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_);

        forgotEmail=findViewById(R.id.ForgotEmail);
        resetButton=findViewById(R.id.ResetButton);

        fAuth=FirebaseAuth.getInstance();

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email=forgotEmail.getText().toString().trim();
                if (TextUtils.isEmpty(Email)) {
                    forgotEmail.setError("*Enter your Registered mail id*");
                    return;
                }
                else{
                    fAuth.sendPasswordResetEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(forgotPassword_Activity.this, "reset link sent", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(forgotPassword_Activity.this,SignUp_Activity.class));
                            }
                            else{
                                Toast.makeText(forgotPassword_Activity.this, "Error!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}