package com.example.korealong.qrcodescanner;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.EAN13Reader;
import com.google.zxing.qrcode.QRCodeWriter;

import net.glxn.qrgen.android.QRCode;
import net.glxn.qrgen.core.scheme.VCard;

import java.util.logging.Logger;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static com.example.korealong.qrcodescanner.Add.edtName;
import static com.example.korealong.qrcodescanner.Add.edtNumber;
import static com.example.korealong.qrcodescanner.Add.imgShow;


public class Scan extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        edtName.setText(result.getText());
        /*VCard lVCard=new VCard("xyz")
                .setEmail("xyz@gmail.com")
                .setAddress("India")
                .setTitle("Androidlift")
                .setCompany("Sayosoft Technology")
                .setPhoneNumber("0000000")
                .setWebsite("www.androidlift.info");*/
        edtNumber.setText(result.getBarcodeFormat().toString());
        Bitmap lBitmap= QRCode.from(String.valueOf(result.getText())).bitmap();
        imgShow.setImageBitmap(lBitmap);
        onBackPressed();
    }
}