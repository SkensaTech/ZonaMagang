package com.zonamagang.zonamagang;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.zonamagang.zonamagang.Model.tb_industri;
import com.zonamagang.zonamagang.Model.tb_siswa;

import java.util.Iterator;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    TextView x;
    EditText mLoginEmail;
    EditText mLoginPass;
    String nama, logo, email, id_user_string;
    String foto;
    int id_siswa;
    Context context = this;
    boolean doubleBackToExit = false;
    public static int id_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Backendless.initApp(this, Constants.APP_ID, Constants.APP_SECRET, Constants.APP_VERSION);
        setContentView(R.layout.activity_main);
        this.layoutItems();
        x = (TextView) findViewById(R.id.buatakun);

        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent register = new Intent(getApplicationContext(), register1.class);
                startActivity(register);
            }
        }  );
    }

    public void doLogin(View view) {
        setContentView(R.layout.loading_screen);
        mLoginEmail.setEnabled(false);
        mLoginPass.setEnabled(false);
        email = mLoginEmail.getText().toString();
        String pass = mLoginPass.getText().toString();

        Backendless.UserService.login(email, pass, new AsyncCallback<BackendlessUser>() {
            public void handleResponse(BackendlessUser user) {
                // user has been logged in
                String user_level = user.getProperty("user_level").toString();
                id_user_string = user.getProperty("id_user").toString();
                if (user_level.equals("2")) {

                    String whereClause = "id_user = " + id_user_string;
                    BackendlessDataQuery dataQuery = new BackendlessDataQuery();
                    dataQuery.setWhereClause(whereClause);
                    Backendless.Persistence.of(tb_industri.class).find(dataQuery,
                            new AsyncCallback<BackendlessCollection<tb_industri>>() {
                                @Override
                                public void handleResponse(BackendlessCollection<tb_industri> foundContacts) {
                                    List<tb_industri> firstPage = foundContacts.getCurrentPage();

                                    Iterator<tb_industri> iterator = firstPage.iterator();

                                    while (iterator.hasNext()) {
                                        tb_industri industriInfo = iterator.next();
                                        nama = industriInfo.getNama();
                                        logo = industriInfo.getLogo();
                                        id_login = industriInfo.getId_industri();
                                        Intent homeIndustri = new Intent(MainActivity.this, HomeIndustri.class);
                                        homeIndustri.putExtra("email", email);
                                        homeIndustri.putExtra("nama", nama);
                                        homeIndustri.putExtra("logo", logo);
//                                        homeIndustri.putExtra("id_user",industri_id);
//                                        Toast.makeText(MainActivity.this, ""+industri_id, Toast.LENGTH_SHORT).show();
                                        startActivity(homeIndustri);

                                    }
                                }

                                @Override
                                public void handleFault(BackendlessFault fault) {
                                    // an error has occurred, the error code can be retrieved with fault.getCode()
//                                    Toast.makeText(MainActivity.this, "Failed to get industri info, " + fault.getMessage(), Toast.LENGTH_LONG).show();
                                    new AlertDialog.Builder(context)
                                            .setTitle("Alert")
                                            .setMessage("Failed to get industri info, "+fault.getMessage())
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            })
                                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            })
                                            .show();
                                }
                            });

                } else if (user_level.equals("3")) {

                    String whereClause = "id_user = " + id_user_string;
                    BackendlessDataQuery dataQuery = new BackendlessDataQuery();
                    dataQuery.setWhereClause(whereClause);
                    Backendless.Persistence.of(tb_siswa.class).find(dataQuery,
                            new AsyncCallback<BackendlessCollection<tb_siswa>>() {
                                @Override
                                public void handleResponse(BackendlessCollection<tb_siswa> foundContacts) {
                                    List<tb_siswa> firstPage = foundContacts.getCurrentPage();

                                    Iterator<tb_siswa> iterator = firstPage.iterator();

                                    while (iterator.hasNext()) {
                                        tb_siswa siswaInfo = iterator.next();
                                        nama = siswaInfo.getNama();
                                        foto = siswaInfo.getFoto();
                                        id_siswa = siswaInfo.getId_siswa();
                                        String id_siswa_string = Integer.toString(id_siswa);

                                        Intent homeSiswa = new Intent(MainActivity.this, home_siswa_1.class);
                                        homeSiswa.putExtra("email", email);
                                        homeSiswa.putExtra("nama", nama);
                                        homeSiswa.putExtra("foto", foto);
                                        homeSiswa.putExtra("id_siswa",id_siswa_string);
                                        startActivity(homeSiswa);
                                        finish();
                                    }
                                }

                                @Override
                                public void handleFault(BackendlessFault fault) {
                                    // an error has occurred, the error code can be retrieved with fault.getCode()
//                                    Toast.makeText(MainActivity.this, "Failed to get industri info, " + fault.getMessage(), Toast.LENGTH_LONG).show();
                                    new AlertDialog.Builder(context)
                                            .setTitle("Alert")
                                            .setMessage("Failed to get Siswa info, "+fault.getMessage())
                                            .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    finish();
                                                }
                                            })
                                            .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            })
                                            .show();
                                }
                            });
                }
            }

            public void handleFault(BackendlessFault fault) {
                // login failed, to get the error code call fault.getCode()
//                Toast.makeText(MainActivity.this, "Login Failed. " + fault.getMessage(), Toast.LENGTH_LONG).show();
                new AlertDialog.Builder(context)
                        .setTitle("Alert")
                        .setMessage("Failed to get industri info, "+fault.getMessage())
                        .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        },true);
        AsyncCallback<Boolean> isValidLoginCallback = new AsyncCallback<Boolean>()
        {
            @Override
            public void handleResponse( Boolean response )
            {
                //Valid Respon
            }

            @Override
            public void handleFault( BackendlessFault fault )
            {
                System.err.println( "Error - " + fault );
            }

        };

        Backendless.UserService.isValidLogin( isValidLoginCallback );
    }

    private void layoutItems() {
        mLoginEmail = (EditText) findViewById(R.id.login_email);
        mLoginPass = (EditText) findViewById(R.id.login_password);
    }

    @Override
    public void onBackPressed(){
        if (doubleBackToExit){
            super.onBackPressed();
            return;
        }
        this.doubleBackToExit = true;
        Toast.makeText(this, "Tap again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExit = false;
            }
        }, 3000);
    }
}
