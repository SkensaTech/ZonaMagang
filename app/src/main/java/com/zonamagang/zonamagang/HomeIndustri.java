package com.zonamagang.zonamagang;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.UserService;
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
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;
import com.zonamagang.zonamagang.Model.tb_industri;
import com.zonamagang.zonamagang.Model.tb_parent_bidang;

import java.util.Iterator;
import java.util.List;
import java.util.zip.Inflater;

public class HomeIndustri extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPageAdapter adapter;
    private Drawer result = null;

    BackendlessUser userInfo;

    String nama,email,logo;

    int id_user;

    public static final String TAG = "Home";

    Toolbar myToolbar;

    View homeIndustri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Backendless.initApp(this,Constants.APP_ID,Constants.APP_SECRET,Constants.APP_VERSION);
        setContentView(R.layout.activity_home_industri);

//        userInfo = Backendless.UserService.CurrentUser();
//        id_user = Integer.parseInt(userInfo.getProperty("id_user").toString());
//        email = userInfo.getProperty("email").toString();

//        homeIndustri = getLayoutInflater().inflate(R.layout.activity_home_industri, new DrawerLayout(this));

        email = getIntent().getStringExtra("email");
        nama = getIntent().getStringExtra("nama");
        logo = getIntent().getStringExtra("logo");
        String a = getIntent().getStringExtra("id_user");
//        id_user = Integer.parseInt(a);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        myToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        adapter = new ViewPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(HomeIndustri.this,R.color.colorPrimary));

        tabLayout.setTabTextColors(ContextCompat.getColorStateList(HomeIndustri.this, R.color.textColor));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(HomeIndustri.this, R.color.colorPrimaryLight));

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

       this.drawerInit();

    }

    public void drawerInit(){

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

        final PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withIcon(getResources().getDrawable(R.drawable.ic_dashboard_black_24dp)).withName("Beranda");
        final PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withIcon(getResources().getDrawable(R.drawable.ic_notifications_black_24dp)).withName("Notifikasi");
        final PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withIcon(getResources().getDrawable(R.drawable.ic_account_box_black_24dp)).withName("Akun Saya");
        final PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(4).withIcon(getResources().getDrawable(R.drawable.ic_supervisor_account_black_24dp)).withName("Tentang Kami");
        final PrimaryDrawerItem item5 = new PrimaryDrawerItem().withIdentifier(5).withName("Keluar");

        AccountHeader headerProfile = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.bg)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName(nama)
                                .withEmail(email)
                                .withIcon(logo)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        return false;
                    }
                })
                .build();

        result = new DrawerBuilder()
                .withAccountHeader(headerProfile)
                .withActivity(this)
                .withToolbar(myToolbar)
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
                        new DividerDrawerItem()
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (position == 1){
                            Intent intent = new Intent(HomeIndustri.this,HomeIndustri.class);
                            startActivity(intent);
                        }
                        else if (drawerItem.getIdentifier() == 2){
                            Intent itent = new Intent(HomeIndustri.this,Notifikasi_Industri.class);
                            startActivity(itent);
                        }
                        else if (drawerItem.getIdentifier() == 3){
                            Intent intent = new Intent(HomeIndustri.this,AkunIndustri.class);
                            intent.putExtra("id_user",id_user);
                            startActivity(intent);
                        }
                        else if (drawerItem.getIdentifier() == 4){
                            Intent intent = new Intent(HomeIndustri.this,TentangKami.class);
                            startActivity(intent);
                        }
                        else if(drawerItem.getIdentifier() == 5){
                            setContentView(R.layout.loading_screen);

                            Backendless.UserService.logout(new AsyncCallback<Void>()
                            {
                                public void handleResponse( Void response )
                                {
                                    // user has been logged out.
                                    Intent MainActivityIntent = new Intent(HomeIndustri.this,MainActivity.class);
                                    startActivity(MainActivityIntent);
                                }

                                public void handleFault( BackendlessFault fault )
                                {
                                    // something went wrong and logout failed, to get the error code call fault.getCode()
                                    Toast.makeText(HomeIndustri.this,"Logout failed",Toast.LENGTH_LONG).show();
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

}
