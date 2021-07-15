package com.resurrection.localveritabanuygulamas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton btnKayitEkle;
    private RecyclerView rcVeriler;
    // veritabnanı helper
    VtHelper vtHelper;
    // Sıralama secenekleri
    String enYeniyeGöreSırala = VtSabitler.S_EKLENME_TARIHI + " DESC";
    String enEskiyeGöreSırala = VtSabitler.S_EKLENME_TARIHI + " ASC";
    String aDanZYeSırala = VtSabitler.S_AD + " ASC";
    String ZDanAYeSırala = VtSabitler.S_AD + " DESC";

    // son secilen sıraya göre kayıtarı sırala
    String mevcutSiralamaTuru = enYeniyeGöreSırala;


    // Action Bar
    ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnKayitEkle = findViewById(R.id.btn_kayit_ekle);
        rcVeriler = findViewById(R.id.rvKayitlar);
        vtHelper = new VtHelper(this);
        // actionBar
        actionBar = getSupportActionBar();
        actionBar.setTitle("bütün kayıtlar");

        verileriYukle(enYeniyeGöreSırala);

        // kayıt ekle butonun tıkladığımızda ne yapsın
        btnKayitEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //kayıt ekle güncelle sayfasına gitsin
                Intent intent = new Intent(MainActivity.this,KayitEkleGuncelle.class);
                startActivity(intent);
            }
        });
    }
    private void verileriYukle(String siralama){
        mevcutSiralamaTuru = siralama;
        kayitAdaptor kayitAdaptor  = new kayitAdaptor(MainActivity.this,vtHelper.butunKayitlariAl(siralama));
        rcVeriler.setAdapter(kayitAdaptor);

        // action bara veri sayısını alt başlık olrak ekle
        actionBar.setSubtitle("Veri Sayısı:"+vtHelper.kayitSayisiAl());

    }

    @Override
    protected void onResume() {

        verileriYukle(mevcutSiralamaTuru);
        super.onResume();
    }

    // menu xml i bağlmak için

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ogeleri,menu);
        return super.onCreateOptionsMenu(menu);
    }

    // menu öğrelerine tıklamak için

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.menu_sil){
            vtHelper.hepsiniSil();
            onResume();
        }
        if (id == R.id.menu_sirala){
            // sırlama
            sıralamaDialog();

        }

        return super.onOptionsItemSelected(item);
    }

    private void sıralamaDialog() {
        String[] ogeler ={"En Yeniye Göre Sırala","En eskiye Göre sırala","a dan ze ye ","z e en a ya sırala"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sırala");
        builder.setItems(ogeler, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    verileriYukle(enYeniyeGöreSırala);
                }
                if (which == 1){
                    verileriYukle(enEskiyeGöreSırala);
                }
                if (which == 2){
                    verileriYukle(aDanZYeSırala);
                }
                if (which == 3){
                    verileriYukle(ZDanAYeSırala);
                }
            }
        });
        builder.create().show();
    }
}



















