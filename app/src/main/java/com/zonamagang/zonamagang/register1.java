package com.zonamagang.zonamagang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class register1 extends AppCompatActivity {
    Button siswa,industri;
    String jenis_daftar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        siswa = (Button) findViewById(R.id.siswa);
        industri= (Button) findViewById(R.id.industri);
        siswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View x) {
                Intent daftarsiswa = new Intent(getApplicationContext(),register2.class);
                jenis_daftar = "siswa";
                daftarsiswa.putExtra("jenis_daftar",jenis_daftar);
                startActivity(daftarsiswa);
            }
        });
        industri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View x) {
                Intent daftarindustri = new Intent(getApplicationContext(),register2.class);
                jenis_daftar = "industri";
                daftarindustri.putExtra("jenis_daftar",jenis_daftar);
                startActivity(daftarindustri);
            }
        });
        TextView masuk = (TextView) findViewById(R.id.signin);
        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent x = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(x);
                finish();
            }
        });
    }
}
