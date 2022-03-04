package com.example.syllabuspro.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.syllabuspro.Course;
import com.example.syllabuspro.R;
import com.example.syllabuspro.Task;
import com.example.syllabuspro.ui.tasks.TasksFragment;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.example.syllabuspro.MainActivity.taskList;

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
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task_course_row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position)
    {
        String name = "Course: " + this.courseList.get(position).getName();
        holder.name.setText(name);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(TasksFragment.context,RecyclerView.HORIZONTAL , false);

        // Add adapter and layout
        holder.taskRecyclerView.setAdapter(new TasksAdapter(getTaskList(position)));
        holder.taskRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public int getItemCount()
    {
        return this.courseList.size();
    }

    public ArrayList<Course> getCourseList()
    {
        return this.courseList;
    }

    public ArrayList<Task> getTaskList(int position)
    {
        Course course = this.courseList.get(position);
        ArrayList<Task> courseTaskList = new ArrayList<Task>();

        for (Task task : taskList)
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
