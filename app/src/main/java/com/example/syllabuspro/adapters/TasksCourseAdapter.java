package com.example.syllabuspro.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.syllabuspro.Course;
import com.example.syllabuspro.MainActivity;
import com.example.syllabuspro.R;
import com.example.syllabuspro.Task;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class TasksCourseAdapter extends RecyclerView.Adapter<TasksCourseAdapter.ViewHolder>
{
    private ArrayList<Course> courseList;

    public TasksCourseAdapter(ArrayList<Course> courseList)
    {
        this.courseList = courseList;
    }

    @NonNull
    @NotNull
    @Override
    public TasksCourseAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_row_course_mode, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position)
    {
        String name = "Course: " + this.courseList.get(position).getName();
        holder.name.setText(name);

        // Add adapter and layout
        holder.taskRecyclerView.setAdapter(new TasksCourseItemAdapter(getTaskList(position)));
        holder.taskRecyclerView.setLayoutManager(
                new LinearLayoutManager(holder.taskRecyclerView.getContext(), RecyclerView.HORIZONTAL, false)
        );
    }

    @Override
    public int getItemCount()
    {
        return this.courseList.size();
    }

    public ArrayList<Task> getTaskList(int position)
    {
        Course course = this.courseList.get(position);
        ArrayList<Task> courseTaskList = new ArrayList<>();

        for (Task task : MainActivity.getTaskList())
        {
            if (task.getCourse().equals(course))
            {
                courseTaskList.add(task);
            }
        }

        return courseTaskList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        RecyclerView taskRecyclerView;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.task_course_name);
            taskRecyclerView = itemView.findViewById(R.id.task_course_recyclerView);
        }
    }
}
