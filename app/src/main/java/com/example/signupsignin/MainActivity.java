package com.example.signupsignin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.signupsignin.Adapters.FragmentPageAdapter;
import com.example.signupsignin.Adapters.RecycleAdapter;
import com.example.signupsignin.LoginActivity.SignInPage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public
class MainActivity extends AppCompatActivity {

    ViewPager viewPager;


    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentPageAdapter(getSupportFragmentManager()));

    }

    @Override
    public
    void onBackPressed() {
        Toast.makeText(this, "backpress", Toast.LENGTH_SHORT).show();
        moveTaskToBack(true);// imlt will clear the back stack

        super.onBackPressed();
    }

    @Override
    public
    boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_update_menu,menu);
        return true;
    }

    @Override
    public
    boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.profile){
            startActivity(new Intent(MainActivity.this,ProfileUpdate.class));
        }
        return  true;
    }


}