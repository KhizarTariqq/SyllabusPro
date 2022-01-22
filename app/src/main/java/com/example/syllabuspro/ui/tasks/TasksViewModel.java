package com.example.syllabuspro.ui.tasks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
public class TasksViewModel { private MutableLiveData<String> mText;

    public TasksViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is task fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
