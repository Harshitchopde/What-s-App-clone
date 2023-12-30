package com.example.signupsignin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.signupsignin.Adapters.FragmentPageAdapter;


public
class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    FragmentPageAdapter fragmentPageAdapter;


    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewpager);
         fragmentPageAdapter = new FragmentPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentPageAdapter);

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