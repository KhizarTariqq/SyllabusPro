package com.example.syllabuspro.ui.courses;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CoursesViewModel extends ViewModel {

    private final MutableLiveData<Void> courseListUpdated = new MutableLiveData<>();

    public LiveData<Void> getCourseListUpdated() {
        return courseListUpdated;
    }
    public void notifyCourseListChanged() {
        courseListUpdated.setValue(null);
    }
}