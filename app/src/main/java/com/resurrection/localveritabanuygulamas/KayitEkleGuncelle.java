package com.resurrection.localveritabanuygulamas;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageActivity;
import com.theartofdev.edmodo.cropper.CropImageView;

public class KayitEkleGuncelle extends AppCompatActivity {
    private CircularImageView profileResmi;
    private EditText editAd, editTelefon, editMail, editDogumtarihi, editAciklama;
    private FloatingActionButton btnKayit;
    ActionBar actionBar;
    // izin sabitleri
    private static final int KAMERA_TALEP_KODU = 100;
    private static final int Depolama_TALEP_KODU = 101;
    // resi seçin sabitleri
    private static final int KAMERADAN_RESİM_SECME_KODU = 102;
    private static final int GALERİDEN_RESİM_SECME_KODU = 103;
    //iziler dizi
    private String[] kameraIzınleri; // kamera ve depolama izin
    private String[] depolamaIzınleri; // depolama izin
    // resim tutucu
    private Uri resimUri;
    private String id,ad,telefon,email,dogumtarihi,aciklama,eklenmetarihi;

    // veritabanı helper

    VtHelper vtHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ekle_guncelle);


        actionBar = getSupportActionBar();
        actionBar.setTitle("Kayıt ekle");
        actionBar.setDisplayShowHomeEnabled(true); // geri oku
        actionBar.setDisplayHomeAsUpEnabled(true);// geri oku

        profileResmi = findViewById(R.id.iv_Profil_Resmi);
        editAd = findViewById(R.id.edit_Ad);
        editTelefon = findViewById(R.id.edit_Telefon);
        editMail = findViewById(R.id.edit_mail);
        editDogumtarihi = findViewById(R.id.edit_Dogumtarihi);
        editAciklama = findViewById(R.id.edit_Aciklama);
        btnKayit = findViewById(R.id.btn_kayit);

        // veritabanı tanımlası
        vtHelper = new VtHelper(this);

        // izin dizileri tanımlama
        kameraIzınleri = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        depolamaIzınleri = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};


        //--------------------------------------------------------
        //-----------------------------------------------------


        //btn kayita tıklama
        btnKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ver tabanına kayıt gönder
                veriEkle();


            }
        });

        //profile resmi tıklama
        profileResmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resimSecmeDialog();
            }
        });


    }

    private void veriEkle() {
        ad = editAd.getText().toString().trim();
        telefon = editTelefon.getText().toString().trim();
        email = editMail.getText().toString().trim();
        aciklama = editAciklama.getText().toString().trim();
        dogumtarihi = editDogumtarihi.getText().toString().trim();

        String anlıkZaman = ""+System.currentTimeMillis();

        long id =  vtHelper.kayitEkle(
                ""+ad,
                ""+resimUri,
                ""+aciklama,
                ""+telefon,
                ""+email,
                ""+dogumtarihi,
                ""+anlıkZaman,
                ""+anlıkZaman);
        Toast.makeText(this, id+" kaydınız eklenmiştir", Toast.LENGTH_SHORT).show();

    }

    private void resimSecmeDialog() {
        String[] ogeler = {"Kamera", "Galeri"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Resim sec");
        builder.setItems(ogeler, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    //Kamera tıklandığında
                    if (!kameraIznıKontrolu()) {
                        //kamera
                        // kameraya erişim izni yoksa izni talep et
                        kameraIzınTalebi();
                    } else {
                        //kameradam resim sec
                        kameradanSec();
                    }
                }
                if (which == 1) {
                    //Galeri
                    //galeriye tıklandığında
                    if (!depolamaIznıKontrolu()) {
                        //kamera
                        // kameraya erişim izni yoksa izni talep et
                        depolamaIzınTalebi();
                    } else {
                        //galeriden resim sec
                        galeridenSec();

                    }
                }
            }
        });
        builder.create().show();
    }

    // kamerayı açtırma
    private void kameradanSec() {
        // kameradan secilen resim onActivty result metotu ile alınacak
        ContentValues degerler = new ContentValues();
        degerler.put(MediaStore.Images.Media.TITLE, "Resim Başlığı");
        degerler.put(MediaStore.Images.Media.DESCRIPTION, "Resim Açıklaması");
        resimUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, degerler);

        Intent kameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        kameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, resimUri);
        startActivityForResult(kameraIntent, KAMERADAN_RESİM_SECME_KODU);

    }

    // galeriden Açtırma
    private void galeridenSec() {
        // galeriden secilen resim onActivty result metotu ile alınacak
        Intent galeriIntent = new Intent(Intent.ACTION_PICK);
        galeriIntent.setType("image/*"); // burada resim ve (/*)resim türlerindekiler anlamına geliyor
        startActivityForResult(galeriIntent, GALERİDEN_RESİM_SECME_KODU);
    }

    //ActionBardaki geriye tıklandığında geriye gitmesi
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();// geri tuşuna basıldığında ne yapıyorsa onu yap
        return super.onSupportNavigateUp();
    }

    // depolam izni kontrolü
    private boolean depolamaIznıKontrolu() {
        boolean sonuc = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return sonuc;
    }

    // depolamaIznıTalebi
    private void depolamaIzınTalebi() {
        ActivityCompat.requestPermissions(this, depolamaIzınleri, Depolama_TALEP_KODU);
    }

    // depolam izni kontrolü
    private boolean kameraIznıKontrolu() {
        boolean sonuc1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        boolean sonuc2 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        return sonuc1 && sonuc2;
    }

    // kameraIznıTalebi
    private void kameraIzınTalebi() {
        ActivityCompat.requestPermissions(this, kameraIzınleri, KAMERA_TALEP_KODU);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull  int[] grantResults) {
        // izin durumuna göre yapılması gerekenler (izin verilme veya reddeilme durumu)
        switch (requestCode){
            case KAMERA_TALEP_KODU:
            {
                if (grantResults.length>0){
                    // izin alınmışsa true döndür aksi halde false
                    boolean kameraKabul = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean depolamaKabul = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (kameraKabul && depolamaKabul){
                        kameradanSec();
                    }else {
                        Toast.makeText(this, "kamera ve depolama izni gerekli", Toast.LENGTH_SHORT).show();
                    }

                }
            }
            break;

            case Depolama_TALEP_KODU:
            {
                if (grantResults.length>0){
                    // izin alınmışsa true döndür aksi halde false
                    boolean depolamaKabul = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (depolamaKabul){
                        galeridenSec();
                    }else {
                        Toast.makeText(this, "depolama izni gerekli", Toast.LENGTH_SHORT).show();
                    }

                }
            }
            break;
        }
        
        
        
        
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //KAMERADAN YADA ALINACAK RESİM BURADA İŞLENİP alınacak
        if (resultCode == RESULT_OK) {
            // resim seçildi
            if (requestCode == GALERİDEN_RESİM_SECME_KODU) {
                // galeriden secilen resim
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this);
            } else if (requestCode == KAMERADAN_RESİM_SECME_KODU) {
                // kameradan secilen resim
                CropImage.activity(resimUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this);
            }
            // resmi kırpıp profil resmine aktarma
            else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                // resim kırpıldı
                // kırpılan resmi al
                // kırpılma başarılı ise
                CropImage.ActivityResult sonuc = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    resimUri = sonuc.getUri();
                    //Resmş circle image viewe aktar
                    profileResmi.setImageURI(resimUri);
                }// kırpılma başarısızsa
                else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    Exception hata = sonuc.getError();
                    Toast.makeText(this, "hata hata", Toast.LENGTH_SHORT).show();
                }

            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}









