package com.rgukt.attend.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.rgukt.attend.R;
import com.rgukt.attend.objects.Day4v3;
import com.rgukt.attend.utils.DatabaseHandler4v3;

public class DeleteDayFragment extends Fragment {

    TextView txt_day, p1, p2, p3, p4, p5, p6 ,p7;
    Spinner daySpinner;
    Button deleteDay;
    String[] days = {
            "monday",
            "tuesday",
            "wednesday",
            "thursday",
            "friday",
            "saturday"
    };
    DatabaseHandler4v3 db;

    int selectedItem = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.fragment_delete_day, container, false);

        txt_day = itemView.findViewById(R.id.txt_day);
        p1 = itemView.findViewById(R.id.txt_p1);
        p2 = itemView.findViewById(R.id.txt_p2);
        p3 = itemView.findViewById(R.id.txt_p3);
        p4 = itemView.findViewById(R.id.txt_p4);
        p5 = itemView.findViewById(R.id.txt_p5);
        p6 = itemView.findViewById(R.id.txt_p6);
        p7 = itemView.findViewById(R.id.txt_p7);
        daySpinner = itemView.findViewById(R.id.spn_days_delete);
        deleteDay = itemView.findViewById(R.id.btn_delete_day);
        db = new DatabaseHandler4v3(getContext());
        SpinnerAdapter adapter = new ArrayAdapter<>(getContext(), com.google.android.material.R.layout.support_simple_spinner_dropdown_item, days);
        daySpinner.setAdapter(adapter);
        daySpinner.setSelection(0);
        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = position;
                Day4v3 day = db.readDaysInTable().get(days[position]);
                if(day == null){
                    Toast.makeText(getContext(), "Selected Day Doesn't exist", Toast.LENGTH_SHORT).show();
                    txt_day.setText("Day: " + days[position]);
                    p1.setText("Period 1: "+"NaN");
                    p2.setText("Period 2: "+"NaN");
                    p3.setText("Period 3: "+"NaN");
                    p4.setText("Period 4: "+"NaN");
                    p5.setText("Period 5: "+"NaN");
                    p6.setText("Period 6: "+"NaN");
                    p7.setText("Period 7: "+"NaN");
                    return;
                }
                Toast.makeText(getContext(), "Selected Day Exists", Toast.LENGTH_SHORT).show();
                txt_day.setText("Day: " + day.getDay());
                p1.setText("Period 1: "+day.getMorningPeriods().get("p1"));
                p2.setText("Period 2: "+day.getMorningPeriods().get("p2"));
                p3.setText("Period 3: "+day.getMorningPeriods().get("p3"));
                p4.setText("Period 4: "+day.getMorningPeriods().get("p4"));
                p5.setText("Period 5: "+day.getAfterNoonPeriods().get("p1"));
                p6.setText("Period 6: "+day.getAfterNoonPeriods().get("p2"));
                p7.setText("Period 7: "+day.getAfterNoonPeriods().get("p3"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        deleteDay.setOnClickListener(v -> {
            db.deleteDayInTable(days[selectedItem]);
            Toast.makeText(getContext(), "Successfully Deleted Selected Day!", Toast.LENGTH_SHORT).show();
            /*TODO: Implement Alert Dialog box to Confirm Delete
                    Add Capitalisation to English Words when user Inputs it
                    Use Firebase Storage
                    Implement Locked Firebase Database
                    Use List instead of array in spinner
                    Change use of HashMap to something simple
                    Implement dynamic List in Delete Frag so that only data containing days are shown
            */
        });

        return itemView;
    }


}