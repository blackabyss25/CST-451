package com.example.lighthouse.ui.journal;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.lighthouse.databinding.FragmentJournalBinding;
import com.example.lighthouse.model.Journal;
import com.example.lighthouse.model.JournalEntries;
import java.io.IOException;


public class JournalFragment extends Fragment {

    private FragmentJournalBinding binding;
    private JournalEntries memories;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Toast.makeText(getContext(), "Journal View created",Toast.LENGTH_SHORT).show();
        JournalViewModel journalViewModel = new ViewModelProvider(this).get(JournalViewModel.class);
        binding = FragmentJournalBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        EditText currentEmotion = binding.currentEmotionInput;
        EditText resultantActions = binding.resultantActionsInput;
        EditText what = binding.whatInput;
        EditText where = binding.whereInput;
        EditText noticed = binding.noticedInput;
        EditText when = binding.whenIFeltInput;
        EditText thoughts = binding.thoughtsInput;
        EditText appropriate = binding.appropriateReactionInput;
        EditText control = binding.controlInput;
        EditText tolerance = binding.canITolerateItInput;
        EditText boundary = binding.boundaryInput;
        Button submit = binding.journalSubmit;
        memories = new JournalEntries();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Journal ne = new Journal();
                ne.setCurrentFeeling(currentEmotion.getText().toString());
                ne.setResultantActions(currentEmotion.getText().toString());
                ne.setWhat(currentEmotion.getText().toString());
                ne.setWhere(currentEmotion.getText().toString());
                ne.setNoticed(currentEmotion.getText().toString());
                ne.setWhenIFelt(currentEmotion.getText().toString());
                ne.setTheThoughtsThatCameToMind(currentEmotion.getText().toString());
                ne.setAppropriateReaction(currentEmotion.getText().toString());
                ne.setTheSituationControllable(currentEmotion.getText().toString());
                ne.setCanITolerateIt(currentEmotion.getText().toString());
                ne.setBoundaryToSet(currentEmotion.getText().toString());

                try {
                    memories.saveMemory(ne);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //-----------------------  For Testing -----------------------------------------------
                //Toast.makeText(getContext(), ne.toString(),Toast.LENGTH_SHORT).show();
                if(memories.getJournalEntries().size() == 0){
                    Toast.makeText(getContext(), "No journal entries",Toast.LENGTH_SHORT).show();
                }
                else {
                    for(Journal entry : memories.getJournalEntries()){
                        Toast.makeText(getContext(), entry.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
                //-----------------------  For Testing -----------------------------------------------

            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        //Toast.makeText(getContext(), "Journal View destroyed",Toast.LENGTH_SHORT).show();
        super.onDestroyView();
        binding = null;
    }
}