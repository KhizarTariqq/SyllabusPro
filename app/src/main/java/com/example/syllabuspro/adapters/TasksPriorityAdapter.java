package com.example.syllabuspro.adapters;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.syllabuspro.HorizontalSpaceItemDecoration;
import com.example.syllabuspro.R;
import com.example.syllabuspro.ui.tasks.TaskPriorityType;
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
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_row_priority_mode, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position)
    {
        String priority = "Priority: " + this.taskPriorityList.get(position).getPriority().toString();
        holder.priorityType.setText(priority);

        // Add adapter, layout and ItemDecoration (to separate items)
        holder.taskRecyclerView.setAdapter(new TasksPriorityItemAdapter(this.taskPriorityList.get(position).getTaskList()));
        holder.taskRecyclerView.setLayoutManager(
                new LinearLayoutManager(holder.taskRecyclerView.getContext(), RecyclerView.HORIZONTAL, false)
        );
        int spacingInPx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 12, holder.taskRecyclerView.getContext().getResources().getDisplayMetrics());

        holder.taskRecyclerView.addItemDecoration(new HorizontalSpaceItemDecoration(spacingInPx));
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
