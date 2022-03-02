package com.example.syllabuspro.ui.goals;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.syllabuspro.Goal;
import com.example.syllabuspro.MainActivity;
import com.example.syllabuspro.R;
import com.example.syllabuspro.adapters.GoalsAdapter;
import com.example.syllabuspro.databinding.FragmentGoalsBinding;


public class GoalsFragment extends Fragment
{
    private FragmentGoalsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        binding = FragmentGoalsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Log.d("GoalsFragment", MainActivity.goalList.toString());
        // Goal goal = MainActivity.goalList.get(0);
        // Log.d("GoalsFragment", goal.toString());
        RecyclerView recyclerView = root.findViewById(R.id.goalsRecyclerview);
        GoalsAdapter adapter = new GoalsAdapter(MainActivity.goalList);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false);

        // Add border between items
        // DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
        //      mLayoutManager.getOrientation());
        // recyclerView.addItemDecoration(mDividerItemDecoration);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(mLayoutManager);

        return root;
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        binding = null;
    }
}
