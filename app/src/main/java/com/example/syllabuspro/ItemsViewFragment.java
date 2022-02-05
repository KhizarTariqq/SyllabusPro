package com.example.syllabuspro;

import android.util.Log;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.navtest.R;
import com.example.navtest.databinding.ItemsViewFragmentBinding;

import java.util.ArrayList;

public class ItemsViewFragment extends Fragment {

    private ItemsViewViewModel mViewModel;
    private ItemsViewFragmentBinding binding;
    private ArrayList<SyllabusItem> itemsList;
    private String courseName;

    public static ItemsViewFragment newInstance() {
        return new ItemsViewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        Log.d("New fragment", "testing");
        binding = ItemsViewFragmentBinding.inflate(inflater, container, false);
        Toolbar toolbar = binding.getRoot().findViewById(R.id.manage_toolbarz);
        toolbar.setTitle("Syllabus items for " + this.courseName);

        // return inflater.inflate(R.layout.items_view_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ItemsViewViewModel.class);
        // TODO: Use the ViewModel
    }

    public void setItemsList(ArrayList<SyllabusItem> itemsList)
    {
        this.itemsList = itemsList;
    }

    public void setCourseName(String courseName)
    {
        this.courseName = courseName;
    }
}
