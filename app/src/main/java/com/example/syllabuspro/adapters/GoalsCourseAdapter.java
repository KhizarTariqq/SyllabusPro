package com.example.syllabuspro.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.syllabuspro.R;
import com.example.syllabuspro.Goal;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GoalsCourseAdapter extends RecyclerView.Adapter<GoalsCourseAdapter.ViewHolder>
{
    private ArrayList<Goal> goalsList;

    public GoalsCourseAdapter(ArrayList<Goal> goalsList)
    {
        this.goalsList = goalsList;
    }

    @NonNull
    @NotNull
    @Override
    public GoalsCourseAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.goal_row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull GoalsCourseAdapter.ViewHolder holder, int position)
    {
        Goal goal = goalsList.get(position);

        Log.d("Adapter", String.valueOf(holder.description));
        holder.description.setText(goal.getDescription());
        holder.course.setText(goal.getCourse().getName());
        holder.deadline.setText(goal.getDeadline().toString());
        holder.task.setText(goal.getTask().getName());
    }

    @Override
    public int getItemCount()
    {
        return this.goalsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView description;
        TextView course;
        TextView deadline;
        TextView task;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            description = itemView.findViewById(R.id.goal_display_description);
            course = itemView.findViewById(R.id.goal_display_course);
            deadline = itemView.findViewById(R.id.goal_display_deadline);
            task = itemView.findViewById(R.id.goal_display_task);
        }
    }
}
