package com.example.syllabuspro.adapters;

import android.util.Log;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
// import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.navtest.R;
import com.example.syllabuspro.Course;
import com.example.syllabuspro.MainActivity;
import com.example.syllabuspro.SyllabusItem;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>
{
    private ArrayList<Course> courseList = new ArrayList<Course>();

    public CustomAdapter(ArrayList<Course> courseList)
    {
        this.courseList = courseList;
        Log.d("Adapter", courseList.toString());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.course_row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position)
    {
        holder.name.setText(this.courseList.get(position).getName());
        // holder.age.setText(this.courseList.get(position).getName());
    }

    @Override
    public int getItemCount()
    {
        return this.courseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.course_item_name);

            // Open syllabus items button
            itemView.findViewById(R.id.open_items_button).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    TextView textView = itemView.findViewById(R.id.course_item_name);
                    String courseName = textView.getText().toString();
                    // Get syllabus items for course

                    ArrayList<SyllabusItem> itemsList = null;
                    for (Course course : courseList)
                    {
                        if (course.getName().equals(courseName))
                        {
                            itemsList = course.getSyllabusItems();
                        }
                    }

                    // search to items list and so course name
                    RecyclerView recyclerView = MainActivity.binding.getRoot().findViewById(R.id.recyclerView);
                    recyclerView.setTag(new Pair<String, ArrayList<SyllabusItem>> (courseName, itemsList));

                    // start new fragment
                    Navigation.findNavController(view).navigate(R.id.navigation_view_items);
                }
            });
        }
    }
}