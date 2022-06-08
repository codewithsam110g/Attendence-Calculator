package com.rgukt.attend;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tel, eng, mat, phy, chem, it;

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

    }
}