package com.resurrection.localveritabanuygulamas;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

public class veriDetaylariActivity extends AppCompatActivity {
    ActionBar actionBar;
    String veriId;

    ImageView profilResmi;
    TextView tvAd, tvEmail, tvTelefon, tvAciklama, tvDogumtarihi, tvEklenmeTarihi, tvGuncellemeTarihi;
    // veritabanı
    private VtHelper vtHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veri_detaylari);

        // action bar
        actionBar = getSupportActionBar();

        actionBar.setTitle("veri detayları");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        profilResmi = findViewById(R.id.profilRsmiDetay);
        tvAd = findViewById(R.id.txt_adı);
        tvEmail = findViewById(R.id.txt_email);
        tvTelefon = findViewById(R.id.txt_telefon);
        tvAciklama = findViewById(R.id.txt_aciklama);
        tvDogumtarihi = findViewById(R.id.txt_dogumtarihi);
        tvEklenmeTarihi = findViewById(R.id.txt_eklenemetarrihi);
        tvGuncellemeTarihi = findViewById(R.id.txt_guncellemtarrihi);


        //veritabanı
        vtHelper = new VtHelper(this);


        // adaptörden gelen id al
        veriId = getIntent().getStringExtra("VERI_ID");
        veriDetaylarınıGoster();



    }

    private void veriDetaylarınıGoster() {
        // id ye göre veri tabanından verileri alma sorgusu
        String idYeGoreSorgu = " SELECT * FROM " + VtSabitler.TABLO_ADI + " WHERE " + VtSabitler.S_ID + " =\"" + veriId + "\"";

        // veritabanını çalıştır
        SQLiteDatabase sqLiteDatabase = vtHelper.getWritableDatabase();

        // bürün alanları alma amaçlı cursor
        Cursor cursor = sqLiteDatabase.rawQuery(idYeGoreSorgu, null);

        // Cursor çalışmaya başladığında
        if (cursor.moveToFirst()) {
            do {
                String id = "" + cursor.getInt(cursor.getColumnIndex(VtSabitler.S_ID));
                String ad = "" + cursor.getString(cursor.getColumnIndex(VtSabitler.S_AD));
                String resim = "" + cursor.getString(cursor.getColumnIndex(VtSabitler.S_RESIM));
                String telefon = "" + cursor.getString(cursor.getColumnIndex(VtSabitler.S_TELEFON));
                String email = "" + cursor.getString(cursor.getColumnIndex(VtSabitler.S_EMAIL));
                String aciklama = "" + cursor.getString(cursor.getColumnIndex(VtSabitler.S_ACIKLAMA));
                String dogumtarihi = "" + cursor.getString(cursor.getColumnIndex(VtSabitler.S_DOGUM_TARIHI));
                String eklenenZamanDilimi = "" + cursor.getString(cursor.getColumnIndex(VtSabitler.S_EKLENME_TARIHI));
                String guncellemeZamanDilimi = "" + cursor.getString(cursor.getColumnIndex(VtSabitler.S_GUNCELLEME_TARIHI));

                // ZAMANLAIN FORMATINI AYARLA

                Calendar takvim = Calendar.getInstance(Locale.getDefault());
                takvim.setTimeInMillis(Long.parseLong(eklenenZamanDilimi));
                String eklemeZamani = "" + DateFormat.format(" dd/MM/yyyy hh:mm:aa ", takvim);

                Calendar takvim2 = Calendar.getInstance(Locale.getDefault());
                takvim2.setTimeInMillis(Long.parseLong(guncellemeZamanDilimi));
                String guncellemeZamani = "" + DateFormat.format(" dd/MM/yyyy hh:mm:aa ", takvim2);

                tvAd.setText(ad);
                tvTelefon.setText(telefon);
                tvEmail.setText(email);
                tvAciklama.setText(aciklama);
                tvDogumtarihi.setText(dogumtarihi);
                tvEklenmeTarihi.setText(eklemeZamani);
                tvGuncellemeTarihi.setText(guncellemeZamani);


                if (resim.equals("null")) {
                    profilResmi.setImageResource(R.drawable.ic_baseline_person_24);
                } else {
                    profilResmi.setImageURI(Uri.parse(resim));
                }


            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // geri tuşuna tıklamndığında yapılanı yapğ demek
        return super.onSupportNavigateUp();
    }
}











