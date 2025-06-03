package com.example.syllabuspro.ui.add_items;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.syllabuspro.MainActivity;
import com.example.syllabuspro.R;
import com.example.syllabuspro.SyllabusItem;
import com.example.syllabuspro.adapters.AddCourseAdapter;
import com.example.syllabuspro.databinding.ActivityMainBinding;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
{
	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
		Calendar mCalendar = Calendar.getInstance();
		int year = mCalendar.get(Calendar.YEAR);
		int month = mCalendar.get(Calendar.MONTH);
		int dayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH);
		return new DatePickerDialog(
				getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, dayOfMonth);
	}

	@Override
	public void onDateSet(DatePicker datePicker, int year, int month, int day)
	{
		ActivityMainBinding binding = MainActivity.binding;
		Calendar mCalendar = Calendar.getInstance();
		mCalendar.set(Calendar.YEAR, year);
		mCalendar.set(Calendar.MONTH, month);
		mCalendar.set(Calendar.DAY_OF_MONTH, day);
		String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());
		Log.d("Date", selectedDate);

		RecyclerView recyclerView = binding.getRoot().findViewById(R.id.addCourseRecyclerView);
		AddCourseAdapter adapter = (AddCourseAdapter) recyclerView.getAdapter();
		ArrayList<SyllabusItem> syllabusItems = adapter.getSyllabusItems();
		SyllabusItem item = syllabusItems.get(syllabusItems.size() - 1);

		item.setDueDate(LocalDate.of(year, month, day));
	}
}
