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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.syllabuspro.R;
import com.example.syllabuspro.adapters.ItemsAdapter;
import com.example.syllabuspro.SyllabusItem;
import com.example.syllabuspro.databinding.ItemsViewFragmentBinding;

import java.util.ArrayList;

public class ItemsViewFragment extends Fragment {

    private ItemsViewViewModel mViewModel;
    private ItemsViewFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        binding = ItemsViewFragmentBinding.inflate(inflater, container, false);
        Toolbar toolbar = binding.getRoot().findViewById(R.id.manage_toolbar);

        // get items list
        RecyclerView courseRecyclerView = container.findViewById(R.id.recyclerView);
        Pair<String, ArrayList<SyllabusItem>> pair = (Pair<String, ArrayList<SyllabusItem>>) courseRecyclerView.getTag();

        ArrayList<SyllabusItem> itemsList = pair.second;

        // set toolbar name and back arrow
        toolbar.setTitle(pair.first + " syllabus items");
        toolbar.setNavigationIcon(R.drawable.ic_action_name);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                NavController navController = Navigation.findNavController(v);
                navController.popBackStack();
            }
        });

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
}
