package com.example.lighthouse.ui.timeline;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TimelineFragment extends Fragment {

    private FragmentTimelineBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TimelineViewModel timelineViewModel = new ViewModelProvider(this).get(TimelineViewModel.class);
        binding = FragmentTimelineBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView textView = binding.textTimeline;

        timelineViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        //For each entry in the retrieved entries, create a button for it, the button will open a dialog to review
        ScrollView timelineScrollView = binding.timelineScrollView;
        LinearLayout buttons = binding.getRoot().findViewById(R.id.timelineButtons);
        JournalEntries je = new JournalEntries();
        ArrayList<Journal> entries = null;
        entries = je.getJournalEntries();

        for(Journal entry : entries){
            Button nb = new Button(this.getContext());
            nb.setId(entry.getId());
            nb.setText("You felt " + entry.getCurrentFeeling());
            nb.setOnClickListener(v -> Toast.makeText(getContext(),"You felt " + nb.getText(), Toast.LENGTH_SHORT).show());
            CharSequence text = nb.getText();
            if(nb.getText().equals("happy")){
                nb.setBackgroundColor(Color.GREEN);
            }
            else if(nb.getText().equals("mad")){
                nb.setBackgroundColor(Color.RED);
            }
            else {
                nb.setBackgroundColor(Color.GRAY);
            }
            buttons.addView(nb);
        }



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}