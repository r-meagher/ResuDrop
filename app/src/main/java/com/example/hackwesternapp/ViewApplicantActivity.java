package com.example.hackwesternapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;

public class ViewApplicantActivity extends AppCompatActivity {
    private ArrayList<CustomUiFieldData> fields;
    private String parseId;
    private String parseClass;
    private String pdfUrl;
    private boolean favourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_applicant);
        final ViewGroup layout = (ViewGroup) findViewById(R.id.view_applicant_layout);
        final Context context = this;

        Intent intent = getIntent();
        final String name = intent.getStringExtra(RecruiterMainActivity.NAME);
        final String email = intent.getStringExtra(RecruiterMainActivity.EMAIL);
        final int rating = intent.getIntExtra(RecruiterMainActivity.RATING, 0);
        favourite = intent.getBooleanExtra(RecruiterMainActivity.FAVOURITE, false);
        final String uiComponents = intent.getStringExtra(RecruiterMainActivity.UI_STRING);

        pdfUrl = intent.getStringExtra(RecruiterMainActivity.PDF_URL);
        parseId = intent.getStringExtra(RecruiterMainActivity.ID);
        parseClass = intent.getStringExtra(RecruiterMainActivity.CLASS);

        ParseQuery<ParseObject> query = ParseQuery.getQuery(parseClass);
        query.getInBackground(parseId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    ((TextView) layout.findViewById(R.id.name)).setText(name);
                    ((TextView) layout.findViewById(R.id.email)).setText(email);
                    setFavouriteImage(((ImageButton) layout.findViewById(R.id.favourite)));
                    ((TextView) layout.findViewById(R.id.rating)).setText(String.valueOf(rating));

                    String components[] = uiComponents.split(Character.toString((char) 03));
                    fields = new ArrayList<>();
                    int numComponents = Integer.parseInt(components[0]);

                    for (int i = 0; i < numComponents; i++) {
                        CustomUiFieldData cufd = new CustomUiFieldData(components[i+1]);
                        fields.add(cufd);
                        layout.addView(cufd.createView(context));
                        getData(cufd, object);
                    }
                }
            }
        });
    }

    public void viewResume(View view) {
        Intent intent = new Intent(this, ViewPdfActivity.class);
        intent.putExtra(RecruiterMainActivity.PDF_URL, pdfUrl);
        this.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        final int rating = Integer.parseInt(((EditText) findViewById(R.id.rating)).getText().toString());
        final boolean favourite = this.favourite;

        ParseQuery<ParseObject> query = ParseQuery.getQuery(parseClass);
        query.getInBackground(parseId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    object.put("Rating", rating);
                    object.put("Favourite", favourite);

                    for (CustomUiFieldData cufd : fields)
                        setData(cufd, object);

                    object.saveInBackground();
                }
            }
        });

        Intent intent = new Intent();
        intent.putExtra(RecruiterMainActivity.RATING, rating);
        intent.putExtra(RecruiterMainActivity.FAVOURITE, favourite);
        setResult(RESULT_OK, intent);

        finish();
    }

    public void toggleFavourite(View view) {
        favourite = !favourite;
        setFavouriteImage((ImageButton) view);
    }

    void setFavouriteImage(ImageButton button) {
        String path = (favourite) ? "ic_favourite_selected" : "ic_favourite_unselected";
        button.setImageResource(getResources().getIdentifier("com.example.hackwesternapp:drawable/" + path, null, null));
    }

    void setData(CustomUiFieldData cufd, ParseObject object) {
        String text;

        switch(cufd.type) {
            case CustomUiFieldData.NUMBER:
                text = ((EditText) cufd.getInputView()).getText().toString();
                object.put(cufd.id, Integer.parseInt(text));
                break;

            case CustomUiFieldData.BOOLEAN:
                boolean b = ((Switch) cufd.getInputView()).isChecked();
                object.put(cufd.id, b);
                break;

            case CustomUiFieldData.TEXT:
                text = ((EditText) cufd.getInputView()).getText().toString();
                object.put(cufd.id, text);
                break;
        }
    }

    void getData(CustomUiFieldData cufd, ParseObject object) {
        switch(cufd.type) {
            case CustomUiFieldData.NUMBER:
                ((EditText) cufd.getInputView()).setText(object.getInt(cufd.id));
                break;

            case CustomUiFieldData.BOOLEAN:
                ((Switch) cufd.getInputView()).setChecked(object.getBoolean(cufd.id));
                break;

            case CustomUiFieldData.TEXT:
                ((EditText) cufd.getInputView()).setText(object.getString(cufd.id));
                break;
        }
    }
}
