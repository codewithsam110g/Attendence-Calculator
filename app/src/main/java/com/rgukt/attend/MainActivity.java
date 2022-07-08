package com.rgukt.attend;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rgukt.attend.objects.SubjectData;
import com.rgukt.attend.objects.SubjectViewData;
import com.rgukt.attend.utils.SubjectViewAdapter;
import com.rgukt.attend.utils.Utils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SubjectViewAdapter.SubjectViewClickInterface {

    private RecyclerView rv;
    private ProgressBar pb;
    private FloatingActionButton fab;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    private ArrayList<SubjectViewData> viewData;
    private RelativeLayout bottomSheet;
    private SubjectViewAdapter adapter;
    private GoogleSignInAccount account;
    private TextView txtNoData;


    private int count = 0;
    private int countLimit = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        account = GoogleSignIn.getLastSignedInAccount(this);
        if (account == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        rv = findViewById(R.id.rv_courses);
        pb = findViewById(R.id.pb_loading);
        fab = findViewById(R.id.btn_fab);
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mDatabase.getReference(account.getId()).child("Subjects");
        viewData = new ArrayList<>();
        bottomSheet = findViewById(R.id.rlb_sheet);
        adapter = new SubjectViewAdapter(viewData, this, this);
        txtNoData = findViewById(R.id.txt_no_data);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        fab.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddSubjectActivity.class)));

        txtNoData.setVisibility(View.GONE);

        getAllSubjects();
        if (viewData.size() != 0) {
            txtNoData.setVisibility(View.GONE);
        }

    }

    private void getAllSubjects() {
        viewData.clear();
        mDatabaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                pb.setVisibility(View.GONE);
                SubjectData dat = snapshot.getValue(SubjectData.class);
                viewData.add(Utils.toSubjectViewData(dat));
                adapter.notifyDataSetChanged();
                txtNoData.setVisibility(View.GONE);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                pb.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
                txtNoData.setVisibility(View.GONE);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                pb.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();

                if (viewData.size() == 0) {
                    txtNoData.setVisibility(View.VISIBLE);
                } else {
                    txtNoData.setVisibility(View.GONE);
                }

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                pb.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
                txtNoData.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (viewData.size() == 0) {
            pb.setVisibility(View.GONE);
            txtNoData.setVisibility(View.VISIBLE);
        } else {
            pb.setVisibility(View.GONE);
            txtNoData.setVisibility(View.GONE);
        }

    }


    @Override
    public void onSubjectClick(int position) {
        displayBottomSheet(viewData.get(position));
    }

    private void displayBottomSheet(SubjectViewData dat) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialog, bottomSheet);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView subName = layout.findViewById(R.id.txt_subject_name);
        TextView present = layout.findViewById(R.id.txt_present_classes);
        TextView absent = layout.findViewById(R.id.txt_absent_classes);
        TextView total = layout.findViewById(R.id.txt_total_classes);
        TextView status = layout.findViewById(R.id.txt_current_status);
        Button edit = layout.findViewById(R.id.btn_edit_subject);
        Button view = layout.findViewById(R.id.btn_view_status);

        SubjectData sub = Utils.toSubjectData(dat);

        subName.setText(dat.getSubjectName());
        present.setText("You are Present for: " + sub.getPresentClasses() + " classes");
        absent.setText("You are Absent for: " + (sub.getTotalClasses() - sub.getPresentClasses()) + " classes");
        total.setText("Total Classes Conducted: " + sub.getPresentClasses());
        status.setText("Your Current Percentage is: " + dat.getPresentPercent() + "%");

        edit.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, EditSubjectActivity.class);
            i.putExtra("subject", sub);
            startActivity(i);
            finish();
        });

        view.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            String res;
            float val = (Float.parseFloat(dat.getPresentPercent()) - sub.getRecPercentage());
            int x = sub.getPresentClasses();
            int y = sub.getTotalClasses();
            float sp = sub.getRecPercentage();
            float days;
            if (val > 0) {
                days = (100 * x / sp) - y;
                days = (float) Math.floor(days);
                if (days > 1) {
                    res = "You can take leave for: " + days + " more classes";
                } else {
                    res = "You are close to Recommended percentage! So don't be Absent until you are present for a few more days!";
                }

            } else if (val < 0) {
                days = ((sp * y) - (100 * x)) / (100 - sp);
                days = (float) Math.ceil(days);
                if (days > 1) {
                    res = "you have to attend upcoming: " + days + " classes to keep Your Percentage";
                } else {
                    res = "You are close to Recommended percentage! So don't be Absent until you are present for a few more days!";
                }
            } else {
                res = "You are right on Recommended percentage! So don't be Absent until you are present for a few more days!";
            }

            dialog.setMessage(res);
            dialog.setTitle("Present or Absent?");

            dialog.setNeutralButton("Ok", (dialog1, which) -> {
                Toast.makeText(this, "You should do that and Keep up your Attendence", Toast.LENGTH_SHORT).show();
            });
            dialog.setCancelable(false);

            AlertDialog alertDialog = dialog.create();
            alertDialog.show();

        });

    }

    @Override
    public void onBackPressed() {
        count++;
        if (count < countLimit) {
            Toast.makeText(this, "Press back one more time to Exit", Toast.LENGTH_SHORT).show();
        } else if (count == countLimit) {
            super.onBackPressed();
        }
        //TODO: add timer to close so that it checks if user wants to close actually to prevent accidental close
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.logout: {
                Toast.makeText(this, "User Logged Out Successfully!", Toast.LENGTH_SHORT).show();
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
                GoogleSignInClient client = GoogleSignIn.getClient(MainActivity.this, gso);
                client.signOut();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                this.finish();
                return true;
            }
            case R.id.settings: {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                finish();
                return true;
            }
            case R.id.time_table:{
                startActivity(new Intent(MainActivity.this, TimeTableActivity.class));
                finish();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

}
