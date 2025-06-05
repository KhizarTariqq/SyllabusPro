package com.example.syllabuspro.ui.tasks;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.syllabuspro.Course;
import com.example.syllabuspro.R;
import com.example.syllabuspro.Task;
import com.example.syllabuspro.MainActivity;
import com.example.syllabuspro.adapters.TasksCourseAdapter;
import com.example.syllabuspro.adapters.TasksPriorityAdapter;
import com.example.syllabuspro.databinding.FragmentTasksBinding;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;

public class TasksFragment extends Fragment
{
    private FragmentTasksBinding binding;

    private Course course = null;
    private Task.Priority priority = null;
    private EditText nameEditText;
    private EditText descriptionEditText;
    private Spinner spinner;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = FragmentTasksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set action bar title and buttons
        requireActivity().setTitle("Tasks");
        setHasOptionsMenu(true);

        // Set task recycler view
        RecyclerView recyclerView = root.findViewById(R.id.tasksRecyclerview);

        // Adapter for when sorting by course
        TasksCourseAdapter courseAdapter = new TasksCourseAdapter(MainActivity.getCourseList());

        // Adapter for when sorting by priority
        TasksPriorityAdapter priorityAdapter = new TasksPriorityAdapter(getPriorityList());

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false);
        // GridLayoutManager mLayoutManager = new GridLayoutManager(this.getContext(), 2, RecyclerView.VERTICAL, false);

        // Set layout manager
        recyclerView.setLayoutManager(mLayoutManager);

        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        AlertDialog dialog = createTaskDialog();

        ExtendedFloatingActionButton button = binding.addTaskButton;
        button.setOnClickListener(v-> showTaskDialog(dialog, view));

    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_tasks, menu);

        MenuItem item = menu.findItem(R.id.action_sort_controls);
        View actionView = item.getActionView();

        // Access spinner from custom layout
        spinner = actionView.findViewById(R.id.tasks_sort_spinner);

        ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.sort_array,
                android.R.layout.simple_spinner_item
        );
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(sortAdapter);

        // Set listener
        spinner.setOnItemSelectedListener(
                getSortSpinnerListener(
                        binding.tasksRecyclerview,
                        new TasksCourseAdapter(MainActivity.getCourseList()),
                        new TasksPriorityAdapter(getPriorityList())
                )
        );
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_edit) {
            // handle edit button click here
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private AdapterView.OnItemSelectedListener getSortSpinnerListener(RecyclerView recyclerView,
                                                                      RecyclerView.Adapter<?> courseAdapter,
                                                                      RecyclerView.Adapter<?> priorityAdapter) {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String choice = (String) adapterView.getItemAtPosition(position);

                if (choice.equals("Courses")) {
                    recyclerView.setAdapter(courseAdapter);
                } else {
                    recyclerView.setAdapter(priorityAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Optional: handle case where nothing is selected
            }
        };
    }

    public AlertDialog createTaskDialog()
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext());
        dialogBuilder.setTitle("Add task: ");
        View layout = getLayoutInflater().inflate(R.layout.add_task_dialog, null);
        dialogBuilder.setView(layout);

        // Setup spinners
        setupCourseSpinner(layout);
        setupPrioritySpinner(layout);

        // EditText for name and description
        nameEditText = layout.findViewById(R.id.input_name);
        descriptionEditText = layout.findViewById(R.id.input_description);

        // Setup buttons
        setupDialogButtons(dialogBuilder);

        AlertDialog dialog = dialogBuilder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        return dialog;
    }

    private void setupCourseSpinner(View layout)
    {
        // set course spinner
        Spinner courseSpinner = layout.findViewById(R.id.tasks_course_spinner);
        ArrayAdapter<String> courseAdapter = new ArrayAdapter<> (requireContext(), android.R.layout.simple_spinner_dropdown_item, MainActivity.courseListToStringArray());
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(courseAdapter);

        // set course spinner click listener
        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View itemView, int position, long l)
            {
                String courseName = (String) adapterView.getItemAtPosition(position);
                Log.d("task", courseName);
                course = MainActivity.getCourseFromString(courseName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
    }

    private void setupPrioritySpinner(View layout)
    {
        // set priority spinner
        Spinner prioritySpinner = layout.findViewById(R.id.tasks_priority_spinner);
        ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.priority_array, android.R.layout.simple_spinner_item);

        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(priorityAdapter);

        // set priority spinner click listener
        prioritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View itemView, int position, long l)
            {
                String priorityName = (String) adapterView.getItemAtPosition(position);
                Log.d("task", priorityName);

                priority = mapPriority(priorityName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
    }

    private void setupDialogButtons(AlertDialog.Builder dialogBuilder)
    {
        // Continue button listener implemented later to not automatically close the dialog
        dialogBuilder.setPositiveButton("Continue", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        // Cancel button click listener
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                dialog.cancel(); // closes dialog dialogBuilder.show() // display the dialog
            }
        });
    }
    private void showTaskDialog(AlertDialog dialog, View view) {
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View innerView) {
                String taskName = nameEditText.getText().toString().trim();
                String taskDescription = descriptionEditText.getText().toString().trim();
                if (course != null && priority != null && !taskName.isEmpty() && !taskDescription.isEmpty()) {
                    collectTaskInput(taskName, taskDescription, priority, course, view);
                    dialog.dismiss();
                }
                else {
                    // TODO Give more detailed response
                    Toast toast = Toast.makeText(view.getContext(), "Input all information before proceeding", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    private void collectTaskInput(String name, String description, Task.Priority priority, Course course, View view)
    {
        // Get task list and update it
        RecyclerView recyclerView = view.getRootView().findViewById(R.id.tasksRecyclerview);

        // Add the task to the task list and save it to storage
        MainActivity.addToTaskList(new Task(name, description, priority, course));
        MainActivity.saveTaskList();

        if (recyclerView.getAdapter().getClass() == TasksCourseAdapter.class)
        {
            TasksCourseAdapter adapter = (TasksCourseAdapter) recyclerView.getAdapter();
            adapter.notifyDataSetChanged();
        }

        else if (recyclerView.getAdapter().getClass() == TasksPriorityAdapter.class)
        {
            TasksPriorityAdapter adapter = (TasksPriorityAdapter) recyclerView.getAdapter();

            adapter.setTaskPriorityList(getPriorityList());
            adapter.notifyDataSetChanged();
        }
    }

    private Task.Priority mapPriority(String name) {
        switch (name) {
            case "Low":
                return Task.Priority.LOW;
            case "Medium":
                return Task.Priority.MEDIUM;
            case "High":
                return Task.Priority.HIGH;
            case "Very High":
                return Task.Priority.VERY_HIGH;
            case "Extreme":
                return Task.Priority.EXTREME;
            default:
                throw new IllegalArgumentException("Unknown priority: " + name);
        }
    }

    public static ArrayList<TaskPriorityType> getPriorityList()
    {
        ArrayList<TaskPriorityType> priorityTypes = new ArrayList<>();
        priorityTypes.add(new TaskPriorityType(Task.Priority.LOW));
        priorityTypes.add(new TaskPriorityType(Task.Priority.MEDIUM));
        priorityTypes.add(new TaskPriorityType(Task.Priority.HIGH));
        priorityTypes.add(new TaskPriorityType(Task.Priority.VERY_HIGH));
        priorityTypes.add(new TaskPriorityType(Task.Priority.EXTREME));

        priorityTypes.removeIf(type -> type.getTaskList().isEmpty());

        return priorityTypes;
    }

}
