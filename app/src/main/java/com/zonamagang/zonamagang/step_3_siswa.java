package com.zonamagang.zonamagang;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.files.BackendlessFile;
import com.zonamagang.zonamagang.Model.last_id;
import com.zonamagang.zonamagang.Model.tb_error;
import com.zonamagang.zonamagang.Model.tb_industri;
import com.zonamagang.zonamagang.Model.tb_siswa;

public class step_3_siswa extends AppCompatActivity {

    ImageView mLogo;
    String nisn="0",nama="0",alamat="0",tempat="0",tgl="0",jeniskelamin="0",provinsi="0",kota="0",sekolah="0",bidang="0",email="0",pass="0",foto="0";
    String telp="0";
    Bitmap bMap_image;
    public static String user_id;

    EditText notelp;
    tb_siswa saveSiswa;
    int id_user,id_siswa,id_bidang,id_sekolah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_3_siswa);
        Backendless.initApp( this, Constants.APP_ID, Constants.APP_SECRET, Constants.APP_VERSION );

        this.layoutItems();
        //passing from layout step 2
        nisn = getIntent().getStringExtra("nisn");
        nama = getIntent().getStringExtra("nama");
        alamat = getIntent().getStringExtra("alamat");
        tempat = getIntent().getStringExtra("tempat");
        tgl =  getIntent().getStringExtra("tanggal");
        jeniskelamin = getIntent().getStringExtra("jeniskelamin");
        provinsi = getIntent().getStringExtra("provinsi");
        kota = getIntent().getStringExtra("kota");
        id_sekolah = getIntent().getIntExtra("id_sekolah",0);
        id_bidang = getIntent().getIntExtra("id_bidang",0);
        email = getIntent().getStringExtra("email");
        pass = getIntent().getStringExtra("pass");
        telp = notelp.getText().toString();
//        Toast.makeText(getApplicationContext(),"Sekolah : "+id_sekolah+"Id Bidang : "+id_bidang,Toast.LENGTH_LONG).show();

//        id_sekolah = Integer.parseInt(sekolah);
//        id_bidang = Integer.parseInt(bidang);
        Toolbar x = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(x);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    //coding upload foto
    public void showFileChooser(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
            {
                if (resultCode == RESULT_OK)
                {
                    Uri photoUri = data.getData();
                    if (photoUri != null)
                    {
                        try {
                            String[] filePathColumn = {MediaStore.Images.Media.DATA};
                            Cursor cursor = getContentResolver().query(photoUri, filePathColumn, null, null, null);
                            cursor.moveToFirst();
                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            String filePath = cursor.getString(columnIndex);
                            cursor.close();
                            bMap_image = BitmapFactory.decodeFile(filePath);
                            mLogo.setImageBitmap(bMap_image);


                        }catch(Exception e)
                        {}
                    }
                }
            }
        }
    }
    //end coding upload foto

    private void layoutItems() {
        mLogo = (ImageView) findViewById(R.id.step_3_siswa_logo);
        notelp = (EditText) findViewById(R.id.notelp);

    }
    public void addUserInfo(){
        BackendlessUser user = new BackendlessUser();
        user.setProperty( "email", email);
        user.setProperty("status_aktif",1);
        user.setProperty("user_level",3);
        user.setProperty("id_user",id_user);
        user.setPassword( pass );

        Backendless.UserService.register( user, new AsyncCallback<BackendlessUser>()
        {
            public void handleResponse( BackendlessUser registeredUser )
            {
                //registered tapi belum login
                user_id = registeredUser.getObjectId();
                step_3_siswa.this.addSiswaInfo();
            }

            public void handleFault( BackendlessFault fault )
            {
//                Toast.makeText(step_3_siswa.this,"There's an Error ! Code = "+fault.getCode(),Toast.LENGTH_SHORT).show();
                String error = fault.getCode();
                if (error.equals("error")){
                    Intent intent = new Intent(getApplicationContext(),register1.class);
                    startActivity(intent);
                    step_3_siswa.this.finish();
                    Toast.makeText(step_3_siswa.this,"Maaf, Email yang anda gunakan telah terdaftar",Toast.LENGTH_SHORT).show();

                }
                // ada error. bisa di retrieved with fault.getCode()

            }
        } );
    }
    public void addSiswaInfo(){
        Backendless.Persistence.of( tb_siswa.class).findLast(new AsyncCallback<tb_siswa>(){
            @Override
            public void handleResponse( tb_siswa siswaInfo )
            {
                id_siswa = siswaInfo.getId_siswa() + 1;
                step_3_siswa.this.saveSiswaInfo();
//                Toast.makeText(getApplicationContext(),"Id Siswa",Toast.LENGTH_LONG).show();

            }
            @Override
            public void handleFault( BackendlessFault fault )
            {
                // an error has occurred, the error code can be retrieved with fault.getCode()
                Toast a =Toast.makeText(getApplicationContext(),"Belum ada id siswa",Toast.LENGTH_LONG);
                a.show();
                id_siswa = 1;
            }
        });
    }
    public void debug(){
        Toast.makeText(getApplicationContext(),id_sekolah+"dan"+id_bidang,Toast.LENGTH_LONG).show();
    }
    public void saveSiswaInfo(){

        saveSiswa = new tb_siswa();
        saveSiswa.setId_siswa(id_siswa);
        saveSiswa.setId_user(id_user);
        saveSiswa.setId_bidang(id_bidang);
        saveSiswa.setId_sekolah(id_sekolah);
        saveSiswa.setNama(nama);
        saveSiswa.setAlamat(alamat);
        saveSiswa.setJenis_kelamin(jeniskelamin);
        saveSiswa.setNo_telp(telp);
        saveSiswa.setNisn(nisn);
        saveSiswa.setTempat_lahir(tempat);
        saveSiswa.setTgl_lahir(tgl);
        try {
        Backendless.Files.Android.upload( bMap_image,
                Bitmap.CompressFormat.PNG,
                100,
                id_user+"_siswa_logo.png",
                "mypics",
                new AsyncCallback<BackendlessFile>()
                {
                    @Override
                    public void handleResponse( final BackendlessFile backendlessFile )
                    {
                        foto = backendlessFile.getFileURL();
                        step_3_siswa.this.saveSiswa.setFoto(foto);

                        // save object asynchronously
                      Backendless.Persistence.save( saveSiswa, new AsyncCallback<tb_siswa>() {
                            public void handleResponse( tb_siswa response )
                            {
                                Toast.makeText(step_3_siswa.this,"Simpan Data",Toast.LENGTH_LONG).show();
                                step_3_siswa.this.registerSuccess();
                            }

                            public void handleFault( BackendlessFault fault )
                            {
                                Toast.makeText(step_3_siswa.this,"Data Siswa Info = "+fault.getMessage(),Toast.LENGTH_LONG).show();
                                String error1 = "Error Add Siswa "+fault.getCode();
                                step_3_siswa.this.send_error(error1);
                            }
                        });
                    }

                    @Override
                    public void handleFault( BackendlessFault backendlessFault )
                    {
                        Toast.makeText( step_3_siswa.this, "Get Error Code : "+backendlessFault.toString(), Toast.LENGTH_LONG ).show();
                    }
                });
        }catch(Exception e)
        {}
    }
    public void onSubmit(View view){
        setContentView(R.layout.loading_screen);
        Backendless.Persistence.of( last_id.class).findLast(new AsyncCallback<last_id>(){
            @Override
            public void handleResponse( last_id siswaInfo )
            {
                id_user = siswaInfo.getId_user() + 1;
                step_3_siswa.this.addUserInfo();
            }
            @Override
            public void handleFault( BackendlessFault fault )
            {
                Toast.makeText(step_3_siswa.this,"USER ID = 1"+fault.getMessage(),Toast.LENGTH_LONG).show();
                id_user = 1;
                step_3_siswa.this.addUserInfo();
            }
        });
    }
    public void registerSuccess(){

        last_id last_id_user = new last_id();
        last_id_user.setId_user(id_user);
        // save object asynchronously
        Backendless.Persistence.save( last_id_user, new AsyncCallback<last_id>() {
            public void handleResponse( last_id response )
            {
                // new Contact instance has been saved
                Intent loginIntent = new Intent(step_3_siswa.this,MainActivity.class);
                Toast.makeText(getApplicationContext(),"Silahkan Cek Email Untuk Verifikasi",Toast.LENGTH_LONG).show();
                startActivity(loginIntent);
                finish();
            }

            public void handleFault( BackendlessFault fault )
            {
                // an error has occurred, the error code can be retrieved with fault.getCode()
                Toast.makeText(getApplicationContext(),"Gagal, Last Id tidak ada",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onBackPressed(){
        Intent intent = new Intent(step_3_siswa.this,register1.class);
        startActivity(intent);
        this.finish();
    }
    public void send_error(String error){
        tb_error tabel_error = new tb_error();

        tabel_error.setUser_id(user_id);
        tabel_error.setError(error);

        Backendless.Persistence.save(tabel_error, new AsyncCallback<tb_error>() {
            @Override
            public void handleResponse(tb_error response) {

            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }
}