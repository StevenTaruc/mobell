package com.example.mobell;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        String msg = intent.getStringExtra(MainActivity.LOCATION_MESSAGE);
        TextView textView = findViewById(R.id.locationInfo);
        textView.setText(msg);
        msg = intent.getStringExtra(MainActivity.REQUEST_MESSAGE);
        textView = findViewById(R.id.requestDetails);
        textView.setText(msg);
    }
}
