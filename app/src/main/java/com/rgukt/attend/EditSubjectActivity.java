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

import java.util.HashMap;
import java.util.Map;

public class EditSubjectActivity extends AppCompatActivity {

    private TextInputEditText edt_subjectName, edt_PresentClasses, edt_TotalClasses, edt_rec_percent;
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

        FirebaseApp.initializeApp(/*context=*/ this);
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(
                SafetyNetAppCheckProviderFactory.getInstance());

        edt_subjectName = findViewById(R.id.edt_sub_name);
        edt_PresentClasses = findViewById(R.id.edt_present_days);
        edt_TotalClasses = findViewById(R.id.edt_total_days);
        edt_rec_percent = findViewById(R.id.edt_rec_percent);
        btn_updateSubject = findViewById(R.id.btn_update_subject);
        btn_removeSubject = findViewById(R.id.btn_remove_subject);
        pb = findViewById(R.id.pb_loading);

        mDatabase = FirebaseDatabase.getInstance();

        subject = getIntent().getParcelableExtra("subject");
        if (subject != null) {
            edt_subjectName.setText(subject.getSubjectName());
            edt_PresentClasses.setText(String.valueOf(subject.getPresentClasses()));
            edt_TotalClasses.setText(String.valueOf(subject.getTotalClasses()));
            edt_rec_percent.setText(String.valueOf(subject.getRecommendedPercentage()));
            subjectId = subject.getSubjectId();
        }

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account == null) {
            startActivity(new Intent(EditSubjectActivity.this, LoginActivity.class));
            finish();
        }

        mDatabaseRef = mDatabase.getReference(account.getId()).child("Subjects").child(StringUtils.capitalize(subjectId));

        btn_updateSubject.setOnClickListener(v -> {

            String subjectName = edt_subjectName.getText().toString();
            String pD = edt_PresentClasses.getText().toString();
            String tD = edt_TotalClasses.getText().toString();
            String recPercent = edt_rec_percent.getText().toString();
            recPercent = (recPercent.equals("") || recPercent.equals("0")) ? "75" : recPercent;

            if (!TextUtils.isEmpty(subjectName) && !TextUtils.isEmpty(pD) && !TextUtils.isEmpty(recPercent)
                    && !TextUtils.isEmpty(tD) && (TextUtils.isDigitsOnly(pD) && TextUtils.isDigitsOnly(tD)
                    && TextUtils.isDigitsOnly(recPercent))) {

                Map<String, Object> map = new HashMap<>();
                map.put("subjectName", StringUtils.capitalize(subjectName));
                map.put("presentClasses", Integer.parseInt(pD));
                map.put("totalClasses", Integer.parseInt(tD));
                map.put("recPercentage", Integer.parseInt(recPercent));
                map.put("subjectId", StringUtils.capitalize(subjectId));

                mDatabaseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        pb.setVisibility(View.GONE);
                        mDatabaseRef.updateChildren(map);
                        Toast.makeText(EditSubjectActivity.this,
                                "Updated Subject Successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditSubjectActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        pb.setVisibility(View.GONE);
                        Toast.makeText(EditSubjectActivity.this,
                                "Failed to Update Subject! Please Try again Later", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn_removeSubject.setOnClickListener(v -> removeSubject());
    }

    private void removeSubject() {
        mDatabaseRef.removeValue().addOnSuccessListener(v -> {
            Toast.makeText(EditSubjectActivity.this, "Subject Deleted Successfully!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(EditSubjectActivity.this, MainActivity.class));
            finish();
        });

    }

}