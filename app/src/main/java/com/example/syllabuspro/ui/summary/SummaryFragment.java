package com.example.syllabuspro.ui.summary;

import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.syllabuspro.R;
import com.example.syllabuspro.databinding.FragmentSummaryBinding;

public class SummaryFragment extends Fragment
{
    private FragmentSummaryBinding binding;
    private ProgressBar progressBar;
    private TextView progressText;
    int i = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = FragmentSummaryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // set the id for the progressbar and progress text
        progressBar = root.findViewById(R.id.daily_progress_bar);
        progressText = root.findViewById(R.id.daily_progress_text);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // set the limitations for the numeric
                // text under the progress bar
                if (i <= 100) {
                    progressText.setText("" + i);
                    progressBar.setProgress(i);
                    i++;
                    handler.postDelayed(this, 200);
                } else {
                    handler.removeCallbacks(this);
                }
            }
        }, 200);

        return root;
    }

    public void onDestroyView()
    {
        super.onDestroyView();
    }
}