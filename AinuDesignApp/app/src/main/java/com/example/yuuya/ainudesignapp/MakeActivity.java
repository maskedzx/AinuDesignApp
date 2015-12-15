package com.example.yuuya.ainudesignapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class MakeActivity extends ActionBarActivity {
    private static final boolean DEBUG = false;
    private static final String TAG = "MakeActivity";

    private ImageButton addMoreuButton;
    private ImageButton addMoreuButton2;
    private ImageButton addAiushiButton;
    private ImageButton addShikuButton;
    private Button subDesignButton;
    private Button saveButton;
    private FrameLayout workFrame;
    private Uri mUri;
    private int num = 0;
    private View designView;
    private int saveNum;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make);

        if (savedInstanceState != null) {
            mUri = savedInstanceState.getParcelable("URI");
        }

        setView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_make, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setView(){
        addMoreuButton = (ImageButton) findViewById(R.id.addMoreu);
        addMoreuButton2 = (ImageButton) findViewById(R.id.addMoreu2);
        addAiushiButton = (ImageButton) findViewById(R.id.addAiushi);
        addShikuButton = (ImageButton) findViewById(R.id.addShiku);
        subDesignButton = (Button) findViewById(R.id.subDesign);
        saveButton = (Button) findViewById(R.id.save);
        workFrame = (FrameLayout) findViewById(R.id.workFrame);
        workFrame.setBackgroundColor(Color.rgb(255,255,255));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("saveNum",saveNum);
    }

    public void addMoreu(View view){
        designView = getLayoutInflater().inflate(R.layout.moreu, null);
        workFrame.addView(designView);
        designView.setId(num);
        num++;
    }

    public void addMoreu2(View view){
        designView = getLayoutInflater().inflate(R.layout.moreu2, null);
        workFrame.addView(designView);
        designView.setId(num);
        num++;
    }

    public void addShiku(View view){
        designView = getLayoutInflater().inflate(R.layout.shiku, null);
        workFrame.addView(designView);
        designView.setId(num);
        num++;
    }
    public void addAiushi(View view){
        designView = getLayoutInflater().inflate(R.layout.aiushi, null);
        workFrame.addView(designView);
        designView.setId(num);
        num++;
    }

    public void subDesign(View view){
        if(num>0) {
            workFrame.removeViewAt(num);
            num--;
        }
    }


}
