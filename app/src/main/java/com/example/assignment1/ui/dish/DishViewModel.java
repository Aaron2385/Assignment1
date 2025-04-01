package com.example.assignment1.ui.dish;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DishViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DishViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}