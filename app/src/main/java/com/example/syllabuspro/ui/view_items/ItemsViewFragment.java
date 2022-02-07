package com.example.syllabuspro.ui.view_items;

import android.util.Log;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.example.navtest.R;
import com.example.navtest.databinding.ItemsViewFragmentBinding;
import com.example.syllabuspro.CustomAdapter;
import com.example.syllabuspro.MainActivity;
import com.example.syllabuspro.SyllabusItem;

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
        binding = ItemsViewFragmentBinding.inflate(inflater, container, false);
        Toolbar toolbar = binding.getRoot().findViewById(R.id.manage_toolbarz);
        RecyclerView recyclerView = container.findViewById(R.id.recyclerView);
        String courseName = (String) recyclerView.getTag();

        toolbar.setTitle(courseName + " syllabus items");
        toolbar.setNavigationIcon(R.drawable.ic_action_name);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                MainActivity.fragmentManager.popBackStackImmediate();
            }
        });
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
