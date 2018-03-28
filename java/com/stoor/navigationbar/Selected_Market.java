package com.stoor.navigationbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Aditya on 27-Mar-18.
 */

public class Selected_Market extends AppCompatActivity {

    ImageView clubImage;
    Button joinButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected__market);

        init();
    }

    public void init() {

        clubImage = (ImageView) findViewById(R.id.marketImage);
        joinButton = (Button) findViewById(R.id.joinMarketButton);

    }
}
