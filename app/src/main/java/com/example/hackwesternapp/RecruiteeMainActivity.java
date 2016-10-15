package com.example.hackwesternapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class RecruiteeMainActivity extends AppCompatActivity {
    static final String EXTRA_QR_STRING = "com.example.hackwesternapp.EXTRA_QR_STRING";

    ArrayAdapter<String> adapter;
    ArrayList<EncryptData> resumeList;
    ArrayList<String> names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruitee_main);

        resumeList = new ArrayList<>();
        names = new ArrayList<>();

        ListView listView = (ListView) findViewById(R.id.recruitee_list_view);

        adapter = new ArrayAdapter(this, R.layout.adapter_recruitee_list, names);
        listView.setAdapter(adapter);

        final Context context = this;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EncryptData ri = resumeList.get(position);
                Intent intent = new Intent(context, ShowQrCodeActivity.class);
                intent.putExtra(EXTRA_QR_STRING, ri.getId() + " " + ri.getKey());
                startActivity(intent);
            }

        });
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
                EncryptData ri = new EncryptData(tmp2[1], tmp2[2]);
                resumeList.add(ri);
                names.add(tmp2[0]);
                adapter.notifyDataSetChanged();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
