package com.zonamagang.zonamagang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.squareup.picasso.Picasso;
import com.zonamagang.zonamagang.Model.tb_industri;

import org.w3c.dom.Text;

import java.util.List;

public class AkunIndustri extends AppCompatActivity {

    private Drawer result = null;
    TextView nama_industri;
    TextView jalan_industri;
    TextView email_industri;
    TextView noTelp_industri;
    TextView profil_industri;
    TextView deskripsi_industri;
    TextView kemampuan_industri;
    ImageView img;

    String namaIndustri,jalanIndustri,emailIndustri,
            noTelpIndustri,profilIndustri,
            deskripsiIndustri,kemampuanIndustri;

    BackendlessUser userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun_industri);
        String x = getIntent().getStringExtra("id_user");
        int id_industri = MainActivity.id_login;
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String whereClause = "id_industri = "+id_industri;
        BackendlessDataQuery query = new BackendlessDataQuery();
        query.setWhereClause(whereClause);
        Backendless.Persistence.of(tb_industri.class).find(query, new AsyncCallback<BackendlessCollection<tb_industri>>() {
            @Override
            public void handleResponse(BackendlessCollection<tb_industri> dataIndustri) {
                List<tb_industri> curpage = dataIndustri.getCurrentPage();
                nama_industri.setText(curpage.get(0).getNama());
                jalan_industri.setText(curpage.get(0).getAlamat());
                email_industri.setText(curpage.get(0).getEmail());
                noTelp_industri.setText(curpage.get(0).getNo_telp());
                profil_industri.setText(curpage.get(0).getProfil());
                deskripsi_industri.setText(curpage.get(0).getJobdesc());
                kemampuan_industri.setText(curpage.get(0).getKualifikasi());
                String xxx = curpage.get(0).getLogo();
                Picasso.with(AkunIndustri.this).load(xxx).into(img);
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
        this.layoutitem();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.edit_icon:
                Intent intent = new Intent(AkunIndustri.this, UpdateAkun.class);
                startActivity(intent);
        }
        return super.onContextItemSelected(item);
    }

    public void layoutitem(){
        nama_industri = (TextView) findViewById(R.id.nama);
        jalan_industri = (TextView) findViewById(R.id.alamat);
        email_industri = (TextView) findViewById(R.id.email_industri) ;
        noTelp_industri = (TextView) findViewById(R.id.noTelp_industri);
        profil_industri = (TextView) findViewById(R.id.profile_industri);
        deskripsi_industri = (TextView) findViewById(R.id.deskripsi_industri);
        kemampuan_industri = (TextView) findViewById(R.id.kemampuan_industri);
        img = (ImageView) findViewById(R.id.logo);
    }
}
