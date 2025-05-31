package com.example.syllabuspro.ui.courses;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.syllabuspro.Course;
import com.example.syllabuspro.R;
import com.example.syllabuspro.UriUtils;
import com.example.syllabuspro.adapters.CustomAdapter;
import com.example.syllabuspro.MainActivity;
import com.example.syllabuspro.databinding.FragmentCoursesBinding;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class CoursesFragment extends Fragment {

    private CoursesViewModel coursesViewModel;
    private FragmentCoursesBinding binding;
    private RecyclerView recyclerView;
    private String directory;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        coursesViewModel = new ViewModelProvider(this).get(CoursesViewModel.class);
        binding = FragmentCoursesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initializing list view with the custom adapter
        Log.d("empty", MainActivity.getCourseList().toString());
        recyclerView = root.findViewById(R.id.recyclerView);
        CustomAdapter adapter = new CustomAdapter(MainActivity.getCourseList());

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false);

        // Add adapter and layout
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(mLayoutManager);

        // Setup addCourse Dialog
        AlertDialog dialog = createNewCourseDialog(root);
        ExtendedFloatingActionButton button = binding.addCourseButton;
        button.setOnClickListener(v -> dialog.show());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public AlertDialog createNewCourseDialog(View view)
    {
        // Set up dialog
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext());
        final EditText courseNameText = new EditText(requireContext());
        dialogBuilder.setTitle("Enter the course name: ");
        dialogBuilder.setView(courseNameText);
        LinearLayout layout = new LinearLayout(requireContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(courseNameText);
        dialogBuilder.setView(layout);

        // Continue button listener
        dialogBuilder.setPositiveButton("Continue", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                layout.removeView(courseNameText);
                String courseName = courseNameText.getText().toString();

                if (courseName.isEmpty())
                {
                    Toast.makeText(requireContext(), "Please add a course name", Toast.LENGTH_LONG).show();
                }

                else
                {
                    Course course = new Course(courseName);
                    RecyclerView recyclerView = view.getRootView().findViewById(R.id.recyclerView);
                    CustomAdapter adapter = (CustomAdapter) recyclerView.getAdapter();
                    MainActivity.addToCourseList(course);
                    adapter.notifyDataSetChanged();

                    // hide keyboard and start new fragment
                    InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    // Navigation.findNavController(view).navigate(R.id.navigation_add_items);

                    mGetContent.launch("application/pdf");
                }
            }
        });

        // Cancel button click listener
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                dialog.cancel();
            }
        });

        AlertDialog dialog = dialogBuilder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return dialog;
    }

    // File selector launcher for PDF
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>()
            {
                @Override
                public void onActivityResult(Uri uri)
                {
                    // Handle the returned Uri
                    String fullFilePath = UriUtils.getPathFromUri(requireContext(), uri);
                    File file = new File(fullFilePath);

                    setDirectory(fullFilePath);
                }
            });

    private void setDirectory(String filepath)
    {
        this.directory = filepath;
    }
}