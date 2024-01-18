package com.example.laba3.ui.current_city;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CurrentCityViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CurrentCityViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is current city fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}