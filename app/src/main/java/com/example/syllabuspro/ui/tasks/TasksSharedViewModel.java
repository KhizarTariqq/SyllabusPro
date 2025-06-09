package com.example.syllabuspro.ui.tasks;

import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.syllabuspro.Task;

import java.util.HashMap;
import java.util.Map;

public class TasksSharedViewModel extends ViewModel {
    private final MutableLiveData<Task> selectedTask = new MutableLiveData<>();
    private final Map<Long, Parcelable> scrollStates = new HashMap<>();

    // Keeps track of the selected (outer) adapter for the
    // Tasks fragment (sort by course vs priority)
    private int selectedSpinnerIndex = 0;

    public void setSelectedTask(Task task) {
        selectedTask.setValue(task);
    }

    public LiveData<Task> getSelectedTask() {
        return selectedTask;
    }


    public void saveScrollState(long courseId, Parcelable state) {
        scrollStates.put(courseId, state);
    }

    @Nullable
    public Parcelable getScrollState(long courseId) {
        return scrollStates.get(courseId);
    }

    public void setSelectedSpinnerIndex(int index) {
        this.selectedSpinnerIndex = index;
    }

    public int getSelectedSpinnerIndex() {
        return selectedSpinnerIndex;
    }
}

