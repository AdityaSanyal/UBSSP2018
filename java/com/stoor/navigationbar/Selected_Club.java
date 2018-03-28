package com.stoor.navigationbar;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Selected_Club extends AppCompatActivity {

    ImageView clubImage;
    Button joinButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected__club);

        init();
    }

    public void init() {

        clubImage = (ImageView) findViewById(R.id.clubImage);
        joinButton = (Button) findViewById(R.id.joinButton);

    }
}