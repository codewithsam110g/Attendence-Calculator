package com.rgukt.attend;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class AttendenceActivity extends Activity {

    TextView custom;
    Button confirm;
    RadioGroup rg1, rg2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence);

        custom = findViewById(R.id.txt_custom);
        confirm = findViewById(R.id.btn_confirm);
        rg1 = findViewById(R.id.rg1);
        rg2 = findViewById(R.id.rg2);
        rg2.clearCheck();


        custom.setOnClickListener(v -> {
            Toast.makeText(this, "Still need to implement this function!", Toast.LENGTH_SHORT).show();
        });

        confirm.setOnClickListener(v -> {
            Toast.makeText(this, "Still need to implement this function!", Toast.LENGTH_SHORT).show();
        });
    }


    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        String str = "";
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rbfdp:
                if (checked) {
                    rg2.clearCheck();
                    str = "Full Day Present";
                    break;
                }
            case R.id.rbfda:
                if (checked) {
                    rg2.clearCheck();
                    str = "Full Day Absent";
                    break;
                }
            case R.id.rbhdmp:
                if (checked) {
                    rg1.clearCheck();
                    str = "Morning Present";
                    break;
                }
            case R.id.rbhdap:
                if (checked) {
                    rg1.clearCheck();
                    str = "Afternoon Present";
                    break;
                }
        }
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }


}
