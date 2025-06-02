package com.example.syllabuspro.ui.courses;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.syllabuspro.ApiClient;
import com.example.syllabuspro.ApiService;
import com.example.syllabuspro.Course;
import com.example.syllabuspro.R;
import com.example.syllabuspro.SyllabusItem;
import com.example.syllabuspro.adapters.CustomAdapter;
import com.example.syllabuspro.MainActivity;
import com.example.syllabuspro.databinding.FragmentCoursesBinding;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoursesFragment extends Fragment {

    private CoursesViewModel coursesViewModel;
    private FragmentCoursesBinding binding;
    private RecyclerView recyclerView;
    private Course newCourse;
    private EditText courseNameTextBox;
    private LinearLayout layout;
    private File pdfFile;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        coursesViewModel = new ViewModelProvider(this).get(CoursesViewModel.class);
        binding = FragmentCoursesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initializing list view with the custom adapter
        recyclerView = root.findViewById(R.id.recyclerView);
        CustomAdapter adapter = new CustomAdapter(MainActivity.getCourseList());

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false);

        // Add adapter and layout
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(mLayoutManager);

        // Setup addCourse Dialog
        AlertDialog dialog = createNewCourseDialog(root);
        ExtendedFloatingActionButton button = binding.addCourseButton;
        button.setOnClickListener(v -> showNewCourseDialog(dialog));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private AlertDialog createNewCourseDialog(View view)
    {
        // Set up dialog
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext());
        courseNameTextBox = new EditText(requireContext());
        dialogBuilder.setTitle("Enter the course name: ");
        dialogBuilder.setView(courseNameTextBox);
        layout = new LinearLayout(requireContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(courseNameTextBox);
        dialogBuilder.setView(layout);

        // Continue button listener
        dialogBuilder.setPositiveButton("Continue", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                layout.removeView(courseNameTextBox);
                String courseName = courseNameTextBox.getText().toString();

                if (courseName.isEmpty())
                {
                    Toast.makeText(requireContext(), "Please add a course name", Toast.LENGTH_LONG).show();
                }

                else
                {
                    // Make course a field and courseName local, use a setter for sylabus items
                    newCourse = new Course(courseName);

                    // hide keyboard and start new fragment
                    InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    // Manual syllabus item creation
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

    private void showNewCourseDialog(AlertDialog dialog) {
        if (layout.indexOfChild(courseNameTextBox) == -1)
        {
            courseNameTextBox.setText("");
            layout.addView(courseNameTextBox);
        }

        dialog.show();
    }

    // File selector launcher for PDF
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>()
            {
                @Override
                public void onActivityResult(Uri uri)
                {
                    if (uri != null)
                    {
                        try
                        {
                            pdfFile = copyUriToPdfFile(requireContext(), uri);
                            processPDF(pdfFile);
                        }

                        catch (IOException e) {
                            Log.e("PDF", "Failed to copy PDF file", e);
                        }
                    }

                    else
                    {
                        Log.w("PDF", "No file selected");
                    }
                }
            });

    private File copyUriToPdfFile(Context context, Uri uri) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        if (inputStream == null) return null;

        // Generate a temp file name with .pdf extension
        File outputFile = new File(context.getCacheDir(), "selected_pdf_" + System.currentTimeMillis() + ".pdf");
        OutputStream outputStream = Files.newOutputStream(outputFile.toPath());

        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outputStream.close();

        return outputFile;
    }

    private void processPDF(File pdfFile) {
        /**
        This method takes in a PDF as a File object, and creates an API
         POST request to run the two spacy NER models to extract all SyllabusItems
         within the PDF
        */
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/pdf"), pdfFile
        );

        MultipartBody.Part filePart = MultipartBody.Part.createFormData(
                "file", pdfFile.getName(), requestBody
        );

        ApiService apiService = ApiClient.getApiService();

        Call<List<SyllabusItem>> call = apiService.uploadPdf(filePart);

        call.enqueue(new Callback<List<SyllabusItem>>() {
            @Override
            public void onResponse(@NonNull Call<List<SyllabusItem>> call, @NonNull Response<List<SyllabusItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<SyllabusItem> items = new ArrayList<>(response.body());
                    newCourse.setSyllabusItems(items);

                    Log.d("API", "Response received");
                    Log.d("API", newCourse.toString());
                    RecyclerView recyclerView = binding.getRoot().getRootView().findViewById(R.id.recyclerView);
                    CustomAdapter adapter = (CustomAdapter) recyclerView.getAdapter();
                    MainActivity.addToCourseList(newCourse);
                    MainActivity.saveCourseList();
                    adapter.notifyDataSetChanged();
                }

                else
                {
                    Log.d("API", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<SyllabusItem>> call, Throwable t) {
                Log.e("API", "Retrofit call failed", t);  // this shows cause
                Toast.makeText(requireContext(), "API Call failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}