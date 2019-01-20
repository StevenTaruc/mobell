package com.example.mobell;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AssistanceInboundActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistance_inbound);
        /**
         * Framework for being able to support handling of messages
         * Commented for development of UI (we do not want things being put where they are not expected)
         */
        /*
        Intent intent = getIntent();
        String msg = intent.getStringExtra(MainActivity.LOCATION_MESSAGE);
        TextView textView = findViewById(R.id.locationInfo);
        // There is nothing to set!
        // textView.setText(msg);
        msg = intent.getStringExtra(MainActivity.REQUEST_MESSAGE);
        textView = findViewById(R.id.requestDetails);
        // There is nothing to set!
        // textView.setText(msg);
        */
    }
}
