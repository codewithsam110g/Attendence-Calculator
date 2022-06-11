package com.rgukt.attend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rgukt.attend.utils.Record;
import com.rgukt.attend.utils.RecordAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

    private RecyclerView recyclerView;

    private RecordAdapter myAdapter;

    // Using ArrayList to Record data
    private ArrayList<Record> list;

    private int t;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    private String arr[] = {
            "Telugu",
            "English",
            "Maths",
            "Physics",
            "Chemistry",
            "Biology",
            "InformationTechnology"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRecord();
        getRecord();
    }

    private void setRecord() {
        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(this);
        if (acc == null) return;
        databaseReference = FirebaseDatabase.getInstance().getReference(acc.getId()).child("Attendance");
        for (String s : arr) {

            databaseReference.child(s).setValue(new Record(s, "13", "86.6"));

        }
        databaseReference.child("TotalDays").setValue(new Record("TotalDays","15","0"));
    }

    private void getRecord() {

        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(this);
        if (acc == null) return;

        // Getting reference of recyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        // Setting the layout as linear
        // layout for vertical orientation
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new RecordAdapter(this, list);

        // Setting Adapter to RecyclerView
        recyclerView.setAdapter(myAdapter);

        // get firebase instance for the desired key location
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(acc.getId()).child("Attendance").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    //userid -> Attendance -> list of subjects
                    //attendance as ref for each child

                    Record dat = ds.getValue(Record.class);
                    if(dat.getSubjectName().equals("TotalDays")){
                        t = Integer.parseInt(dat.getPresentRatio().trim());
                        continue;
                    }
                    list.add(dat);

                }
                update();
                //notifying adapter class about the change
                //myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void update(){

        ArrayList<Record> temp = list;
        Object[] records = temp.toArray();
        temp.clear();
        for (Object rec : records) {
            Record r = (Record) rec;
            String percentRatio_s = r.getPresentRatio();
            float percentage = (float) Integer.parseInt(percentRatio_s) / t;
            float val = percentage * 100;
            r.setPresentPercent((df.format(val)).trim());
            temp.add(r);
        }
        sorty(temp);
        list = temp;
        myAdapter.notifyDataSetChanged();
    }

    private void sorty(ArrayList<Record> list){
        ArrayList<Record> temp = list;
        Object[] records = temp.toArray();
        for (Object rec : records) {
            Record r = (Record) rec;
            switch (r.getSubjectName()){
                case "Telugu":
                {
                    list.set(0, r);
                    break;
                }
                case "English":
                {
                    list.set(1, r);
                    break;
                }
                case "Maths":
                {
                    list.set(2, r);
                    break;
                }
                case "Physics":
                {
                    list.set(3, r);
                    break;
                }
                case "Chemistry":
                {
                    list.set(4, r);
                    break;
                }
                case "Biology":
                {
                    list.set(5, r);
                    break;
                }
                case "InformationTechnology":
                {
                    list.set(6, r);
                    break;
                }
                default:
                {
                    Toast.makeText(this, "Error Tried to use Random Record for List", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
