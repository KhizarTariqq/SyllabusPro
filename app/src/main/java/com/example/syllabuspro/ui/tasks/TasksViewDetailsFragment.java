package com.example.syllabuspro.ui.tasks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModel;

import android.widget.TextView;

import com.example.syllabuspro.R;
import com.example.syllabuspro.databinding.FragmentTasksBinding;
import com.example.syllabuspro.databinding.FragmentTasksViewDetailsBinding;

public class TasksViewDetailsFragment extends Fragment {
    private FragmentTasksViewDetailsBinding binding;
    TasksSharedViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = FragmentTasksViewDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        TextView descView = view.findViewById(R.id.task_description);
        TextView courseView = view.findViewById(R.id.task_course);
        TextView priorityView = view.findViewById(R.id.task_priority);

        viewModel = new ViewModelProvider(requireActivity()).get(TasksSharedViewModel.class);
        viewModel.getSelectedTask().observe(getViewLifecycleOwner(), task -> {
            if (task != null) {
                descView.setText(task.getDescription());
                courseView.setText(task.getCourse().getName());
                priorityView.setText(task.getPriority().toString());
            }
        });

        return view;
    }
}