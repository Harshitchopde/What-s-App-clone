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
import com.example.signupsignin.Model.Users;
import com.example.signupsignin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public
class SignUpPage extends AppCompatActivity {
    FirebaseAuth mAuth;
    Button registerBtn;
//    private static final String TAG = "SignInPage";
    EditText rNameU,rEmailU,rPasswordU;
    TextView GotoLogin;
    FirebaseUser firebaseUser;
    FirebaseDatabase database;

    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        registerBtn = findViewById(R.id.registerbtn);
        rNameU = findViewById(R.id.rName);
        rEmailU = findViewById(R.id.rEmail);
        rPasswordU = findViewById(R.id.rPassword);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser!=null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        GotoLogin = findViewById(R.id.clickGOloginHere);
        GotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignInPage.class));
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                String Name = rNameU.getText().toString().trim();
                final String Email = rEmailU.getText().toString().trim();
                final String Password = rPasswordU.getText().toString().trim();
                if(TextUtils.isEmpty(Name)){
                    rNameU.setError("Name is Require");
                    return;
                }
                if (TextUtils.isEmpty(Email)){
                    rEmailU.setError("Email is Require");
                    return;
                }
                if(TextUtils.isEmpty(Password)){
                    rPasswordU.setError("Password is Require");
                    return;
                }
                if (Password.length()<6){
                    rPasswordU.setError("Password must be greater than 6 char ");
                }
                mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public
                    void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Users users = new Users(Name,Email,"");
                            String id = mAuth.getCurrentUser().getUid();
                            database.getReference().child("user/"+id).setValue(users);
                            Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                            Toast.makeText(SignUpPage.this, "SuccessFully User is created ", Toast.LENGTH_SHORT).show();
                            startActivity(intent);

                        }
                        else{
                            Toast.makeText(SignUpPage.this, "error"+task.getException(), Toast.LENGTH_SHORT).show();
//                            Log.e(TAG, "onComplete: Register fail due to :"+task.getException().toString().trim() );
                        }
                    }
                });
            }
        });


    }
}