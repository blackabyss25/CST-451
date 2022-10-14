package com.example.lighthouse.ui.affirmations;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lighthouse.R;
import com.example.lighthouse.databinding.FragmentAffirmationsBinding;
import com.example.lighthouse.model.Affirmation;
import com.example.lighthouse.model.AffirmationEntries;
import com.example.lighthouse.model.Journal;

import java.io.*;


import java.util.ArrayList;

import static java.lang.System.in;

public class AffirmationsFragment extends Fragment {

    private FragmentAffirmationsBinding binding;
    private String filePath = "/data/data/com.example.lighthouse/files/lighthouseData/affirmationEntries.txt";
    private AffirmationEntries affirmationEntries;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //-------------------------- Bindings & Instantiations ------------------------------------------
        AffirmationsViewModel affirmationsViewModel = new ViewModelProvider(this).get(AffirmationsViewModel.class);
        binding = FragmentAffirmationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        EditText affirmationInput = binding.addAffirmation;
        Button commitButton = binding.commitButton;
        LinearLayout affirmationsLinearLayout = binding.affirmationsLinearLayout;
        LinearLayout buttons = binding.affirmationsLinearLayout;
        TextView noAffirmationsYet = binding.noAffirmationsYetText;
        affirmationEntries = new AffirmationEntries();
        //-------------------------- End of Bindings & Instantiations -----------------------------------

        //For when there are no affirmations
        if (affirmationEntries.getAffirmations() == null && affirmationEntries.getSize() == 0) {
            noAffirmationsYet.setText("You don't currently have any affirmations");
        }

        //--------------------- On Create View, load all previously written affirmations into the linear layout for viewing ---------------------------------------
        if (affirmationEntries.getAffirmations() != null) {
            for (Affirmation a : affirmationEntries.getAffirmations()) {
                System.out.println();
                TextView affirmation = new TextView(getContext());
                affirmation.setText(a.getText());
                affirmation.setGravity(Gravity.CENTER);
                affirmation.setPadding(100, 10, 100, 10);
                if (a.getId() % 2 == 0) {
                    affirmation.setBackgroundColor(Color.CYAN);
                } else {
                    affirmation.setBackgroundColor(Color.GRAY);
                }
                affirmationsLinearLayout.addView(affirmation);
            }
        }
        //---------------------------------------------------------------------------------------------------------------------------------------------------------

        //---------------- Set commit button functionality -----------------------
        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initial if check is to make sure they don't submit an empty affirmation.
                if (!affirmationInput.getText().toString().equals("")) {
                    //Save Affirmation to list
                    Affirmation na = new Affirmation(affirmationInput.getText().toString());
                    System.out.println("Checking contents before id set");
                    System.out.println("--------------------------------------------------------------------------------------------------------");
                    for(Affirmation a : affirmationEntries.getAffirmations()){
                        System.out.println("a: " + a.toString());
                    }
                    System.out.println("--------------------------------------------------------------------------------------------------------");
                    na.setId(affirmationEntries.getAffirmations().size()+1);
                    System.out.println("onClick na text: " + na.getText() + " onClick na id: " + na.getId());
                    //Insert the affirmation into the persisted list
                    affirmationEntries.saveAffirmation(na);
                    //Create visual representation of the affirmation for display
                    TextView affirmation = new TextView(getContext());
                    affirmation.setText(na.getText());
                    affirmation.setGravity(Gravity.CENTER);
                    affirmation.setPadding(100, 10, 100, 10);
                    //Toast.makeText(getContext(),"affirmation.size(): " + affirmations.size(), Toast.LENGTH_SHORT).show();
                    if (affirmationEntries.getAffirmations().size() % 2 == 0) {
                        affirmation.setBackgroundColor(Color.CYAN);
                    } else {
                        affirmation.setBackgroundColor(Color.GRAY);
                    }
                    //Visuals housekeeping - adding to view, resetting any necessary components
                    affirmationsLinearLayout.addView(affirmation);
                    //Remove the notifcation that there are no affirmations
                    noAffirmationsYet.setText("");
                    //Clear out input box
                    affirmationInput.setText("");


                }
            }
        });
        //------------------------------------------------------------------------

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}