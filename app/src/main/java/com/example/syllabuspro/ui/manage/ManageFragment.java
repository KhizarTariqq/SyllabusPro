package com.example.syllabuspro.ui.manage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.navtest.R;
import com.example.navtest.databinding.FragmentManageBinding;
import com.example.syllabuspro.Course;
import com.example.syllabuspro.CustomAdapter;
import com.example.syllabuspro.MainActivity;

import java.util.ArrayList;

public class ManageFragment extends Fragment {

    private ManageViewModel manageViewModel;
    private FragmentManageBinding binding;
    private ArrayList <Course> courseList = new ArrayList<Course>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        manageViewModel =
                new ViewModelProvider(this).get(ManageViewModel.class);

        binding = FragmentManageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initializing list view with the custom adapter
        // ArrayList <Course> itemList = new ArrayList<Course>();


        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        CustomAdapter adapter = new CustomAdapter(courseList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);

        // final TextView textView = binding.textNotifications;
        // manageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
        //     @Override
        //     public void onChanged(@Nullable String s) {
        //         textView.setText(s);
        //     }
        // });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}