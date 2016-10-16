package com.example.hackwesternapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.webkit.WebView;

public class ViewPdfActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);

        Intent intent = getIntent();
        String fileName = intent.getStringExtra(RecruiterMainActivity.PDF_NAME);

        WebView mWebView = new WebView(ViewPdfActivity.this);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + fileName);
        setContentView(mWebView);
    }
}
