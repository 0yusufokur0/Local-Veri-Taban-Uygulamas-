package com.resurrection.localveritabanuygulamas;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class KayitEkleGuncelle extends AppCompatActivity {
    private CircularImageView profileResmi;
    private EditText editAd,editTelefon,editMail,editDogumtarihi,editAciklama;
    private FloatingActionButton btnKayit;
    ActionBar actionBar;
    // izin sabitleri
    private static final int  KAMERA_TALEP_KODU =100;
    private static final int  Depolama_TALEP_KODU =101;
    // resi seçin sabitleri
    private static final int  KAMERADAN_RESİM_SECME_KODU = 102;
    private static final int  GALERİDEN_RESİM_SECME_KODU = 103;
    //iziler dizi
    private String[] kameraIzınleri; // kamera ve depolama izin
    private String[] depolamaIzınleri; // depolama izin
    // resim tutucu
    private Uri resimUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ekle_guncelle);
        actionBar  = getSupportActionBar();
        actionBar.setTitle("Kayıt ekle");
        actionBar.setDisplayShowHomeEnabled(true); // geri oku
        actionBar.setDisplayHomeAsUpEnabled(true);// geri oku

        profileResmi = findViewById(R.id.iv_Profil_Resmi);
        editTelefon = findViewById(R.id.edit_Telefon);
        editMail = findViewById(R.id.edit_mail);
        editDogumtarihi = findViewById(R.id.edit_Dogumtarihi);
        editAciklama = findViewById(R.id.edit_Aciklama);
        btnKayit = findViewById(R.id.btn_kayit);

        // izin dizileri tanımlama
        kameraIzınleri = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        depolamaIzınleri = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};



        //btn kayita tıklama
        btnKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ver tabanına kayıt gönder
                Toast.makeText(KayitEkleGuncelle.this, "kayıt buradan yapılacak....", Toast.LENGTH_SHORT).show();
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

    private void resimSecmeDialog() {
        String[] ogeler = {"Kamera","Galeri"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Resim sec");
        builder.setItems(ogeler, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    //Kamera tıklandığında
                    if (!kameraIznıKontrolu()){
                        //kamera
                        // kameraya erişim izni yoksa izni talep et
                        kameraIzınTalebi();
                    }else {
                        //kameradam resim sec
                        kameradanSec();
                    }
                }
                if (which == 1){
                    //Galeri
                    //galeriye tıklandığında
                    if (!depolamaIznıKontrolu()){
                        //kamera
                        // kameraya erişim izni yoksa izni talep et
                        depolamaIzınTalebi();
                    }else {
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
        degerler.put(MediaStore.Images.Media.TITLE,"Resim Başlığı");
        degerler.put(MediaStore.Images.Media.DESCRIPTION,"Resim Açıklaması");
        resimUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,degerler);

        Intent kameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        kameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,resimUri);
        startActivityForResult(kameraIntent,KAMERADAN_RESİM_SECME_KODU);
    }

    // galeriden Açtırma
    private void galeridenSec() {
        // galeriden secilen resim onActivty result metotu ile alınacak
        Intent galeriIntent = new Intent(Intent.ACTION_PICK);
        galeriIntent.setType("image/*"); // burada resim ve (/*)resim türlerindekiler anlamına geliyor
        startActivityForResult(galeriIntent,GALERİDEN_RESİM_SECME_KODU);
    }

    //ActionBardaki geriye tıklandığında geriye gitmesi
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();// geri tuşuna basıldığında ne yapıyorsa onu yap
        return super.onSupportNavigateUp();
    }
    // depolam izni kontrolü
    private boolean depolamaIznıKontrolu(){
        boolean sonuc = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) ==( PackageManager.PERMISSION_GRANTED);
        return sonuc;
    }
    // depolamaIznıTalebi
    private void depolamaIzınTalebi(){
        ActivityCompat.requestPermissions(this,depolamaIzınleri,Depolama_TALEP_KODU);
    }
    // depolam izni kontrolü
    private boolean kameraIznıKontrolu(){
        boolean sonuc1 = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) ==( PackageManager.PERMISSION_GRANTED);
        boolean sonuc2 = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) ==( PackageManager.PERMISSION_GRANTED);
        return sonuc1 && sonuc2;
    }
    // kameraIznıTalebi
    private void kameraIzınTalebi(){
        ActivityCompat.requestPermissions(this,kameraIzınleri,KAMERA_TALEP_KODU);
    }
}










