package com.example.hackwesternapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.parse.Parse;
import com.parse.ParseObject;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Parse.initialize(new Parse.Configuration.Builder(this)
        .applicationId("resudrop2016")
        .server("http://parseserver-3353q-env.us-east-1.elasticbeanstalk.com/parse").build());
    }

    public void selectRecruiter(View view) {
        Intent intent = new Intent(this, RecruiterMainActivity.class);
        startActivity(intent);
    }

    public void selectRecruitee(View view) {
        Intent intent = new Intent(this, RecruiteeMainActivity.class);
        startActivity(intent);
    }
}
