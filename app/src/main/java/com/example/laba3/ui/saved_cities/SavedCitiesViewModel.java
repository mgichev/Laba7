package com.example.laba3.ui.saved_cities;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SavedCitiesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SavedCitiesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}