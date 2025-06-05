package com.example.syllabuspro.ui.waiting_api_response;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.syllabuspro.R;
import com.example.syllabuspro.databinding.FragmentCoursesBinding;
import com.example.syllabuspro.databinding.FragmentWaitingApiResponseBinding;
import com.example.syllabuspro.ui.courses.CoursesViewModel;

public class WaitingApiResponseFragment extends Fragment {

    private FragmentWaitingApiResponseBinding binding;
    private WaitingApiResponseViewModel waitingApiResponseViewModel;

    public static WaitingApiResponseFragment newInstance() {
        return new WaitingApiResponseFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        waitingApiResponseViewModel = new ViewModelProvider(this).get(WaitingApiResponseViewModel.class);
        binding = FragmentWaitingApiResponseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ((AppCompatActivity) requireActivity()).getSupportActionBar().hide();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}