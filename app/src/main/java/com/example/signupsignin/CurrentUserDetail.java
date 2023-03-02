package com.example.signupsignin;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.signupsignin.Model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public
class CurrentUserDetail {
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    String user_name, user_email, user_pic;

    public
    void firebaseGetCurrentUserDetail() {
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference("user/" + mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public
            void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                user_name = users.getUsername();
                user_email = users.getUserEmail();
                user_pic = users.getProfilePic();
                Log.e(TAG, "onDataChange: " +user_pic);

            }

            @Override
            public
            void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}