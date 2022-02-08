package com.example.syllabuspro.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.navtest.R;
import com.example.syllabuspro.Task;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder>
{
    private ArrayList<Task> taskList;

    public TasksAdapter(ArrayList<Task> taskList)
    {
        this.taskList = taskList;
    }

    @NonNull
    @NotNull
    @Override
    public TasksAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position)
    {

    }

    @Override
    public int getItemCount()
    {
        return this.taskList.size();
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
