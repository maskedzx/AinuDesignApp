package com.example.yuuya.ainudesignapp;

import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;


public class MakeActivity extends ActionBarActivity {
    private static final boolean DEBUG = false;
    private static final String TAG = "MakeActivity";

    private Button addMoreuButton;
    private FrameLayout workFrame;
    private ArrayList<ImageView> designParts;
    private ImageView moreu;
    private Uri mUri;
    private int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make);

        if (savedInstanceState != null) {
            mUri = savedInstanceState.getParcelable("URI");
        }

        setParts();
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
        designParts = new ArrayList<ImageView>();
        addMoreuButton = (Button) findViewById(R.id.addMoreu);
        workFrame = (FrameLayout) findViewById(R.id.workFrame);
    }

    private void setParts(){
        moreu = new ImageView(this);
        moreu.setImageResource(R.drawable.moreu);
    }

    @Override
    public void onSaveInstanceState(final Bundle args) {
        if (DEBUG) Log.v(TAG, "onSaveInstanceState:" + args);
        args.putParcelable("URI", mUri);
        super.onSaveInstanceState(args);


    }

    public void addMoreu(View view){
        designParts.add(moreu);
        workFrame.addView(designParts.get(num));
        num++;
    }
}
