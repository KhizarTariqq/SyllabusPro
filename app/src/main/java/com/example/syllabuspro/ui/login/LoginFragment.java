package com.example.syllabuspro.ui.login;

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
import com.example.syllabuspro.databinding.FragmentLoginBinding;
import com.example.syllabuspro.ui.waiting_api_response.WaitingApiResponseViewModel;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;
    private FragmentLoginBinding binding;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Hide the taskbar
        ((AppCompatActivity) requireActivity()).getSupportActionBar().hide();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        ((AppCompatActivity) requireActivity()).getSupportActionBar().show();
    }

}