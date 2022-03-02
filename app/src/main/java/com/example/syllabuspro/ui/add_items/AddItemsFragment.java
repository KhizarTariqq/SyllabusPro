package com.example.syllabuspro.ui.add_items;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.syllabuspro.Course;
import com.example.syllabuspro.MainActivity;
import com.example.syllabuspro.R;
import com.example.syllabuspro.SyllabusItem;
import com.example.syllabuspro.adapters.AddCourseAdapter;
import com.example.syllabuspro.adapters.CustomAdapter;
import com.example.syllabuspro.databinding.AddItemsFragmentBinding;
import com.example.syllabuspro.ui.manage.ManageFragment;

import java.util.ArrayList;

public class AddItemsFragment extends Fragment {

    private AddItemsViewModel mViewModel;
    private AddItemsFragmentBinding binding;
    public ArrayList<SyllabusItem> syllabusItems;

    public static AddItemsFragment newInstance() {
        return new AddItemsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        binding = AddItemsFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // set toolbar name and back arrow
        Toolbar toolbar = binding.getRoot().findViewById(R.id.manage_toolbar);
        toolbar.setTitle("Enter syllabus items: ");
        toolbar.setNavigationIcon(R.drawable.ic_action_name);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MainActivity.fragmentManager.popBackStackImmediate();
            }
        });

        RecyclerView addItemsRecyclerView = root.findViewById(R.id.addCourseRecyclerView);


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false);
        syllabusItems = MainActivity.syllabusItems;
        AddCourseAdapter adapter = new AddCourseAdapter(syllabusItems);

        // Add border between items
        // DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(addItemsRecyclerView.getContext(),
        //      mLayoutManager.getOrientation());
        // addItemsRecyclerView.addItemDecoration(mDividerItemDecoration);

        addItemsRecyclerView.setAdapter(adapter);
        addItemsRecyclerView.setLayoutManager(mLayoutManager);

        root.findViewById(R.id.add_item_button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Check if all input fields are completed
                // then create the SyllabusItem object from the information provided
                // once the object is created then add the new item to the list

                RecyclerView recyclerView = binding.getRoot().findViewById(R.id.addCourseRecyclerView);
                AddCourseAdapter adapter = (AddCourseAdapter) recyclerView.getAdapter();
                ArrayList<SyllabusItem> syllabusItems = adapter.getSyllabusItems();

                if (syllabusItems.size() == 0 || syllabusItems.get(syllabusItems.size() - 1).notNull())
                {
                    syllabusItems.add(new SyllabusItem());
                    adapter.notifyDataSetChanged();
                }

                else
                {
                    Toast toast = Toast.makeText(getContext(), "Complete the current item before adding a new one.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        root.findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                RecyclerView recyclerView = binding.getRoot().findViewById(R.id.addCourseRecyclerView);
                AddCourseAdapter adapter = (AddCourseAdapter) recyclerView.getAdapter();
                ArrayList<SyllabusItem> syllabusItems = adapter.getSyllabusItems();

                if (syllabusItems.get(syllabusItems.size() - 1).notNull())
                {
                    MainActivity.syllabusItems = new ArrayList<SyllabusItem>();
                    MainActivity.saveCourses();
                    MainActivity.fragmentManager.popBackStackImmediate();
                }

                else
                {
                    Toast toast = Toast.makeText(getContext(), "Complete the last item before continuing.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddItemsViewModel.class);
        // TODO: Use the ViewModel
    }
}