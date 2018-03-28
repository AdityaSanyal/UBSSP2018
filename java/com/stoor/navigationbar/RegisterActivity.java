package com.stoor.navigationbar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
import com.stoor.navigationbar.R;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

//    private static final String TAG = "RegisterActivity";
//    private static final String URL_FOR_REGISTRATION = "https://XXX.XXX.X.XX/android_login_example/register.php";
    ProgressDialog progressDialog;

    private EditText EditTextMavID, EditTextName, EditTextEmail, EditTextPassword, EditTextconfirmPassword, EditTextphoneNumber, EditTextgender;
    private Button btnSignUp;
    private Button btnLinkLogin;
    private RadioGroup genderRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user);
        // Progress dialog
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setCancelable(false);
        btnSignUp = (Button) findViewById(R.id.btn_signup);
        btnLinkLogin = (Button) findViewById(R.id.btn_link_login);

    }

    public void onClick(View view)
    {
        if(view == findViewById(R.id.btn_signup))
        {
            EditTextMavID = (EditText) findViewById(R.id.signup_input_mavid);
            EditTextName = (EditText) findViewById(R.id.signup_input_name);
            EditTextEmail = (EditText) findViewById(R.id.signup_input_email);
            EditTextPassword = (EditText) findViewById(R.id.signup_input_password);
            EditTextconfirmPassword = (EditText) findViewById(R.id.signup_input_confirmpassword);
            EditTextphoneNumber = (EditText) findViewById(R.id.signup_input_phonenumber);
            EditTextgender = (EditText) findViewById(R.id.signup_input_gender);

            String mavID, name, email, password, confirmPassword, phoneNumber, gender;

            mavID = EditTextMavID.getText().toString();
            name = EditTextName.getText().toString();
            email = EditTextEmail.getText().toString();
            password = EditTextPassword.getText().toString();
            confirmPassword = EditTextconfirmPassword.getText().toString();
            phoneNumber = EditTextphoneNumber.getText().toString();
            gender= EditTextgender.getText().toString();
            boolean flag=true;


            if(mavID.length()!=10 || !mavID.matches("^\\d{10}$"))
            {
                flag=false;
                Toast.makeText(this, "Invalid MAV ID", Toast.LENGTH_SHORT).show();
            }
            if(!email.contains("@mavs.uta.edu"))
            {
                flag=false;
                Toast.makeText(this, "Invalid Email ID", Toast.LENGTH_SHORT).show();
            }
            if(!password.equals(confirmPassword))
            {
                flag=false;
                Toast.makeText(this, "Passwords not same", Toast.LENGTH_SHORT).show();
            }
//            if(gender.equalsIgnoreCase("male") == false || gender.equalsIgnoreCase("female") == false)
//            {
//                flag = false;
//                Toast.makeText(this, "Please check the gender", Toast.LENGTH_SHORT).show();
//            }
            if(flag) {
                UserPOJO userpojo = new UserPOJO();
                userpojo.setMavID(mavID);
                userpojo.setName(name);
                userpojo.setEmail(email);
                userpojo.setPassword(password);
                userpojo.setPhoneNumber(phoneNumber);
                userpojo.setGender(gender);
                SQLiteHelper helper = new SQLiteHelper(this);
                helper.insertUserData(userpojo);
                Toast.makeText(RegisterActivity.this, "Registration successful.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }

        }
    }
}
