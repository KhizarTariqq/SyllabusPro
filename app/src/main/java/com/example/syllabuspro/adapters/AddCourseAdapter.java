package com.example.syllabuspro.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.syllabuspro.DatePickerFragment;
import com.example.syllabuspro.MainActivity;
import com.example.syllabuspro.R;
import com.example.syllabuspro.SyllabusItem;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AddCourseAdapter extends RecyclerView.Adapter<AddCourseAdapter.ViewHolder>
{
    public ArrayList<SyllabusItem> syllabusItems;
    private ViewHolder viewHolder;

    public AddCourseAdapter(ArrayList<SyllabusItem> syllabusItems)
    {
        this.syllabusItems = syllabusItems;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        this.viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.add_items_dialog_row, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position)
    {
        holder.name.setText("Name:");

        // Set up spinner for type
        Spinner spinner = (Spinner) holder.itemView.findViewById(R.id.types_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(holder.itemView.getContext(),
                R.array.types_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // set up spinner listener
        spinner.setSelection(0, false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l)
            {
                if (spinner.getTag() == null)
                {
                    spinner.setTag(true);
                }

                else
                {
                    Log.d("empty", String.valueOf(adapterView.getSelectedItemId()));
                    Log.d("Spinner", "testing");
                    Log.d("Spinner", adapterView.getItemAtPosition(position).getClass().getSimpleName());

                    String typeString = adapterView.getItemAtPosition(position).getClass().getSimpleName();
                    adapterView.getItemAtPosition(position);
                    SyllabusItem.Type type = null;

                    if (typeString.equals("Quiz"))
                    {
                        type = SyllabusItem.Type.Quiz;
                    }

                    else if (typeString.equals("Assignment"))
                    {
                        type = SyllabusItem.Type.Assignment;
                    }

                    else if (typeString.equals("Term") && typeString.equals("Test"))
                    {
                        type = SyllabusItem.Type.TermTest;
                    }

                    else if (typeString.equals("Class") && typeString.equals("Participation"))
                    {
                        type = SyllabusItem.Type.ClassParticipation;
                    }
                    else if (typeString.equals("Final") && typeString.equals("Exam"))
                    {
                        type = SyllabusItem.Type.FinalExam;
                    }

                    // Get SyllabusItem
                    RecyclerView recyclerView = view.getRootView().findViewById(R.id.addCourseRecyclerView);
                    AddCourseAdapter adapter = (AddCourseAdapter) recyclerView.getAdapter();
                    ArrayList<SyllabusItem> syllabusItems = adapter.getSyllabusItems();

                    SyllabusItem item = syllabusItems.get(syllabusItems.size() - 1);
                    item.setType(type);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
    }

    @Override
    public int getItemCount()
    {
        return this.syllabusItems.size();
    }

    public void setItemProperties()
    {
        // get information
        String itemName = (String) this.viewHolder.name.getText();
        int weight = Integer.parseInt((String) this.viewHolder.weight.getText());


        // Get SyllabusItem and set properties
        SyllabusItem item = syllabusItems.get(syllabusItems.size());

        // item.setProperties();
    }

    public ArrayList<SyllabusItem> getSyllabusItems()
    {
        return this.syllabusItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        TextView weight;
        Spinner spinner;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.enter_item_name);
            weight = itemView.findViewById(R.id.enter_weight);
            // age = itemView.findViewById(R.id.item_age);

            // Set up date picker
            Button deadlineButton = itemView.findViewById(R.id.deadline_button);
            deadlineButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    DialogFragment fragment = new DatePickerFragment();
                    fragment.show(MainActivity.fragmentManager, "datePicker");
                }
            });


        }
    }
}
