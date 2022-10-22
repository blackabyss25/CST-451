package com.example.lighthouse;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.lighthouse.model.Journal;
import com.example.lighthouse.model.JournalEntries;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.lighthouse.databinding.ActivityMainBinding;

import java.io.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_timeline, R.id.navigation_journal, R.id.navigation_affirmations).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


        //--------------------- Create files for storing data --------------------------
        File mainDir = new File(MainActivity.this.getFilesDir(), "lighthouseData");
        File affirmationDir = new File(mainDir, "affirmations");
        File journalDir = new File(mainDir, "journals");
        //If the persistence files don't already exist-----create them
        if (!mainDir.exists()) {
            mainDir.mkdir();
        }
        if (!affirmationDir.exists()) {
            affirmationDir.mkdir();
        }
        if (!journalDir.exists()) {
            journalDir.mkdir();
        }
        //------------------------------------------------------------------------------
    }


}






