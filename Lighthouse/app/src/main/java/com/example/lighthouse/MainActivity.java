package com.example.lighthouse;

import android.content.Context;
import android.os.Bundle;
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
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_timeline, R.id.navigation_journal, R.id.navigation_affirmations)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


        //Create files for storing data
        File file = new File(MainActivity.this.getFilesDir(), "lighthouseData");
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            File journalEntries = new File(file, "journalEntries.txt");
            File affirmationEntries = new File(file, "affirmationEntries.txt");
            FileWriter journalWriter = new FileWriter(journalEntries);
            journalWriter.flush();
            journalWriter.close();
            FileWriter affirmationWriter = new FileWriter(affirmationEntries);
            affirmationWriter.flush();
            affirmationWriter.close();
            //Toast.makeText(MainActivity.this, "Files written", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }


        //----------------  Testing getMemories --------------------------
//        JournalEntries je = new JournalEntries();
//        try {
//            ArrayList<Journal> memories = je.readAllEntries();
//            Toast.makeText(MainActivity.this, "Got " + memories.size() + " Memories", Toast.LENGTH_LONG).show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }


}






