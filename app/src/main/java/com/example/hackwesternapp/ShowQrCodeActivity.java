package com.example.hackwesternapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class ShowQrCodeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_qr_code);

        ImageView imageView = (ImageView) findViewById(R.id.imageViewQrCode);
        TextView textView = (TextView) findViewById(R.id.textViewCompanyName);

        Intent intent = getIntent();
        String message = intent.getStringExtra(RecruiteeMainActivity.EXTRA_QR_STRING);
        String name = intent.getStringExtra(RecruiteeMainActivity.EXTRA_NAME);

        textView.setText("Hello! I'm " + name);

        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix matrix = qrCodeWriter.encode(message, BarcodeFormat.QR_CODE, 400, 400);
            imageView.setImageBitmap(toBitmap(matrix));
        } catch (WriterException ex) {
            ex.printStackTrace();
        }
    }

    private Bitmap toBitmap(BitMatrix matrix) {
        int height = matrix.getHeight();
        int width = matrix.getWidth();
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bmp.setPixel(x, y, matrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bmp;
    }
}
