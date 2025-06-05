package com.example.syllabuspro.ui.calendar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.syllabuspro.R;
import com.example.syllabuspro.Utils;
import com.example.syllabuspro.databinding.FragmentCalendarBinding;


public class CalendarFragment extends Fragment {

    private CalendarViewModel calendarViewModel;
    private FragmentCalendarBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calendarViewModel =
                new ViewModelProvider(this).get(CalendarViewModel.class);

        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set action bar title
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Calendar");
        setHasOptionsMenu(true);

        return root;
    }

    private void filter() {
        Log.d("Button", "Filter button clicked!");
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

        // Add some padding between the filter button and the right edge of the toolbar
        Toolbar tb = requireActivity().findViewById(R.id.main_toolbar);
        tb.setPaddingRelative(tb.getPaddingStart(), tb.getPaddingTop(), Utils.dpToPx(requireContext(), 14), tb.getPaddingBottom());

        // Start rendering after layout is done
        view.post(() -> startPostponedEnterTransition());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull android.view.Menu menu, @NonNull android.view.MenuInflater inflater) {
        inflater.inflate(R.menu.menu_calendar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull android.view.MenuItem item) {
        if (item.getItemId() == R.id.action_filter) {
            filter();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}