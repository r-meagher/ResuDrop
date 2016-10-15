package com.example.hackwesternapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewApplicantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_applicant);

        Intent intent = getIntent();
        String name = intent.getStringExtra(RecruiterMainActivity.APPLICANT_NAME);

        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText("Scanned " + name);
    }
}
