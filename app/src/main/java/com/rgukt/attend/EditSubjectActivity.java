package com.rgukt.attend;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rgukt.attend.objects.SubjectData;

import java.util.HashMap;
import java.util.Map;

public class EditSubjectActivity extends AppCompatActivity {

    private TextInputEditText edt_subjectName, edt_PresentClasses, edt_TotalClasses;
    private Button btn_updateSubject, btn_removeSubject;
    private ProgressBar pb;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    private String subjectId;
    private SubjectData subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subject);

        edt_subjectName = findViewById(R.id.edt_sub_name);
        edt_PresentClasses = findViewById(R.id.edt_present_days);
        edt_TotalClasses = findViewById(R.id.edt_total_days);
        btn_updateSubject = findViewById(R.id.btn_update_subject);
        btn_removeSubject = findViewById(R.id.btn_remove_subject);
        pb = findViewById(R.id.pb_loading);

        mDatabase = FirebaseDatabase.getInstance();

        subject = getIntent().getParcelableExtra("subject");
        if (subject != null) {
            edt_subjectName.setText(subject.getSubjectName());
            edt_PresentClasses.setText(String.valueOf(subject.getPresentClasses()));
            edt_TotalClasses.setText(String.valueOf(subject.getTotalClasses()));
            subjectId = subject.getSubjectId();
        }

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account == null) {
            startActivity(new Intent(EditSubjectActivity.this, LoginActivity.class));
            finish();
        }

        mDatabaseRef = mDatabase.getReference(account.getId()).child("Subjects").child(subjectId);

        btn_updateSubject.setOnClickListener(v -> {

            String subjectName = edt_subjectName.getText().toString();
            String pD = edt_PresentClasses.getText().toString();
            String tD = edt_TotalClasses.getText().toString();

            if (!TextUtils.isEmpty(subjectName) && !TextUtils.isEmpty(pD) && !TextUtils.isEmpty(tD) && (TextUtils.isDigitsOnly(pD) && TextUtils.isDigitsOnly(tD))) {

                Map<String, Object> map = new HashMap<>();
                map.put("subjectName", subjectName);
                map.put("presentClasses", Integer.parseInt(pD));
                map.put("totalClasses", Integer.parseInt(tD));
                map.put("subjectId", subjectId);

                mDatabaseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        pb.setVisibility(View.GONE);
                        mDatabaseRef.updateChildren(map);
                        Toast.makeText(EditSubjectActivity.this, "Updated Course Successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditSubjectActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        pb.setVisibility(View.GONE);
                        Toast.makeText(EditSubjectActivity.this, "Failed to Update Course! Please Try again Later", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn_removeSubject.setOnClickListener(v -> removeSubject());
    }

    private void removeSubject() {
        mDatabaseRef.removeValue();
        Toast.makeText(this, "Course Deleted Successfully!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditSubjectActivity.this, MainActivity.class));
        finish();
    }

}