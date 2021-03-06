package com.example.hackwesternapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class RecruiterMainActivity extends AppCompatActivity {
    static final String NAME = "com.example.hackwesternapp.NAME";
    static final String EMAIL = "com.example.hackwesternapp.EMAIL";
    static final String RATING = "com.example.hackwesternapp.RATING";
    static final String FAVOURITE = "com.example.hackwesternapp.FAVOURITE";
    static final String ID = "com.example.hackwesternapp.ID";
    static final String CLASS = "com.example.hackwesternapp.CLASS";
    static final String UI_STRING = "com.example.hackwesternapp.UI_STRING";
    static final String PDF_URL = "com.example.hackwesternapp.PDF_URL";

    static int fav;
    static int notfav;

    final String pClass = "AccountData";

    private List<ApplicantData> applicantList;
    private RecyclerView recyclerView;
    private RecruiterListAdapter mAdapter;

    String uiString;
    String companyClass;

    int clickedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_main);

        fav = getResources().getIdentifier("com.example.hackwesternapp:drawable/ic_favourite_selected", null, null);
        notfav = getResources().getIdentifier("com.example.hackwesternapp:drawable/ic_favourite_unselected", null, null);

        recyclerView = (RecyclerView) findViewById(R.id.applicant_list_view);
        applicantList = new ArrayList<>();

        mAdapter = new RecruiterListAdapter((List) applicantList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        Intent intent = getIntent();
        String input = intent.getStringExtra(MainActivity.INPUT);
        int index = input.indexOf((char) 03);
        companyClass = input.substring(0, index);
        uiString = input.substring(index + 1);
    }

    public void scanQrCode(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }

    // Get the results:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            ApplicantData ad = applicantList.get(clickedItem);
            Bundle extras = data.getExtras();

            ad.setRating(extras.getInt(RATING));
            ad.setFavourite(extras.getBoolean(FAVOURITE));
            mAdapter.notifyDataSetChanged();
        }
        else {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    String tmp = result.getContents(); // Used to store the results of the QR scan

                    if (tmp.indexOf(',') == -1)
                        getData(tmp);
                    else
                        Toast.makeText(this, "Invalid QR Code", Toast.LENGTH_LONG).show();
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    void getData(final String id) {
        final Activity a = this;
        final ParseObject recruiterData = new ParseObject(companyClass);
        final ApplicantData newApplicant = new ApplicantData("", "");

        ParseQuery<ParseObject> query = ParseQuery.getQuery(pClass);
        query.getInBackground(id, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    newApplicant.setName(object.getString("Name"));
                    newApplicant.setEmail(object.getString("Email"));
                    newApplicant.setUrl(((ParseFile) object.get("data_pdf")).getUrl());
                    pushToParse(recruiterData, newApplicant);
                } else {
                    Toast.makeText(a, "Failed to get data", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    void pushToParse(final ParseObject recruiterData, final ApplicantData newApplicant) {
        final Activity a = this;
        recruiterData.put("Name", newApplicant.getName());
        recruiterData.put("Email", newApplicant.getEmail());
        recruiterData.put("Rating", newApplicant.getRating());
        recruiterData.put("Favourite", newApplicant.isFavourite());
        recruiterData.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    newApplicant.setId(recruiterData.getObjectId());
                    addApplicant(newApplicant, applicantList);
                    mAdapter.notifyDataSetChanged();
                    viewApplicant(newApplicant);
                } else {
                    Toast.makeText(a, "Couldn't get new id!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    void viewApplicant(final ApplicantData newApplicant, int result) {
        Intent intent = new Intent(this, ViewApplicantActivity.class);
        intent.putExtra(NAME, newApplicant.getName());
        intent.putExtra(EMAIL, newApplicant.getEmail());
        intent.putExtra(RATING, newApplicant.getRating());
        intent.putExtra(FAVOURITE, newApplicant.isFavourite());
        intent.putExtra(ID, newApplicant.getId());
        intent.putExtra(CLASS, companyClass);
        intent.putExtra(UI_STRING, uiString);
        intent.putExtra(PDF_URL, newApplicant.getUrl());

        startActivityForResult(intent, 2);
    }

    void viewApplicant(final ApplicantData newApplicant) {
        viewApplicant(newApplicant, 0);
    }

    void addApplicant(ApplicantData ad, List<ApplicantData> al) {
        int i = 0;

        while(i < al.size()) {
            if (ad.compareTo(al.get(i)) >= 0) {
                al.add(i, ad);
                return;
            }

            i++;
        }

        al.add(ad);
    }
}
