package com.example.hackwesternapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.ArrayList;

public class ViewApplicantActivity extends AppCompatActivity {
    private ArrayList<CustomUiFieldData> fields;
    private String parseId;
    private String parseClass;
    private String pdfUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_applicant);
        ViewGroup layout = (ViewGroup) findViewById(R.id.view_applicant_layout);

        Intent intent = getIntent();
        String name = intent.getStringExtra(RecruiterMainActivity.NAME);
        String email = intent.getStringExtra(RecruiterMainActivity.EMAIL);
        int rating = intent.getIntExtra(RecruiterMainActivity.RATING, 0);
        boolean favourite = intent.getBooleanExtra(RecruiterMainActivity.FAVOURITE, false);

        pdfUrl = intent.getStringExtra(RecruiterMainActivity.PDF_URL);
        parseId = intent.getStringExtra(RecruiterMainActivity.ID);
        parseClass = intent.getStringExtra(RecruiterMainActivity.CLASS);

        ((TextView) layout.findViewById(R.id.name)).setText(name);
        ((TextView) layout.findViewById(R.id.email)).setText(email);
        setFavouriteImage(((ImageButton) layout.findViewById(R.id.favourite)), favourite);
        ((TextView) layout.findViewById(R.id.rating)).setText(String.valueOf(rating));

        String uiComponents = intent.getStringExtra(RecruiterMainActivity.UI_STRING);
        String components[] = uiComponents.split(Character.toString((char) 03));
        fields = new ArrayList<>();
        int numComponents = Integer.parseInt(components[0]);

        for (int i = 0; i < numComponents; i++) {
            CustomUiFieldData cufd = new CustomUiFieldData(components[i+1]);
            fields.add(cufd);
            layout.addView(cufd.createView(this));
        }
    }

    public void viewResume(View view) {
        Intent intent = new Intent(this, ViewPdfActivity.class);
        intent.putExtra(RecruiterMainActivity.PDF_URL, pdfUrl);
        this.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        final Context c = this;
        final ArrayList<CustomUiFieldData> fields = this.fields;

        ParseObject pass = new ParseObject(parseClass);


        finish();
        return;
    }

    void setFavouriteImage(ImageButton button, boolean favourite) {
        String path = (favourite) ? "ic_favourite_selected" : "ic_favourite_unselected";
        button.setImageResource(getResources().getIdentifier("yourpackagename:drawable/" + path, null, null));
    }
}
