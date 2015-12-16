package com.example.yuuya.ainudesignapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
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
    private Button bgcolorButton;
    private Button subDesignButton;
    private Button saveButton;
    private FrameLayout workFrame;
    private Uri mUri;
    private int num = 0;
    private View designView;
    private View seekView;
    private int saveNum;
    private boolean seek = false;
    private ImageView moreu;
    private ImageView moreu2;
    private ImageView shiku;
    private ImageView aiushi;


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

    private void setView() {
        addMoreuButton = (ImageButton) findViewById(R.id.addMoreu);
        addMoreuButton2 = (ImageButton) findViewById(R.id.addMoreu2);
        addAiushiButton = (ImageButton) findViewById(R.id.addAiushi);
        addShikuButton = (ImageButton) findViewById(R.id.addShiku);
        subDesignButton = (Button) findViewById(R.id.subDesign);
        bgcolorButton = (Button) findViewById(R.id.bgcolor);
        saveButton = (Button) findViewById(R.id.save);
        workFrame = (FrameLayout) findViewById(R.id.workFrame);
        workFrame.setBackgroundColor(Color.rgb(255, 255, 255));
        registerForContextMenu(bgcolorButton);
    }


    @Override


    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, 0, 0, "赤");
        menu.add(0, 1, 1, "青");
        menu.add(0, 2, 2, "白");
        menu.add(0, 3, 0, "黒");
        menu.add(0, 4, 1, "緑");
        menu.add(0, 5, 2, "黄緑");
        menu.add(0, 6, 0, "紫");
        menu.add(0, 7, 1, "ピンク");
        menu.add(0, 8, 2, "グレー");
    }


    @Override


    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                workFrame.setBackgroundColor(Color.parseColor("#FF0000"));
                return true;
            case 1:
                workFrame.setBackgroundColor(Color.parseColor("#0000FF"));
                return true;
            case 2:
                workFrame.setBackgroundColor(Color.parseColor("#FFFFFF"));
                return true;
            case 3:
                workFrame.setBackgroundColor(Color.parseColor("#000000"));
                return true;
            case 4:
                workFrame.setBackgroundColor(Color.parseColor("#008000"));
                return true;
            case 5:
                workFrame.setBackgroundColor(Color.parseColor("#00FF00"));
                return true;
            case 6:
                workFrame.setBackgroundColor(Color.parseColor("#9900CC"));
                return true;
            case 7:
                workFrame.setBackgroundColor(Color.parseColor("#FF00FF"));
                return true;
            default:
                workFrame.setBackgroundColor(Color.parseColor("#BEBEBE"));
                return super.onContextItemSelected(item);
        }


    }


    public void bgcolor(View view) {
        openContextMenu(bgcolorButton);
    }

    public void setSeek(View view){
        if(seek==false){
            seekView = getLayoutInflater().inflate(R.layout.seekber, null);
            workFrame.addView(seekView);
            seek = true;
            setSeekBer();
        }else if(seek==true){
            workFrame.removeView(seekView);
            seek = false;
        }

    }

    public void setSeekBer(){
        SeekBar red =(SeekBar)findViewById(R.id.red);
        SeekBar green =(SeekBar)findViewById(R.id.green);
        SeekBar blue =(SeekBar)findViewById(R.id.blue);
        red.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int p, boolean fromUser) {
                moreu.setBackgroundColor(Color.rgb(p, 0, 0));
                moreu2.setBackgroundColor(Color.rgb(p, 0, 0));
                aiushi.setBackgroundColor(Color.rgb(p, 0, 0));
                shiku.setBackgroundColor(Color.rgb(p, 0, 0));
            }
        });
        green.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int p, boolean fromUser) {
                moreu.setBackgroundColor(Color.rgb(0, p, 0));
                moreu2.setBackgroundColor(Color.rgb(0, p, 0));
                aiushi.setBackgroundColor(Color.rgb(0,p, 0));
                shiku.setBackgroundColor(Color.rgb(0,p, 0));
            }
        });
        blue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int p, boolean fromUser) {
                moreu.setBackgroundColor(Color.rgb(0, 0, p));
                moreu2.setBackgroundColor(Color.rgb(0, 0,p));
                aiushi.setBackgroundColor(Color.rgb(0, 0, p));
                shiku.setBackgroundColor(Color.rgb(0, 0, p));
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("saveNum", saveNum);
    }

    public void addMoreu(View view) {
        designView = getLayoutInflater().inflate(R.layout.moreu, null);
        workFrame.addView(designView);
        num++;
        moreu = (ImageView)findViewById(R.id.moreuDesign);
    }

    public void addMoreu2(View view) {
        designView = getLayoutInflater().inflate(R.layout.moreu2, null);
        workFrame.addView(designView);
        num++;
        moreu2 = (ImageView)findViewById(R.id.moreuDesign2);
    }

    public void addShiku(View view) {
        designView = getLayoutInflater().inflate(R.layout.shiku, null);
        workFrame.addView(designView);
        num++;
        shiku = (ImageView)findViewById(R.id.shikuDesign);
    }

    public void addAiushi(View view) {
        designView = getLayoutInflater().inflate(R.layout.aiushi, null);
        workFrame.addView(designView);
        num++;
        aiushi = (ImageView)findViewById(R.id.aiushiDesign);
    }

    public void subDesign(View view) {
        if (num > 0) {
            num--;
            workFrame.removeViewAt(num);
        }
    }


}
