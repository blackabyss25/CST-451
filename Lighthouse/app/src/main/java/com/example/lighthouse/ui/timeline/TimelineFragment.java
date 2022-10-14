package com.example.lighthouse.ui.timeline;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
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
import com.example.lighthouse.databinding.FragmentTimelineBinding;
import com.example.lighthouse.model.Journal;
import com.example.lighthouse.model.JournalEntries;


import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TimelineFragment extends Fragment {

    private FragmentTimelineBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Toast.makeText(getContext(), "Timeline View created",Toast.LENGTH_SHORT).show();
        TimelineViewModel timelineViewModel = new ViewModelProvider(this).get(TimelineViewModel.class);
        binding = FragmentTimelineBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView textView = binding.textTimeline;
        timelineViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        TextView noMemoriesYetTextView = binding.noMemoriesYetTextView;
        LinearLayout timelineJournalEntries = binding.getRoot().findViewById(R.id.timelineMemories);
        JournalEntries journalEntries = new JournalEntries();
        ArrayList<Journal> entries = journalEntries.getJournalEntries();


        //Create Dialog Box to display details of journal entry
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.journal_entry_dialog, null);
        TextView currentFeeling, resultantActions, what, where, noticed, whenIFelt, actionsTaken, theThoughtsThatCameToMind,
                appropriateReaction, isTheSituationControllable, canITolerateIt, doINeedToSetABoundary, boundaryToSet;
        Button closeButton;
        //Create elements of dialog
        ArrayList<TextView> textViews = new ArrayList<>();
        LinearLayout ll = mView.findViewById(R.id.dialogLinearLayout);

        //Bind to View
        currentFeeling = mView.findViewById(R.id.currentFeelingTextView);
        resultantActions =  mView.findViewById(R.id.resultantActionsTextView);
        what = mView.findViewById(R.id.whatTextView);
        where = mView.findViewById(R.id.whereTextView);
        noticed = mView.findViewById(R.id.noticedTextView);
        whenIFelt = mView.findViewById(R.id.whenIFeltTextView);
        actionsTaken = mView.findViewById(R.id.actionsTakenTextView);
        theThoughtsThatCameToMind = mView.findViewById(R.id.theThoughtsThatCameToMindTextView);
        appropriateReaction = mView.findViewById(R.id.appropriateReactionTextView);
        isTheSituationControllable = mView.findViewById(R.id.isTheSituationControllableTextView);
        canITolerateIt = mView.findViewById(R.id.canITolerateItTextView);
        doINeedToSetABoundary = mView.findViewById(R.id.boundaryToSetTextView);
        boundaryToSet = mView.findViewById(R.id.boundaryToSetTextView);
        closeButton = mView.findViewById(R.id.closeButton);










        //For when there are no memories
        if(journalEntries.getJournalEntries().size() == 0){
            noMemoriesYetTextView.setText("You don't currently have any journal entries");
        }

        //--------------------- On Create View, load all previously written affirmations into the linear layout for viewing ---------------------------------------
        //System.out.println("journalEntries.getAllEntries().size:" + journalEntries.getAllEntries().size());
        if (journalEntries.getJournalEntries() != null) {
            for (Journal j : entries) {
                System.out.println("created timeline entry for id: "+j.getId());

                TextView journal = new TextView(getContext());
                journal.setText("I was " + j.getCurrentFeeling());
                journal.setGravity(Gravity.CENTER);
                journal.setPadding(100, 10, 100, 10);

                Button jb = new Button(getContext());
                jb.setId(j.getId());
                jb.setText("I was " + j.getCurrentFeeling());
                jb.setGravity(Gravity.CENTER);
                jb.setPadding(100, 10, 100, 10);
                //Choose a different color for the button based on the emotion experienced for ease of viewing and easier trend assessment
                if(j.getCurrentFeeling().equals("happy")){
                    jb.setBackgroundColor(Color.GREEN);
                }
                else if(j.getCurrentFeeling().equals("mad")){
                    jb.setBackgroundColor(Color.RED);
                }
                else {
                    jb.setBackgroundColor(Color.GRAY);
                }
                jb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("v: " + v.getId());

                        //Put the textviews into a list for ease
                        textViews.add(currentFeeling);
                        textViews.add(resultantActions);
                        textViews.add(what);
                        textViews.add(where);
                        textViews.add(noticed);
                        textViews.add(whenIFelt);
                        textViews.add(actionsTaken);
                        textViews.add(theThoughtsThatCameToMind);
                        textViews.add(appropriateReaction);
                        textViews.add(isTheSituationControllable);
                        textViews.add(canITolerateIt);
                        textViews.add(doINeedToSetABoundary);
                        textViews.add(boundaryToSet);

                        //Set Text fields to journal entry content
                        Journal e = journalEntries.getEntry(v.getId());
                        System.out.println(e.toString());
                        currentFeeling.setText(e.getCurrentFeeling());
                        resultantActions.setText(e.getResultantActions());
                        what.setText(e.getWhat());
                        where.setText(e.getWhere());
                        noticed.setText(e.getNoticed());
                        whenIFelt.setText(e.getWhenIFelt());
                        actionsTaken.setText(e.getActionsTaken());
                        theThoughtsThatCameToMind.setText(e.getTheThoughtsThatCameToMind());
                        appropriateReaction.setText(e.getAppropriateReaction());
                        isTheSituationControllable.setText(e.getIsTheSituationControllable());
                        canITolerateIt.setText(e.getCanITolerateIt());
                        doINeedToSetABoundary.setText(e.getDoINeedToSetABoundary());
                        boundaryToSet.setText(e.getBoundaryToSet());

                        //Attach all textviews to LinearLayout

                        for(TextView tv : textViews){
                            //ll.addView(tv);
                            System.out.println("tv.getText(): "+tv.getText());
                        }

                        dialogBuilder.setView(mView);
                        AlertDialog dialog = dialogBuilder.create();
                        dialog.show();
                        closeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Close the dialog
                                dialog.dismiss();
                            }
                        });
                    }
                });
                timelineJournalEntries.addView(jb);
            }
        }




        return root;
    }

    @Override
    public void onDestroyView() {
        //Toast.makeText(getContext(), "Timeline View destroyed",Toast.LENGTH_SHORT).show();
        super.onDestroyView();
        binding = null;
    }
}