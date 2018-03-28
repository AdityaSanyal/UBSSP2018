package com.stoor.navigationbar;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

   BottomNavigationView navBar;
   HomeFragment homeFragment;
   ClubsFragment clubsFragment;
   MarketFragment marketFragment;
   MessagesFragment messagesFragment;
   ProfileFragment profileFragment;

   RelativeLayout homeMainDisplay;
   FrameLayout mainFrame;
   RelativeLayout loginRelativeLayouot;

   Button login;
   Button register;

   final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        login.setOnClickListener(this);
        register.setOnClickListener(this);

        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.navHome:
                        setFragmant(homeFragment);
                        return true;

                   case R.id.navClubs:
                     setFragmant(clubsFragment);
                      return true;

                   case R.id.navMarket:
                        setFragmant(marketFragment);
                     return true;

                    case R.id.navMessages:
                        setFragmant(messagesFragment);
                        return true;

                    case R.id.navProfile:
                        setFragmant(profileFragment);
                        return true;

                    default:
                        return false;
                }
            }
        });

    }

    public void init() {

        mainFrame = (FrameLayout) findViewById(R.id.mainFrame);

        navBar = (BottomNavigationView) findViewById(R.id.navBar);
        homeFragment = new HomeFragment();
        clubsFragment = new ClubsFragment();
        marketFragment = new MarketFragment();
        messagesFragment = new MessagesFragment();
        profileFragment = new ProfileFragment();

        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);

        loginRelativeLayouot = (RelativeLayout) findViewById(R.id.loginRelativeLayout);
        homeMainDisplay = (RelativeLayout) findViewById(R.id.homeMainDisplay);

    }

    private void setFragmant(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame,fragment);
        fragmentTransaction.commit();
    }
    @Override
    public void onClick(View view) {
        setFragmant(homeFragment);
        loginRelativeLayouot.setVisibility(View.INVISIBLE);
        navBar.setVisibility(View.VISIBLE);

        if (view == findViewById(R.id.login)) {

            EditText editText1 = (EditText) findViewById(R.id.username);
            String username = editText1.getText().toString();
            EditText editText2 = (EditText) findViewById(R.id.password);
            String password = editText2.getText().toString();
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(MainActivity.this, "Username or password cannot be empty.", Toast.LENGTH_SHORT).show();
            } else {
                boolean flag;
                SQLiteHelper helper = new SQLiteHelper(this);
                flag = helper.loginSearch(username, password);
                Log.i("info","Verification received from DB");
                if (flag == false) {
//                    Toast.makeText(MainActivity.this, "Username or password is wrong.", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("info", "Login clicked");
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this,HomeFragment.class);
                    startActivity(intent);
                    finish();
                   setFragmant(homeFragment);
                   loginRelativeLayouot.setVisibility(View.INVISIBLE);
                    navBar.setVisibility(View.VISIBLE);
                }

            }

        }
        else if(view == findViewById(R.id.register))
        {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        if(requestCode == 888){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 888);
            }
            else {
                Toast.makeText(this, "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        if (requestCode == REQUEST_CODE_GALLERY) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(this, "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i("info","finally");

        if(requestCode == 888 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                clubsFragment.imageViewClub.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }


        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            Log.i("info","reached1");

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                clubsFragment.imageView.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
