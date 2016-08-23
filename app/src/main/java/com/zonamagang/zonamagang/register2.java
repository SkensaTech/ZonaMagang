package com.zonamagang.zonamagang;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;

public class register2 extends AppCompatActivity {
    TextView mEmail;
    TextView mPass;
    TextView mRePass;
    Button mSubmit;
    private EditText emailEditText;
    private EditText passEditText;
    private EditText repassEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        Toolbar x = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(x);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.layoutItems();

        emailEditText = (EditText) findViewById(R.id.daftaremail);
        passEditText = (EditText) findViewById(R.id.daftarpwd);
        repassEditText = (EditText) findViewById(R.id.daftarrepwd);

        findViewById(R.id.tomboldaftar).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                final String email = emailEditText.getText().toString();
                if (!isValidEmail(email)) {
                    emailEditText.setError("Invalid Email");
                }

                final String pass = passEditText.getText().toString();
                if (!isValidPassword(pass)) {
                    passEditText.setError("Invalid Password");
                }

                final String repass = repassEditText.getText().toString();
                if (!isValidPassword(repass)){
                    repassEditText.setError("Invalid Password");
                }


            }
        });


//        check.setOnClickListener(new View.OnClickListener() {
//                                     @Override
//                                     public void onClick(View arg0) {
//
//
//                                     }
//                                 });

    }



    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password with retype password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() >= 6) {
            return true;
        }
        return false;
    }


    public void nextStep(View view){

        EditText formnama = (EditText) findViewById(R.id.daftaremail);
        EditText formpwd = (EditText) findViewById(R.id.daftarpwd);
        EditText repwd = (EditText) findViewById(R.id.daftarrepwd);
        String kosong = null;

    }


//    public void sendNext() {
//        Intent step1= new Intent(this, step_1_industri.class);
//        step1.putExtra("email", mEmail.getText().toString());
//        step1.putExtra("Pass", mPass.getText().toString());
//        step1.putExtra("repass", mRePass.getText().toString());
//
//        //before
//        step1.putExtra("email", mEmail.getText().toString());
//        step1.putExtra("pass", mPass.getText().toString());
//        startActivity(step1);
//    }

    private void layoutItems (){
        mEmail = (TextView)findViewById(R.id.daftaremail);
        mPass = (TextView)findViewById(R.id.daftarpwd);
        mRePass = (TextView)findViewById(R.id.daftarrepwd);

        mSubmit = (Button)findViewById(R.id.tomboldaftar);
    }
}