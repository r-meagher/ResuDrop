package com.example.hackwesternapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class RecruiterMainActivity extends AppCompatActivity {
    static final String APPLICANT_NAME = "com.example.hackwesternapp.APPLICANT_NAME";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ArrayList<ApplicantData> applicantList;
    ArrayList<String> names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recruiter_recycler_view);

        applicantList = new ArrayList<>();
        names = new ArrayList<>();

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(new ApplicantData[] {});
        mRecyclerView.setAdapter(mAdapter);
    }

    public void scanQrCode(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }

    // Get the results:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                String tmp1 = result.getContents();
                String tmp2[] = tmp1.split(" ");

                Intent intent = new Intent(this, ViewApplicantActivity.class);
                intent.putExtra(APPLICANT_NAME, tmp2[0]);
                startActivity(intent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
