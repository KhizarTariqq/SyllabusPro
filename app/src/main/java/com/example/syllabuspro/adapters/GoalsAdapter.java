package com.example.syllabuspro.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.navtest.R;
import com.example.syllabuspro.Goal;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GoalsAdapter extends RecyclerView.Adapter<GoalsAdapter.ViewHolder>
{
    private ArrayList<Goal> goalsList;

    public GoalsAdapter(ArrayList<Goal> goalsList)
    {
        this.goalsList = goalsList;
    }

    @NonNull
    @NotNull
    @Override
    public GoalsAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull GoalsAdapter.ViewHolder holder, int position)
    {

    }

    @Override
    public int getItemCount()
    {
        return this.goalsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            // name = itemView.findViewById(R.id.item_name);
            // age = itemView.findViewById(R.id.item_age);
        }
    }
}
