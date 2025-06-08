package com.example.syllabuspro.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.syllabuspro.R;
import com.example.syllabuspro.Task;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TasksPriorityItemAdapter extends RecyclerView.Adapter<TasksPriorityItemAdapter.ViewHolder>
{
    private ArrayList<Task> taskList;

    public TasksPriorityItemAdapter(ArrayList<Task> taskList)
    {
        this.taskList = taskList;
    }

    @NonNull
    @NotNull
    @Override
    public TasksPriorityItemAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_priority_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position)
    {
        holder.description.setText(this.taskList.get(position).getDescription());
        holder.course.setText(this.taskList.get(position).getCourse().getName());
    }

    @Override
    public int getItemCount()
    {
        return this.taskList.size();
    }

    public ArrayList<Task> getTaskList()
    {
        return this.taskList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView description;
        TextView course;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            description = itemView.findViewById(R.id.task_display_description);
            course = itemView.findViewById(R.id.task_display_course);
        }
    }
}
