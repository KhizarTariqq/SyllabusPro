package com.example.syllabuspro.ui.tasks;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
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
import com.example.syllabuspro.MainActivity;
import com.example.syllabuspro.adapters.TasksCourseAdapter;
import com.example.syllabuspro.adapters.TasksPriorityAdapter;
import com.example.syllabuspro.databinding.FragmentTasksBinding;

import java.util.ArrayList;

public class TasksFragment extends Fragment
{
    private FragmentTasksBinding binding;
    public static Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = FragmentTasksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        context = getContext();

        // Set task recycler view
        RecyclerView recyclerView = root.findViewById(R.id.tasksRecyclerview);
        // Adapter for when sorting by course
        TasksCourseAdapter courseAdapter = new TasksCourseAdapter(MainActivity.courseList);

        // Adapter for when sorting by priority
        ArrayList<TaskPriorityType> priorityTypes= new ArrayList<TaskPriorityType>();
        priorityTypes.add(new TaskPriorityType(Task.Priority.LOW));
        priorityTypes.add(new TaskPriorityType(Task.Priority.MEDIUM));
        priorityTypes.add(new TaskPriorityType(Task.Priority.HIGH));
        priorityTypes.add(new TaskPriorityType(Task.Priority.EXTREME));
        TasksPriorityAdapter priorityAdapter = new TasksPriorityAdapter(priorityTypes);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false);
        // GridLayoutManager mLayoutManager = new GridLayoutManager(this.getContext(), 2, RecyclerView.VERTICAL, false);

        // Set adapter and layout manager
        // recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(mLayoutManager);

        // set sort spinner
        // this sets the display mode from sorting with respect to courses or priority
        Spinner spinner = root.findViewById(R.id.tasks_sort_spinner);
        ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(inflater.getContext(),
        R.array.sort_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(sortAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l)
            {
                String choice = (String) adapterView.getItemAtPosition(position);

                // If user chooses to sort by courses
                if (choice.equals("Courses"))
                {
                    recyclerView.setAdapter(courseAdapter);
                }

                // Else if user chooses to sort by priority
                else
                {
                    recyclerView.setAdapter(priorityAdapter);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        // set edit button
        ImageButton editButton = root.findViewById(R.id.tasks_edit_button);
        editButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });

        return root;
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        binding = null;
    }
}
