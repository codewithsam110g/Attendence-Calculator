package com.rgukt.attend.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.rgukt.attend.R;
import com.rgukt.attend.objects.Day4v3;
import com.rgukt.attend.utils.DatabaseHandler4v3;


public class Add_Update_DayFragment extends Fragment {

    String[] days = {
            "monday",
            "tuesday",
            "wednesday",
            "thursday",
            "friday",
            "saturday"
    };
    private Spinner spn_days;
    private TextInputEditText p1, p2, p3, p4, p5, p6, p7;
    private Button addDay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_day, container, false);

        spn_days = v.findViewById(R.id.spn_days);
        p1 = v.findViewById(R.id.edt_period_1);
        p2 = v.findViewById(R.id.edt_period_2);
        p3 = v.findViewById(R.id.edt_period_3);
        p4 = v.findViewById(R.id.edt_period_4);
        p5 = v.findViewById(R.id.edt_period_5);
        p6 = v.findViewById(R.id.edt_period_6);
        p7 = v.findViewById(R.id.edt_period_7);
        addDay = v.findViewById(R.id.btn_add_day);

        DatabaseHandler4v3 db = new DatabaseHandler4v3(getContext());

        SpinnerAdapter adapter = new ArrayAdapter<>(getContext(), com.google.android.material.R.layout.support_simple_spinner_dropdown_item, days);
        spn_days.setAdapter(adapter);
        spn_days.setSelection(0);

        spn_days.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (db.readDaysInTable().containsKey(days[position])) {
                    Day4v3 d = db.readDaysInTable().get(days[position]);
                    p1.setText(d.getMorningPeriods().get("p1"));
                    p2.setText(d.getMorningPeriods().get("p2"));
                    p3.setText(d.getMorningPeriods().get("p3"));
                    p4.setText(d.getMorningPeriods().get("p4"));
                    p5.setText(d.getAfterNoonPeriods().get("p1"));
                    p6.setText(d.getAfterNoonPeriods().get("p2"));
                    p7.setText(d.getAfterNoonPeriods().get("p3"));
                } else {
                    p1.setText("");
                    p2.setText("");
                    p3.setText("");
                    p4.setText("");
                    p5.setText("");
                    p6.setText("");
                    p7.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addDay.setOnClickListener(v2 -> {
            String day = spn_days.getSelectedItem().toString();
            String per1 = p1.getText().toString();
            String per2 = p2.getText().toString();
            String per3 = p3.getText().toString();
            String per4 = p4.getText().toString();
            String per5 = p5.getText().toString();
            String per6 = p6.getText().toString();
            String per7 = p7.getText().toString();

            if (TextUtils.isEmpty(per1) || TextUtils.isEmpty(per2) || TextUtils.isEmpty(per3) ||
                    TextUtils.isEmpty(per4) || TextUtils.isEmpty(per5) || TextUtils.isEmpty(per6) || TextUtils.isEmpty(per7)) {
                Toast.makeText(getContext(), "Please Enter All Fields! Including Day", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(day)) return;
            if (!db.readDaysInTable().containsKey(day)) {
                if (db.addDayToTable(new Day4v3(day, per1, per2, per3, per4, per5, per6, per7))) {
                    Toast.makeText(getContext(), "Successfully Added Day to Database", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (db.updateDayInTable(day, new Day4v3(day, per1, per2, per3, per4, per5, per6, per7))) {
                    Toast.makeText(getContext(), "Successfully Updated the data of: " + day, Toast.LENGTH_SHORT).show();
                }
            }

        });

        return v;
    }
}