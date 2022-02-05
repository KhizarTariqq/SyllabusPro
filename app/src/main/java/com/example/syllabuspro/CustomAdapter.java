package com.example.syllabuspro;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
// import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.navtest.R;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>
{
    private ArrayList<Course> courseList = new ArrayList<Course>();
    private Course selectedCourse;

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
        TextView name, age;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            itemView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    TextView textView = itemView.findViewById(R.id.item_name);
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


                    Log.d("new method", "" + name);
                    Log.d("new method", String.valueOf(name));

                    // search to items list and so course name
                    ItemsViewFragment fragment = new ItemsViewFragment();
                    fragment.setItemsList(itemsList);
                    fragment.setCourseName(courseName);

                    // start new fragment
                    // MainActivity.setFragmentManager();
                    MainActivity.fragmentManager.beginTransaction()
                            .replace(R.id.container, fragment, "findThisFragment")
                            .addToBackStack(null)
                            .commit();

                }
            });
        }
    }

    public void setSelectedCourse(Course selectedCourse)
    {
        Log.d("view items 2", selectedCourse.toString());
        this.selectedCourse = selectedCourse;
    }

    public Course getSelectedCourse()
    {
        return this.selectedCourse;
    }

    public void getPosition()
    {
        // int pos = this.
        // recyclerView.getFocusedChild().;

        // Get correct course name
        // Log.d("position: ", String.valueOf(pos));
    }
}