package com.example.korealong.qrcodescanner;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.korealong.qrcodescanner.Sqlite.Database;

public class Detail extends AppCompatActivity {

    ImageView imgShowDetail;
    TextView lblNameCard;
    int i = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        addControl();
        initUI();
    }

    private void initUI() {
        Intent intent = getIntent();
        i = intent.getIntExtra("ID",-1);
        SQLiteDatabase database = Database.initDatabase(this, "MCardDB.sqlite");
        Cursor cursor = database.rawQuery("SELECT * FROM Card WHERE id = ?",new String[]{i + ""});
        cursor.moveToFirst();
        String nameCard =  cursor.getString(1);
        byte[] image = cursor.getBlob(3);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
        imgShowDetail.setImageBitmap(bitmap);
        lblNameCard.setText(nameCard);
    }
    private void addControl() {
        imgShowDetail = (ImageView) findViewById(R.id.imgShowDetail);
        lblNameCard =  findViewById(R.id.lblNameCard);
    }


}
