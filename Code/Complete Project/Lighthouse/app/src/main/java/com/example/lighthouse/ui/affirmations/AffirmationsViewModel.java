package com.example.lighthouse.ui.affirmations;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AffirmationsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AffirmationsViewModel() {
        mText = new MutableLiveData<>();





    }

    public LiveData<String> getText() {
        return mText;
    }
}