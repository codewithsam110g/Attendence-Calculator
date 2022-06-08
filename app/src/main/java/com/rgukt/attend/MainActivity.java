package com.rgukt.attend;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private TextView tel, eng, mat, phy, chem, it;
    private FirebaseDatabase mRef;
    private DatabaseReference mDataRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tel = findViewById(R.id.txt_tel);
        eng = findViewById(R.id.txt_eng);
        mat = findViewById(R.id.txt_mat);
        phy = findViewById(R.id.txt_phy);
        chem = findViewById(R.id.txt_chem);
        it = findViewById(R.id.txt_it);

        mRef = FirebaseDatabase.getInstance();
        mDataRef = mRef.getReference("Data");

        UserData data;
        data = new UserData(10, 8, 8, 8, 8, 8, 8, 8);

        mDataRef.setValue(data);

    }
}