package com.example.laba3.ui.city_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CityListViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CityListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is city list fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}