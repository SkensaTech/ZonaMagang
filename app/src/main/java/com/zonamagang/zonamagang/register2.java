package com.zonamagang.zonamagang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    String xx,y;

    String passw;
    public String tmp1="",tmp2="",tmp3="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        //coding toolbar
        Toolbar x = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(x);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.layoutItems();

        xx = getIntent().getStringExtra("jenis_daftar");
        emailEditText = (EditText) findViewById(R.id.daftaremail);
        passEditText = (EditText) findViewById(R.id.daftarpwd);
        repassEditText = (EditText) findViewById(R.id.daftarrepwd);

        findViewById(R.id.tomboldaftar).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                final String email = emailEditText.getText().toString();
                if (!isValidEmail(email)) {
                    emailEditText.setError("Invalid Email Format");

                }

                passw = passEditText.getText().toString();
                if (!isValidPassword(passw)) {
                    passEditText.setError("Minimal 6 Character");
                }

                final String repass = repassEditText.getText().toString();
                if (!isValidRePassword(repass)) {
                    repassEditText.setError("Password Not Match");

                }
                String xxx = tmp1+tmp2+tmp3;
                if (xxx.equals("123")){

                    register2.this.sendNext();
                }
                //register2.this.sendNext();
            }
        });
    }


    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        tmp1 = "1";
        return matcher.matches();
    }

    // validating password with retype password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() >= 6) {
            tmp2 = "2";
            return true;



        } else {
            tmp2 = "";

            return false;
        }
    }

    private boolean isValidRePassword(String pass) {
        if (pass.equals(passw)) {
            tmp3 = "3";
            return true;

        } else {
            tmp3 = "";

            return false;
        }
    }


    public void nextStep(View view){

        EditText formnama = (EditText) findViewById(R.id.daftaremail);
        EditText formpwd = (EditText) findViewById(R.id.daftarpwd);
        EditText repwd = (EditText) findViewById(R.id.daftarrepwd);
        String kosong = null;
        this.sendNext();

    }

   public void sendNext() {
        Intent step1;
       if (xx.equals("siswa")){
           step1= new Intent(this, step_1_siswa_daftar.class);
       } else {
           step1= new Intent(this, step_1_industri.class);

       }
        step1.putExtra("email", mEmail.getText().toString());
        step1.putExtra("Pass", mPass.getText().toString());
        step1.putExtra("repass", mRePass.getText().toString());

        //before
        step1.putExtra("email", mEmail.getText().toString());
        step1.putExtra("pass", mPass.getText().toString());
        startActivity(step1);
    }

    private void layoutItems (){
        mEmail = (TextView)findViewById(R.id.daftaremail);
        mPass = (TextView)findViewById(R.id.daftarpwd);
        mRePass = (TextView)findViewById(R.id.daftarrepwd);

        mSubmit = (Button)findViewById(R.id.tomboldaftar);
    }
}