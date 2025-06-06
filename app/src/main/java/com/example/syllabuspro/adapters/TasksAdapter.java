package com.example.syllabuspro.adapters;

import android.util.Log;
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
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position)
    {
        holder.description.setText(this.taskList.get(position).getDescription());
        holder.course.setText(this.taskList.get(position).getCourse().getName());

        // Show priority with uppercase first letter and rest lowercase
        String priorityString = this.taskList.get(position).getPriority().toString();
        String cleanedPriorityString = priorityString.charAt(0) + priorityString.substring(1).toLowerCase();
        holder.priority.setText(cleanedPriorityString);
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
        TextView priority;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            description = itemView.findViewById(R.id.task_display_description);
            course = itemView.findViewById(R.id.task_display_course);
            priority = itemView.findViewById(R.id.task_display_priority);
        }
    }
}
