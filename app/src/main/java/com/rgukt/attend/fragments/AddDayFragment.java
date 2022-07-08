package com.rgukt.attend.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.rgukt.attend.R;
import com.rgukt.attend.objects.Day4v3;
import com.rgukt.attend.utils.DatabaseHandler4v3;


public class AddDayFragment extends Fragment {

    private Spinner spn_days;
    private TextInputEditText p1, p2, p3, p4, p5, p6, p7;
    private Button addDay;

    String[] days = {
            "monday",
            "tuesday",
            "wednesday",
            "thursday",
            "friday",
            "saturday"
    };

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

        SpinnerAdapter adapter = new ArrayAdapter<>(getContext(), com.google.android.material.R.layout.support_simple_spinner_dropdown_item, days);
        spn_days.setAdapter(adapter);

        addDay.setOnClickListener(v2 ->{
            String day = spn_days.getSelectedItem().toString();
            String per1 = p1.getText().toString();
            String per2 = p2.getText().toString();
            String per3 = p3.getText().toString();
            String per4 = p4.getText().toString();
            String per5 = p5.getText().toString();
            String per6 = p6.getText().toString();
            String per7 = p7.getText().toString();

            if(TextUtils.isEmpty(per1) || TextUtils.isEmpty(per2) || TextUtils.isEmpty(per3) ||
                    TextUtils.isEmpty(per4) || TextUtils.isEmpty(per5) || TextUtils.isEmpty(per6) || TextUtils.isEmpty(per7)){
                Toast.makeText(getContext(), "Please Enter All Fields! Including Day", Toast.LENGTH_SHORT).show();
            }

            if(TextUtils.isEmpty(day)) return;

            DatabaseHandler4v3 db = new DatabaseHandler4v3(getContext());
            if(db.addDayToTable(new Day4v3(day, per1, per2, per3, per4, per5, per6 ,per7))){
                Toast.makeText(getContext(), "Successfully Added Day to Database", Toast.LENGTH_SHORT).show();
            }

        });

        return v;
    }
}