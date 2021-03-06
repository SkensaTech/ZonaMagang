package com.zonamagang.zonamagang;


import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;

import com.squareup.picasso.Picasso;

public class home_siswa_1 extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewIndustriAdapter adapter;
    private Drawer result = null;
    String email, foto, nama, searchquery,bidang,kota,provinsi;
    boolean doubleBackToExit = false;
    MenuItem cari;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Backendless.initApp(this, Constants.APP_ID, Constants.APP_SECRET, Constants.APP_VERSION);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_siswa_1);

        email = getIntent().getStringExtra("email");
        nama = getIntent().getStringExtra("nama");
        foto = getIntent().getStringExtra("foto");

        if(getIntent().getStringExtra("bidang") != null){
            bidang = getIntent().getStringExtra("bidang");
            kota = getIntent().getStringExtra("kota");
            provinsi = getIntent().getStringExtra("provinsi");
        }
        if(getIntent().getStringExtra("searchquery") != null){
            if(!getIntent().getStringExtra("searchquery").equals("")){
                searchquery = getIntent().getStringExtra("searchquery");
            }
        }

        Toolbar x = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(x);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        adapter = new ViewIndustriAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        viewPager.setAdapter(adapter);


        Toolbar myToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this,R.color.colorPrimary));

        tabLayout.setTabTextColors(ContextCompat.getColorStateList(this, R.color.textColor));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.colorPrimaryLight));

        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }
        });

        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withIcon(getResources().getDrawable(R.drawable.ic_search_black_24dp)).withName("Cari Industri");
        SecondaryDrawerItem item2 = (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(2).withIcon(getResources().getDrawable(R.drawable.ic_dashboard_black_24dp)).withName("Beranda");
        SecondaryDrawerItem item3 = (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(3).withIcon(getResources().getDrawable(R.drawable.ic_notifications_black_24dp)).withName("Notifikasi");
        SecondaryDrawerItem item4 = (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(4).withIcon(getResources().getDrawable(R.drawable.ic_supervisor_account_black_24dp)).withName("Tentang Kami");
        SecondaryDrawerItem item5 = (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(5).withIcon(getResources().getDrawable(R.drawable.ic_account_box_black_24dp)).withName("Akun Saya");
        SecondaryDrawerItem item6 = (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(6).withName("Keluar");

        AccountHeader headerprofil = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.bg)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName(nama)
                                .withEmail(email)
                                .withIcon(foto)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        return false;
                    }
                })
                .build();

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        result = new DrawerBuilder()
                .withAccountHeader(headerprofil)
                .withActivity(this)
                .withToolbar(x)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2,
                        new DividerDrawerItem(),
                        item3,
                        new DividerDrawerItem(),
                        item4,
                        new DividerDrawerItem(),
                        item5,
                        new DividerDrawerItem(),
                        item6,
                        new DividerDrawerItem()
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem.getIdentifier() == 1){
//                            Toast.makeText(getApplicationContext(),"Fitur Pencarian masih dalam tahap pengembangan",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(home_siswa_1.this, search_filter.class);
                            intent.putExtra("email",email);
                            intent.putExtra("nama",nama);
                            intent.putExtra("foto",foto);
                            startActivity(intent);
                        }
                        else if (drawerItem.getIdentifier() == 3){
                            Intent itent = new Intent(home_siswa_1.this, notifikasi_siswa.class);
                            startActivity(itent);
//                            finish();
                        }
                        else if(drawerItem.getIdentifier() == 4){
                            Intent itent = new Intent(home_siswa_1.this,TentangKami.class);
                            startActivity(itent);
                        }
                        else if (drawerItem.getIdentifier() == 5){
                            Intent intent = new Intent(home_siswa_1.this,DetailSiswa.class);
                            startActivity(intent);
                        }
                        else if(drawerItem.getIdentifier() == 6){
                            setContentView(R.layout.loading_screen);
                            Backendless.UserService.logout(new AsyncCallback<Void>()
                            {
                                public void handleResponse( Void response )
                                {
                                    // user has been logged out..
                                    Intent MainActivityIntent = new Intent(home_siswa_1.this,MainActivity.class);
                                    startActivity(MainActivityIntent);
                                }

                                public void handleFault( BackendlessFault fault )
                                {
                                    // something went wrong and logout failed, to get the error code call fault.getCode()
                                    Toast.makeText(home_siswa_1.this,"Logout failed",Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                        return false;
                    }
                })
                .build();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);

    }

    public MenuItem getCariMenu(){
        return cari;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuin = getMenuInflater();
        menuin.inflate(R.menu.menu_search, menu);
        cari = menu.findItem(R.id.searchbox);
        cari.setEnabled(false);


        final SearchView pencarian = (SearchView) MenuItemCompat.getActionView(cari);
        pencarian.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e("home_siswa_1","search clicked");

                bidang = null;

                searchquery = query;
                adapter = new ViewIndustriAdapter(getSupportFragmentManager());
                viewPager = (ViewPager) findViewById(R.id.viewpager);

                viewPager.setAdapter(adapter);
                return true;
//                Intent intent = getIntent();
//                intent.putExtra("searchquery",query);
//                intent.putExtra("email",email);
//                intent.putExtra("nama",nama);
//                intent.putExtra("foto",foto);
//                finish();
//                startActivity(intent);
//                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

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

    public String getQuery() {
        return searchquery;
    }

    public String getProvinsiSearch(){
        return provinsi;
    }

    public String getKotaSearch(){
        return kota;
    }

    public String getBidangSearch(){
        return bidang;
    }

}
