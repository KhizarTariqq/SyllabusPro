package com.example.syllabuspro.adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.syllabuspro.ui.add_items.DatePickerFragment;
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

        // set up spinner listener
        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l)
            {
                String typeString = adapterView.getItemAtPosition(position).toString();
                SyllabusItem.Type type;

                switch (typeString) {
                    case "Quiz":
                        type = SyllabusItem.Type.QUIZ;
                        break;

                    case "Assignment":
                        type = SyllabusItem.Type.ASSIGNMENT;
                        break;

                    case "Lab":
                        type = SyllabusItem.Type.LAB;
                        break;

                    case "Pre-class Activity":
                        type = SyllabusItem.Type.PRE_CLASS_ACTIVITY;
                        break;

                    case "Midterm":
                        type = SyllabusItem.Type.MIDTERM;
                        break;

                    case "Participation":
                        type = SyllabusItem.Type.PARTICIPATION;
                        break;

                    case "Final Exam":
                        type = SyllabusItem.Type.EXAM;
                        break;

                    case "Other":
                        type = SyllabusItem.Type.OTHER;
                        break;

                    default:
                        type = null;
                        break;
                }

                // Get SyllabusItem
                RecyclerView recyclerView = view.getRootView().findViewById(R.id.addCourseRecyclerView);
                AddCourseAdapter adapter = (AddCourseAdapter) recyclerView.getAdapter();
                ArrayList<SyllabusItem> syllabusItems = adapter.getSyllabusItems();

                SyllabusItem item = syllabusItems.get(syllabusItems.size() - 1);
                item.setType(type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        // set edittext listeners
        EditText editName = holder.itemView.findViewById(R.id.input_name);
        editName.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                Log.d("empty", "textBoxChange");
                SyllabusItem item = syllabusItems.get(syllabusItems.size() - 1);
                item.setDescription(editable.toString());
            }
        });

        EditText editWeight = holder.itemView.findViewById(R.id.input_weight);
        editWeight.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                Log.d("empty", "textBoxChange");
                SyllabusItem item = syllabusItems.get(syllabusItems.size() - 1);
                item.setWeight(Integer.valueOf(editable.toString()));
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return this.syllabusItems.size();
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
            spinner = itemView.findViewById(R.id.types_spinner);

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

            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(itemView.getContext(),
            R.array.types_array, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            spinner.setAdapter(adapter);
        }
    }
}
