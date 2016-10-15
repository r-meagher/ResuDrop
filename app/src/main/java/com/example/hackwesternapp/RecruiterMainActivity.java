package com.example.hackwesternapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.SortedList;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class RecruiterMainActivity extends AppCompatActivity {
    static final String APPLICANT_NAME = "com.example.hackwesternapp.APPLICANT_NAME";

    private List<ApplicantData> applicantList;
    private RecyclerView recyclerView;
    private RecruiterListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_main);

        recyclerView = (RecyclerView) findViewById(R.id.applicant_list_view);
        applicantList = new ArrayList<>();

        mAdapter = new RecruiterListAdapter((List) applicantList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        addTestData();
        mAdapter.notifyDataSetChanged();
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

    void addTestData() {
        for (int i = 0; i < 100; i++) {
            applicantList.add(new ApplicantData("Name " + i, "Email " + i));
        }
    }
}
