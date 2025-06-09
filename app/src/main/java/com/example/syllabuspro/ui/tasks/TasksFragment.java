package com.example.syllabuspro.ui.tasks;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.syllabuspro.Course;
import com.example.syllabuspro.R;
import com.example.syllabuspro.Task;
import com.example.syllabuspro.MainActivity;
import com.example.syllabuspro.Utils;
import com.example.syllabuspro.adapters.CoursesAdapter;
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
    private RecyclerView recyclerView;
    private TasksSharedViewModel viewModel;
    private TasksCourseAdapter courseAdapter;
    private TasksPriorityAdapter priorityAdapter;
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

        // Setup shared view model between TasksAdapters and ViewDetailsFragment
        viewModel = new ViewModelProvider(requireActivity()).get(TasksSharedViewModel.class);
        courseAdapter = new TasksCourseAdapter(this, MainActivity.getCourseList(), viewModel);
        priorityAdapter = new TasksPriorityAdapter(this, getPriorityList(), viewModel);

        // Set task recycler view
        recyclerView = root.findViewById(R.id.tasksRecyclerview);

        // Set layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Delay transition until toolbar padding is set
        postponeEnterTransition();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set padding as zero between the sort spinner and the toolbar
        Toolbar tb = requireActivity().findViewById(R.id.main_toolbar);
        int padding = Utils.dpToPx(requireContext(), 0);
        tb.setPaddingRelative(padding, padding, padding, padding);

        // Setup dialog
        AlertDialog dialog = createTaskDialog();

        ExtendedFloatingActionButton button = binding.addTaskButton;
        button.setOnClickListener(v-> showTaskDialog(dialog, view));

        // Start rendering after layout is done
        view.post(() -> startPostponedEnterTransition());
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
                R.layout.tasks_sort_spinner_item
        );
        sortAdapter.setDropDownViewResource(R.layout.tasks_sort_spinner_dropdown_item);
        spinner.setAdapter(sortAdapter);

        // Set listener
        spinner.setOnItemSelectedListener(
                getSortSpinnerListener(binding.tasksRecyclerview, courseAdapter, priorityAdapter));

        // Set dropdown menu to be a little lower
        //spinner.setDropDownVerticalOffset(Utils.dpToPx(requireContext(),10));

        // Alternative: Make the drop down under the spinner:
        spinner.post(() -> {
            int[] spinnerLocation = new int[2];
            spinner.getLocationOnScreen(spinnerLocation);
            int spinnerY = spinnerLocation[1];

            // Get the height of the action bar (toolbar)
            TypedValue tv = new TypedValue();
            int actionBarHeight = 0;
            if (spinner.getContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, spinner.getResources().getDisplayMetrics());
            }

            // Calculate offset from the spinner's position to the bottom of the action bar
            int offset = actionBarHeight - spinnerY + (spinner.getHeight() / 2);

            spinner.setDropDownVerticalOffset(offset);
        });



    }

    @Override
    public void onPause() {
        super.onPause();

        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            View outerChild = recyclerView.getChildAt(i);
            RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(outerChild);

            if (holder instanceof TasksCourseAdapter.ViewHolder ||
                holder instanceof TasksPriorityAdapter.ViewHolder) {

                RecyclerView innerRv = holder instanceof TasksCourseAdapter.ViewHolder ?
                        ((TasksCourseAdapter.ViewHolder) holder).getTasksRecyclerView() :
                        ((TasksPriorityAdapter.ViewHolder) holder).getTasksRecyclerView();
                long id = holder instanceof TasksCourseAdapter.ViewHolder ?
                        courseAdapter.getItemId(holder.getAdapterPosition()) :
                        priorityAdapter.getItemId(holder.getAdapterPosition());

                LinearLayoutManager layout = (LinearLayoutManager) innerRv.getLayoutManager();

                if (layout != null) {
                    Parcelable state = layout.onSaveInstanceState();
                    viewModel.saveScrollState(id, state);
                }
            }
        }
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

        // EditText for name and description
        descriptionEditText = layout.findViewById(R.id.input_description);

        // Setup spinners
        setupCourseSpinner(layout);
        setupPrioritySpinner(layout);

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

        // Make it so tapping the spinner makes EditText lose focus and hides the keyboard
        courseSpinner.setOnTouchListener(getClearFocusAndHideKeyboardListener(descriptionEditText));

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
                priority = mapPriority(priorityName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        // Make it so tapping the spinner makes EditText lose focus and hides the keyboard
        prioritySpinner.setOnTouchListener(getClearFocusAndHideKeyboardListener(descriptionEditText));
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
                String taskDescription = descriptionEditText.getText().toString().trim();
                if (course != null && priority != null && !taskDescription.isEmpty())
                {
                    collectTaskInput(taskDescription, priority, course, view);
                    dialog.dismiss();
                }

                else
                {
                    // TODO Give more detailed response
                    Toast toast = Toast.makeText(view.getContext(), "Input all information before proceeding", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    private void collectTaskInput(String description, Task.Priority priority, Course course, View view)
    {
        // Get task list and update it
        RecyclerView recyclerView = view.getRootView().findViewById(R.id.tasksRecyclerview);

        // Add the task to the task list and save it to storage
        MainActivity.addToTaskList(new Task(description, priority, course));
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

        priorityTypes.removeIf(type -> type.getTaskList().isEmpty());

        return priorityTypes;
    }

    private View.OnTouchListener getClearFocusAndHideKeyboardListener(EditText editText) {
        // When a spinner is tapped:
        // 1. Clear focus from the descriptionEditText, to remove the highlighted border when focused
        // 2. Hide keyboard

        return (v, event) -> {
            if (editText != null) {
                // Hide the keyboard before clearing focus
                InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

                // Then clear focus to trigger drawable change
                editText.clearFocus();
            }

            // Accessibility compliance
            v.performClick();

            return false;
        };
    }
}
