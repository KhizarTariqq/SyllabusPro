package com.example.syllabuspro.ui.tasks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.widget.TextView;

import com.example.syllabuspro.R;
import com.example.syllabuspro.databinding.FragmentTasksBinding;
import com.example.syllabuspro.databinding.FragmentTasksViewDetailsBinding;
import com.example.syllabuspro.ui.view_items.ItemsViewFragment;

public class TasksViewDetailsFragment extends Fragment {
    private FragmentTasksViewDetailsBinding binding;
    TasksSharedViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = FragmentTasksViewDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Toolbar toolbar = requireActivity().findViewById(R.id.main_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        toolbar.setNavigationOnClickListener(getBackButtonListener());

        binding.completedTaskButton.post(() -> {
            int width = binding.completedTaskButton.getWidth();
            ViewGroup.LayoutParams params = binding.removeTaskButton.getLayoutParams();
            params.width = width;
            binding.removeTaskButton.setLayoutParams(params);
        });

        TextView descView = view.findViewById(R.id.task_description);
        TextView courseView = view.findViewById(R.id.task_course);
        TextView priorityView = view.findViewById(R.id.task_priority);

        viewModel = new ViewModelProvider(requireActivity()).get(TasksSharedViewModel.class);
        viewModel.getSelectedTask().observe(getViewLifecycleOwner(), task -> {
            if (task != null)
            {
                // Set the description string with a smaller font on the text after the colon
                String descriptionString = "Description: " + task.getDescription();
                SpannableString spannableDescription = new SpannableString(descriptionString);
                spannableDescription.setSpan(
                        new AbsoluteSizeSpan(17, true),
                        13,
                        descriptionString.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );

                String courseString = "Course: " + task.getCourse().getName();
                String priorityString = "Priority: " + task.getPriority().toString();

                descView.setText(spannableDescription);
                courseView.setText(courseString);
                priorityView.setText(priorityString);

                String toolbarTitle;

                // Hide the irrelevant textView depending on which mode the spinner (sort by) was
                // on in the TasksFragment
                if (viewModel.getSelectedSpinnerIndex() == 0)
                {
                    toolbarTitle = task.getCourse().getName() + " Task Details";
                    courseView.setVisibility(View.GONE);

                }

                else
                {
                    toolbarTitle = task.getPriority().toString() + " Priority Task Details";
                    priorityView.setVisibility(View.GONE);
                }

                toolbar.setTitle(toolbarTitle);
            }
        });

        return view;
    }

    private View.OnClickListener getBackButtonListener() {
        // Listener for back button in the action bar
        return v -> {
            NavController navController = NavHostFragment.findNavController(TasksViewDetailsFragment.this);
            navController.popBackStack();
        };
    }
}