package com.example.signupsignin.Fragment;



import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.signupsignin.Adapters.RecycleAdapter;

import com.example.signupsignin.Model.Users;
import com.example.signupsignin.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public
class MainActivityFragment extends Fragment {
    private static final String TAG = "MainActivityFragment";
//    public String name_of_User;

    public
    MainActivityFragment() {
        // Required empty public constructor
    }

    FirebaseDatabase database;
    RecycleAdapter recycleAdapter;
    ArrayList<Users> arrayList = new ArrayList<>();
    RecyclerView recyclerView;
    FirebaseAuth mauth;



    @Override
    public
    View onCreateView(LayoutInflater inflater, ViewGroup container,
                      Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_activity, container, false);
        mauth =FirebaseAuth.getInstance();
        getFetchData();
        recycleAdapter = new RecycleAdapter(getContext(), arrayList);
        recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recycleAdapter);
        return view;
    }
    private void getFetchData(){
        database = FirebaseDatabase.getInstance();
//        Log.e(TAG, "onCreateView: running database" );

        database.getReference().child("user").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public
            void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Users users = dataSnapshot.getValue(Users.class);
                    if(users.getUserEmail().equals(mauth.getCurrentUser().getEmail())){
                        continue;
                    }

                    arrayList.add(users);
                }

                recycleAdapter.notifyDataSetChanged();


            }


            @Override
            public
            void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: "+error.getDetails() );
                Log.e(TAG, "onCancelled: "+error.getMessage());


            }
        });
    }
}