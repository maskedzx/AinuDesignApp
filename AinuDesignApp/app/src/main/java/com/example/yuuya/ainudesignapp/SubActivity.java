package com.example.yuuya.ainudesignapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class SubActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Intent intent = getIntent();
        String imagePath = intent.getStringExtra("imagePath");
        ImageView imageView = (ImageView) findViewById(R.id.selectedImage);
        Bitmap bmp = BitmapFactory.decodeFile(imagePath);
        imageView.setImageBitmap(bmp);
    }

}
