package com.example.syllabuspro.ui.tasks;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.syllabuspro.R;
import com.example.syllabuspro.Task;
import com.example.syllabuspro.adapters.CustomAdapter;
import com.example.syllabuspro.MainActivity;
import com.example.syllabuspro.adapters.TasksAdapter;
import com.example.syllabuspro.databinding.FragmentManageBinding;
import com.example.syllabuspro.MainActivity;
import com.example.syllabuspro.databinding.TasksFragmentBinding;

import java.util.ArrayList;

public class TasksFragment extends Fragment
{
    private TasksFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = TasksFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Get task recycler view
        RecyclerView recyclerView = root.findViewById(R.id.tasksRecyclerview);
        TasksAdapter adapter = new TasksAdapter(MainActivity.taskList);

        // LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this.getContext(), 2, RecyclerView.VERTICAL, false);

        // Add border between items
        DividerItemDecoration mDividerItemDecorationVertical = new DividerItemDecoration(recyclerView.getContext(), RecyclerView.VERTICAL);
        DividerItemDecoration mDividerItemDecorationHorizontal = new DividerItemDecoration(recyclerView.getContext(), RecyclerView.HORIZONTAL);
        recyclerView.addItemDecoration(mDividerItemDecorationVertical);
        recyclerView.addItemDecoration(mDividerItemDecorationHorizontal);

        // Set adapter and layout manager
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(mLayoutManager);

        return root;
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        binding = null;
    }
}