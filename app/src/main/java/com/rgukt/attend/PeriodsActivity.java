package com.rgukt.attend;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class PeriodsActivity extends AppCompatActivity {

    LinearLayout linear;
    Button gen, fin;
    EditText txt;
    CheckBox cb;
    List<String> subs = new ArrayList<>();

    List<EditText> txs = new ArrayList<>();
    int times;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periods);

        //TOOD: Need better Implementation of changing subjects

        linear = findViewById(R.id.lyt_linear);
        gen = findViewById(R.id.btn_gen);
        fin = findViewById(R.id.btn_fin);
        txt = findViewById(R.id.txt_subs);
        cb = findViewById(R.id.cb_edit);
        fin.setEnabled(false);
        gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                times = Integer.parseInt(
                        txt.getText().toString().trim().length() !=0 ?  txt.getText().toString().trim() : "0"
                );
                if(times == 0) Toast.makeText(PeriodsActivity.this, "Please Enter a Valid Number!", Toast.LENGTH_SHORT).show();
                linear.removeAllViews();
                txs.clear();
                for (int i = 0; i < times; i++) {
                    EditText ed;
                    ed = new EditText(PeriodsActivity.this);
                    txs.add(ed);
                    ed.setHint("Please Enter a Subject Name");
                    ed.setBackgroundColor(getColor(R.color.teal_700));
                    ed.setId(i+5);
                    ed.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    linear.addView(ed);
                }
                fin.setEnabled(true);
            }
        });

        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(PeriodsActivity.this);
                if (acc == null) return;
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference(acc.getId()).child("SubjectList");

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(cb.isChecked()){
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                subs.add(ds.getValue(String.class));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                ref.removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        for (EditText e : txs) {
                            String s = e.getText().toString();
                            ref.child(StringUtils.capitalize(s)).setValue(StringUtils.capitalize(s));
                        }
                        for (String s : subs) {
                            ref.child(StringUtils.capitalize(s)).setValue(StringUtils.capitalize(s));
                        }
                    }
                });

                startActivity(new Intent(PeriodsActivity.this, HomeActivity.class));
                finish();
            }
        });


    }
}