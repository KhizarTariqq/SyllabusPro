package com.example.syllabuspro.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.syllabuspro.R;
import com.example.syllabuspro.Task;
import com.example.syllabuspro.ui.tasks.TaskPriorityType;
import com.example.syllabuspro.ui.tasks.TasksFragment;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TasksPriorityAdapter extends RecyclerView.Adapter<TasksPriorityAdapter.ViewHolder>
{
    private ArrayList<TaskPriorityType> taskList;

    public TasksPriorityAdapter(ArrayList<TaskPriorityType> taskList)
    {
        this.taskList = taskList;
    }

    @NonNull
    @NotNull
    @Override
    public TasksPriorityAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position)
    {
        holder.priorityType.setText(this.taskList.get(position).getPriority().toString());

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(TasksFragment.context,RecyclerView.HORIZONTAL, false);

        // Add adapter and layout
        holder.taskRecyclerView.setAdapter(new TasksAdapter(this.taskList.get(position).getTaskList()));
        holder.taskRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public int getItemCount()
    {
        return this.taskList.size();
    }

    public ArrayList<TaskPriorityType> getTaskList()
    {
        return this.taskList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView priorityType;
        RecyclerView taskRecyclerView;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            priorityType = itemView.findViewById(R.id.task_priority);
            taskRecyclerView = itemView.findViewById(R.id.task_priority_recyclerView);
        }
    }
}
