package com.rgukt.attend.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
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

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.rgukt.attend.R;
import com.rgukt.attend.objects.Day4v3;
import com.rgukt.attend.utils.DatabaseHandler4v3;

import java.util.ArrayList;
import java.util.List;

public class DeleteDayFragment extends Fragment {

    TextView txt_day, p1, p2, p3, p4, p5, p6, p7;
    Spinner daySpinner;
    Button deleteDay;
    Object[] days;
    DatabaseHandler4v3 db;
    SpinnerAdapter adapter;

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
        days = getActiveDays().toArray();
        adapter = new ArrayAdapter<>(getContext(), com.google.android.material.R.layout.support_simple_spinner_dropdown_item, days);
        daySpinner.setAdapter(adapter);
        daySpinner.setSelection(0);
        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = position;
                Day4v3 day = db.readDaysInTable().get(days[position]);
                if (day == null) {
                    txt_day.setText("Day: " + days[position]);
                    p1.setText("Period 1: " + "NaN");
                    p2.setText("Period 2: " + "NaN");
                    p3.setText("Period 3: " + "NaN");
                    p4.setText("Period 4: " + "NaN");
                    p5.setText("Period 5: " + "NaN");
                    p6.setText("Period 6: " + "NaN");
                    p7.setText("Period 7: " + "NaN");
                    return;
                }
                txt_day.setText("Day: " + day.getDay());
                p1.setText("Period 1: " + day.getMorningPeriods().get("p1"));
                p2.setText("Period 2: " + day.getMorningPeriods().get("p2"));
                p3.setText("Period 3: " + day.getMorningPeriods().get("p3"));
                p4.setText("Period 4: " + day.getMorningPeriods().get("p4"));
                p5.setText("Period 5: " + day.getAfterNoonPeriods().get("p1"));
                p6.setText("Period 6: " + day.getAfterNoonPeriods().get("p2"));
                p7.setText("Period 7: " + day.getAfterNoonPeriods().get("p3"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        deleteDay.setOnClickListener(v -> {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
            builder.setMessage("Please Click on Delete button if you want to confirm!");
            builder.setTitle("Delete ?");
            builder.setCancelable(false);
            builder.setPositiveButton("Delete!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.deleteDayInTable(days[selectedItem].toString());
                    Toast.makeText(getContext(), "Successfully Deleted Selected Day!", Toast.LENGTH_SHORT).show();
                    onResume();
                }
            });
            builder.setNegativeButton("Keep it!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });

        return itemView;
    }

    private List<String> getActiveDays() {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < db.readDaysInTable().size(); i++) {
            res.add(((Day4v3) (db.readDaysInTable().values().toArray()[i])).getDay());
        }
        if (res.size() == 0) {
            res.add("Please Add Data before Deleting");
        }
        return res;
    }

    @Override
    public void onResume() {
        super.onResume();
        days = getActiveDays().toArray();
        adapter = new ArrayAdapter<>(getContext(), com.google.android.material.R.layout.support_simple_spinner_dropdown_item, days);
        daySpinner.setAdapter(adapter);

    }
}