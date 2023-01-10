package com.example.signupsignin.LoginActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.signupsignin.MainActivity;
import com.example.signupsignin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public
class SignInPage extends AppCompatActivity {
    Button loginBtn;
    EditText uPassword,uEmail;
    TextView gotoRegister;
    FirebaseAuth  mAuth;


    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);
        loginBtn = findViewById(R.id.loginbtn);
        uPassword = findViewById(R.id.UserPassword);
        uEmail = findViewById(R.id.UserEmail);
        mAuth = FirebaseAuth.getInstance();
        gotoRegister = findViewById(R.id.clickGoForNewHere);
        gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUpPage.class));
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                final String Email = uEmail.getText().toString().trim();
                final String Password = uPassword.getText().toString().trim();
                if (TextUtils.isEmpty(Email)){
//                    Log.e(TAG, "onClick: Email is Require");
                    uEmail.setError("Email is Require");
                    return;
                }
                if(TextUtils.isEmpty(Password)){
                    uPassword.setError("Password is Require");
//                    Log.e(TAG, "onClick: Password is Require");
                    return;
                }
                if (Password.length()<6){
//                    Log.e(TAG, "onClick: Password is too short" );
                    uPassword.setError("Password must be greater than 6 char ");
                }
                mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public
                    void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(SignInPage.this, "Login SuccessFull", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else {
                            Toast.makeText(SignInPage.this, "Login fail :"+task.getException().toString().trim(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });




    }
}