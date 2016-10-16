package com.example.hackwesternapp;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class RecruiterMainActivity extends AppCompatActivity {
    static final String APPLICANT_DATA = "com.example.hackwesternapp.APPLICANT_NAME";
    static final String PDF_NAME = "com.example.hackwesternapp.PDF_NAME";

    private List<ApplicantData> applicantList;
    private RecyclerView recyclerView;
    private RecruiterListAdapter mAdapter;

    private SQLiteDatabase applicantDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_main);

        applicantDatabase = openOrCreateDatabase("Applicant Database",MODE_PRIVATE,null);

        recyclerView = (RecyclerView) findViewById(R.id.applicant_list_view);
        applicantList = new ArrayList<>();

        mAdapter = new RecruiterListAdapter((List) applicantList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
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
                String tmp1 = result.getContents(); // Used to store the results of the QR scan
                getData(this, tmp1);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    void getData(final Activity a, final String id) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("AccountData");
        query.getInBackground(id, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    ApplicantData newApplicant = new ApplicantData(object.getString("Name"), object.getString("Email"), id);
                    applicantList.add(newApplicant);
                    mAdapter.notifyDataSetChanged();

                    // Create a recruiter-specific applicant
                    ParseObject recruiterData = new ParseObject("RecruiterData");
                    recruiterData.put("Name", newApplicant.getName());
                    recruiterData.put("Email", newApplicant.getEmail());
                    recruiterData.put("Rating", newApplicant.getRating());
                    recruiterData.put("Favourite", newApplicant.isFavourite());
                    recruiterData.saveInBackground();

                    Intent intent = new Intent(a, ViewPdfActivity.class);
                    intent.putExtra(PDF_NAME, ((ParseFile) object.get("data_pdf")).getUrl());
                    startActivity(intent);
                } else {
                    Toast.makeText(a, "Failed to get data", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
