package com.krishna.securetimersample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.krishna.securetimer.SecureTimer;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView tvCurrentTime = (TextView) findViewById(R.id.tv_current_time);
        Button btnGetTime = (Button) findViewById(R.id.btn_get_time);
        btnGetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = SecureTimer.with(MainActivity.this).getCurrentDate();
                tvCurrentTime.setText(date.toString());
            }
        });
    }
}
