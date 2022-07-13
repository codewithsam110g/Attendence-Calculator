package com.rgukt.attend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rgukt.attend.objects.SubjectData;

import org.apache.commons.lang3.StringUtils;

public class AddSubjectActivity extends AppCompatActivity {

    private TextInputEditText edt_subjectName, edt_presentDays, edt_totalDays, edt_recPercentage;
    private Button btn_addSubject;
    private ProgressBar pb;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    private String subjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        FirebaseApp.initializeApp(/*context=*/ this);
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(
                SafetyNetAppCheckProviderFactory.getInstance());

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account == null) {
            startActivity(new Intent(AddSubjectActivity.this, LoginActivity.class));
            finish();
        }

        edt_subjectName = findViewById(R.id.edt_sub_name);
        edt_presentDays = findViewById(R.id.edt_present_days);
        edt_totalDays = findViewById(R.id.edt_total_days);
        edt_recPercentage = findViewById(R.id.edt_rec_percent);
        btn_addSubject = findViewById(R.id.btn_add_subject);
        pb = findViewById(R.id.pb_loading);
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mDatabase.getReference(account.getId()).child("Subjects");

        btn_addSubject.setOnClickListener(v -> {
            pb.setVisibility(View.VISIBLE);
            String subName = edt_subjectName.getText().toString();
            String presentDays = edt_presentDays.getText().toString();
            String totalDays = edt_totalDays.getText().toString();
            String s = edt_recPercentage.getText().toString();
            String recPercent = (s.equals("") || s.equals("0")) ? "75" : s;

            subjectId = subName;

            if (subName.trim().length() == 0 || presentDays.trim().length() == 0
                    || totalDays.trim().length() == 0) {
                pb.setVisibility(View.GONE);
                Toast.makeText(this, "Please Enter all of the Fields", Toast.LENGTH_SHORT).show();
                return;
            }

            SubjectData subjectData = new SubjectData(StringUtils.capitalize(subName), Integer.parseInt(presentDays),
                    Integer.parseInt(totalDays), Integer.parseInt(recPercent), StringUtils.capitalize(subjectId));

            mDatabaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    pb.setVisibility(View.GONE);
                    mDatabaseRef.child(StringUtils.capitalize(subjectId)).setValue(subjectData);
                    Toast.makeText(AddSubjectActivity.this, "Subject Added Successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddSubjectActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    pb.setVisibility(View.GONE);
                    Toast.makeText(AddSubjectActivity.this,
                            "Cannot add Subject!\n The Error is " + error, Toast.LENGTH_SHORT).show();
                }
            });

        });

    }
}