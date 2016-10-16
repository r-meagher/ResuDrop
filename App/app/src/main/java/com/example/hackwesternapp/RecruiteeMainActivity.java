package com.example.hackwesternapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class RecruiteeMainActivity extends AppCompatActivity {
    static final String EXTRA_QR_STRING = "com.example.hackwesternapp.EXTRA_QR_STRING";
    static final String EXTRA_NAME = "com.example.hackwesternapp.EXTRA_NAME";

    private List<EncryptData> resumeList;
    private RecyclerView recyclerView;
    private ApplicantListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruitee_main);

        recyclerView = (RecyclerView) findViewById(R.id.applicant_list_view);
        resumeList = new ArrayList<>();

        mAdapter = new ApplicantListAdapter(resumeList);
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
                String tmp1 = result.getContents();
                String tmp2[] = tmp1.split(",");

                if (tmp2.length != 3)
                    Toast.makeText(this, "Invalid QR Code", Toast.LENGTH_LONG).show();
                else {
                    EncryptData ri = new EncryptData(tmp2[0], tmp2[1], tmp2[2]);
                    resumeList.add(ri);
                    mAdapter.notifyDataSetChanged();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
