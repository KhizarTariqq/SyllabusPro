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

public class TasksCourseItemAdapter extends RecyclerView.Adapter<TasksCourseItemAdapter.ViewHolder>
{
    private ArrayList<Task> taskList;

    public TasksCourseItemAdapter(ArrayList<Task> taskList)
    {
        this.taskList = taskList;
    }

    @NonNull
    @NotNull
    @Override
    public TasksCourseItemAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_course_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position)
    {
        holder.description.setText(this.taskList.get(position).getDescription());
        holder.priority.setText(this.taskList.get(position).getPriority().toString());
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

        TextView priority;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            description = itemView.findViewById(R.id.task_display_description);
            priority = itemView.findViewById(R.id.task_display_priority);
        }
    }
}
