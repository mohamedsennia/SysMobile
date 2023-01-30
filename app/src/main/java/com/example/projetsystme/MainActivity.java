package com.example.projetsystme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
static int choice1,choice2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction fr= getSupportFragmentManager().beginTransaction();
        fr.add(R.id.fragemntcontainer,new HomeFragment());
        fr.commit();
    }
}