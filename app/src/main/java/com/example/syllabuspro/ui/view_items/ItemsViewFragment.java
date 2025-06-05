package com.example.syllabuspro.ui.view_items;

import android.util.Pair;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.syllabuspro.R;
import com.example.syllabuspro.adapters.ItemsAdapter;
import com.example.syllabuspro.SyllabusItem;
import com.example.syllabuspro.databinding.FragmentSyllabusItemsViewBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ItemsViewFragment extends Fragment {

    private ItemsViewViewModel mViewModel;
    private FragmentSyllabusItemsViewBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        binding = FragmentSyllabusItemsViewBinding.inflate(inflater, container, false);
        Toolbar toolbar = requireActivity().findViewById(R.id.main_toolbar);

        // get items list
        RecyclerView courseRecyclerView = container.findViewById(R.id.recyclerView);
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Pair<String, ArrayList<SyllabusItem>> pair = (Pair<String, ArrayList<SyllabusItem>>) courseRecyclerView.getTag();

        ArrayList<SyllabusItem> itemsList = pair.second;

        // set toolbar name and back arrow
        toolbar.setTitle(pair.first + " Syllabus items");
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        toolbar.setNavigationOnClickListener(getBackButtonListener());

        RecyclerView itemsRecyclerView = binding.getRoot().findViewById(R.id.items_recyclerView);
        ItemsAdapter adapter = new ItemsAdapter(itemsList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false);

        // Add border between items
        // DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(itemsRecyclerView.getContext(),
        //      mLayoutManager.getOrientation());
        // itemsRecyclerView.addItemDecoration(mDividerItemDecoration);

        // Add adapter and layout
        itemsRecyclerView.setAdapter(adapter);
        itemsRecyclerView.setLayoutManager(mLayoutManager);

        return binding.getRoot();
    }

    private View.OnClickListener getBackButtonListener() {
        // Listener for back button in the action bar
        return v -> {
            NavController navController = NavHostFragment.findNavController(ItemsViewFragment.this);
            navController.popBackStack();
        };
    }

}
