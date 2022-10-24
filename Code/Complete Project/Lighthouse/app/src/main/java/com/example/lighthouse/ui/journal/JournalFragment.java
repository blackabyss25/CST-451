package com.example.lighthouse.ui.journal;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.lighthouse.databinding.FragmentJournalBinding;
import com.example.lighthouse.model.Journal;
import com.example.lighthouse.model.JournalEntries;
import java.io.IOException;
import java.util.ArrayList;


public class JournalFragment extends Fragment {

    private FragmentJournalBinding binding;
    private JournalEntries journals;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentJournalBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ScrollView scrollView = binding.journalScrollView;

        //Create Journal Input Objects using EditText views
        EditText currentEmotion = binding.currentEmotionInput;
        EditText resultantActions = binding.resultantActionsInput;
        EditText what = binding.whatInput;
        EditText where = binding.whereInput;
        EditText noticed = binding.noticedInput;
        EditText actionsTaken = binding.actionsTakenInput;
        EditText when = binding.whenIFeltInput;
        EditText thoughts = binding.thoughtsInput;
        EditText appropriate = binding.appropriateReactionInput;
        EditText control = binding.controlInput;
        EditText tolerance = binding.canITolerateItInput;
        EditText boundary = binding.boundaryInput;
        TextView journalLegend = binding.journalLegend;


        //For ease, adding all the user input views to a list
        ArrayList<EditText> fields = new ArrayList<>();
        fields.add(currentEmotion);
        fields.add(resultantActions);
        fields.add(what);
        fields.add(where);
        fields.add(noticed);
        fields.add(actionsTaken);
        fields.add(when);
        fields.add(thoughts);
        fields.add(appropriate);
        fields.add(control);
        fields.add(tolerance);
        fields.add(boundary);

        //Create a submission button
        Button submit = binding.journalSubmit;

        journals = new JournalEntries();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initial if check is to make sure they don't submit an empty
                boolean allFieldsPopulated = true;
                //Check all fields are populated, assume yes, look through all of them, and if you find any that aren't set to false to prevent incomplete submission
                for(EditText et : fields){
                    if(et.getText().toString().equals("")){
                       allFieldsPopulated = false;
                    }
                }
                //Once you know that all submission fields have been populated, actually continue with saving the entry
                if(allFieldsPopulated){
                    Journal ne = new Journal();
                    ne.setCurrentFeeling(currentEmotion.getText().toString());
                    ne.setResultantActions(resultantActions.getText().toString());
                    ne.setWhat(what.getText().toString());
                    ne.setWhere(where.getText().toString());
                    ne.setNoticed(noticed.getText().toString());
                    ne.setActionsTaken(actionsTaken.getText().toString());
                    ne.setWhenIFelt(when.getText().toString());
                    ne.setTheThoughtsThatCameToMind(thoughts.getText().toString());
                    ne.setAppropriateReaction(appropriate.getText().toString());
                    ne.setTheSituationControllable(control.getText().toString());
                    ne.setCanITolerateIt(tolerance.getText().toString());
                    ne.setBoundaryToSet(boundary.getText().toString());
                    ne.setId(journals.getJournalEntries().size()+1);

                    try {
                        journals.saveEntry(ne);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //Reset text inputs to empty
                    for(EditText et : fields){
                        et.setText("");
                    }
                } else {
                    Toast.makeText(getContext(), "Please make sure you answer everything before clicking Finish",Toast.LENGTH_SHORT).show();
                }
            }
        });
        scrollView.smoothScrollTo(0,0);
        return root;
    }

    @Override
    public void onDestroyView() {
        //Toast.makeText(getContext(), "Journal View destroyed",Toast.LENGTH_SHORT).show();
        super.onDestroyView();
        binding = null;
    }
}