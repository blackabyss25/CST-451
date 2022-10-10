package com.example.lighthouse.ui.affirmations;

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
import com.example.lighthouse.databinding.FragmentAffirmationsBinding;
import com.example.lighthouse.model.Affirmation;

import java.util.ArrayList;

public class AffirmationsFragment extends Fragment {

    private FragmentAffirmationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AffirmationsViewModel affirmationsViewModel = new ViewModelProvider(this).get(AffirmationsViewModel.class);
        binding = FragmentAffirmationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView addAffirmationTextView = binding.addAffirmation;
        Button commitButton = binding.commitButton;
        ScrollView affirmations = binding.scrollView1;
        LinearLayout buttons = binding.getRoot().findViewById(R.id.affirmationsButtonsLayout);

        ArrayList<Affirmation> currentAffirmations = null;
        //For when there are no affirmations
        if(currentAffirmations == null){
            TextView noAffirmationtsYet = binding.noAffirmationsYetText;
            noAffirmationtsYet.setText("You don't currently have any affirmations");
        }

        //------------- For Testing/future implementation of loaded affirmations
        /*
        for(int i = 0; i <= 50; i++){
            Button nb = new Button(this.getContext());
            nb.setId(i+1);
            nb.setText("Button " + (i+1));
            nb.setOnClickListener(v -> Toast.makeText(getContext(),"You clicked button " + v.getId(), Toast.LENGTH_SHORT).show());
            nb.setBackgroundColor(Color.GREEN);
            buttons.addView(nb);
        }
        */
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}