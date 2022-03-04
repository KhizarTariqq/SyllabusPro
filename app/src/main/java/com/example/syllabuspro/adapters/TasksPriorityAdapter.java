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
    private ArrayList<TaskPriorityType> taskPriorityList;

    public TasksPriorityAdapter(ArrayList<TaskPriorityType> taskPriorityList)
    {
        this.taskPriorityList = taskPriorityList;
    }

    @NonNull
    @NotNull
    @Override
    public TasksPriorityAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task_priority_row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position)
    {
        holder.priorityType.setText(this.taskPriorityList.get(position).getPriority().toString());

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(TasksFragment.context,RecyclerView.HORIZONTAL, false);

        // Add adapter and layout
        holder.taskRecyclerView.setAdapter(new TasksAdapter(this.taskPriorityList.get(position).getTaskList()));
        holder.taskRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public int getItemCount()
    {
        return this.taskPriorityList.size();
    }

    public void setTaskPriorityList(ArrayList<TaskPriorityType> taskPriorityList)
    {
        this.taskPriorityList = taskPriorityList;
    }

    public ArrayList<TaskPriorityType> getTaskPriorityList()
    {
        return this.taskPriorityList;
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
