package com.bawp.todoister;

import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bawp.todoister.model.Priority;
import com.bawp.todoister.model.Task;
import com.bawp.todoister.model.TaskViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Calendar;
import java.util.Date;

public class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private EditText enterTodo;
    private ImageButton buttonCalendar;
    private ImageButton buttonPriority;
    private RadioGroup radioGroupPriority;
    private RadioButton selectedRadioButton;
    private int selectedButtonId;
    private ImageButton buttonSave;
    private CalendarView calendarView;
    private Group calendarGroup;
    private Date dueDate;
    Calendar calendar = Calendar.getInstance();

    public BottomSheetFragment() {

    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);
        calendarGroup = view.findViewById(R.id.calendar_group);
        calendarView = view.findViewById(R.id.calendar_view);
        buttonCalendar = view.findViewById(R.id.today_calendar_button);
        enterTodo = view.findViewById(R.id.enter_todo_et);
        buttonSave = view.findViewById(R.id.save_todo_button);
        buttonPriority = view.findViewById(R.id.priority_todo_button);
        radioGroupPriority = view.findViewById(R.id.radioGroup_priority);

        Chip chipToday = view.findViewById(R.id.today_chip);
        Chip chipTomorrow = view.findViewById(R.id.tomorrow_chip);
        Chip chipNextWeek = view.findViewById(R.id.next_week_chip);
        chipToday.setOnClickListener(this);
        chipTomorrow.setOnClickListener(this);
        chipNextWeek.setOnClickListener(this);

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarGroup.setVisibility(calendarGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int date) {
//                Log.e("CAL", "i== "+ i + " i1== " + i1 + " i2== " + i2);
                calendar.clear();
                calendar.set(year, month, date);
                dueDate = calendar.getTime();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String task = enterTodo.getText().toString().trim();
                if (!TextUtils.isEmpty(task) && dueDate != null) {
                    Task myTask = new Task(task, Priority.HIGH, dueDate, Calendar.getInstance().getTime(), false);
                    TaskViewModel.insert(myTask);
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.today_chip) {
            // set data for today
            calendar.add(Calendar.DAY_OF_YEAR, 0);
            dueDate = calendar.getTime();
        } else if (id == R.id.tomorrow_chip) {
            // set data for tomorrow
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            dueDate = calendar.getTime();
        } else if (id == R.id.next_week_chip) {
            // set data for next week
            calendar.add(Calendar.DAY_OF_YEAR, 7);
            dueDate = calendar.getTime();
        }
        Log.e("TIME", "onclick: " + dueDate.toString());
    }
}