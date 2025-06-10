package com.example.syllabuspro.adapters;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.syllabuspro.R;
import com.example.syllabuspro.Task;
import com.example.syllabuspro.ui.tasks.TasksFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.function.Consumer;

public class TasksCourseItemAdapter extends RecyclerView.Adapter<TasksCourseItemAdapter.ViewHolder>
{
    private ArrayList<Task> taskList;
    private final Consumer<Task> onItemClick;

    public TasksCourseItemAdapter(ArrayList<Task> taskList, Consumer<Task> onItemClick)
    {
        this.taskList = taskList;
        this.onItemClick = onItemClick;
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
        // Set the description string with a smaller font on the text after the colon
        String descriptionString = "Description: " + this.taskList.get(position).getDescription();
        SpannableString spannableDescription = new SpannableString(descriptionString);

        spannableDescription.setSpan(
                new AbsoluteSizeSpan(15, true),
                13,
                descriptionString.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        holder.description.setText(spannableDescription);
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
            description = itemView.findViewById(R.id.task_description);
            priority = itemView.findViewById(R.id.task_display_priority);
            setupClickListener(itemView);
        }

        private void setupClickListener(View itemView) {
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick.accept(taskList.get(position));
                }
            });
        }
    }
}
