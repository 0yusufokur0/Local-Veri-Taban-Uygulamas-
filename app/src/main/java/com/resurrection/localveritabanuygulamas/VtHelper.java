package com.resurrection.localveritabanuygulamas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

// veri tabnında ki ekleme güncelleme okutma silme işlemlerinin yapılcağı java sınıfı (crud)
public class VtHelper extends SQLiteOpenHelper {

    // vferi tabını oluşturma
    public VtHelper(@Nullable Context context) {
        super(context, VtSabitler.VT_ADI,null,VtSabitler.VT_VERSION);

    }

    // tablo oluşturma
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // tablo oluştur
        sqLiteDatabase.execSQL(VtSabitler.TABLO_OLUSTUR);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        // Yapısal bir değişilik (upgrade ) oldupunuda eski tabloyu düşür ve yenisini oluştur

        // eski tablo duruyorsa kurtul düşür drop yap
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+VtSabitler.TABLO_ADI);
        // YENİDEN TABLO OLUŞTUR

        onCreate(sqLiteDatabase);

    }



    // veritabınına kayıt ekleme metotu
    public long kayitEkle(String ad ,String resim,String aciklama,String telefon,String email,String dogumtarihi,String eklenmetarihi,String guncellemetarihi){{
    }
        // veritabaınan veri eklnecceği yazılabiliri veri tabanı olması şlazıom
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        // içerik değerleri
        ContentValues degerler = new ContentValues();


        // ıd otomatik eklenceği için yazılmasına gerek yok
        // veri ekleme
        degerler.put(VtSabitler.S_AD,ad);
        degerler.put(VtSabitler.S_RESIM,resim);
        degerler.put(VtSabitler.S_ACIKLAMA,aciklama);
        degerler.put(VtSabitler.S_TELEFON,telefon);
        degerler.put(VtSabitler.S_EMAIL,email);
        degerler.put(VtSabitler.S_DOGUM_TARIHI,dogumtarihi);
        degerler.put(VtSabitler.S_EKLENME_TARIHI,eklenmetarihi);
        degerler.put(VtSabitler.S_GUNCELLEME_TARIHI,guncellemetarihi);
        // satırı id göre ekle boş sutun kalmasın
        long id = sqLiteDatabase.insert(VtSabitler.TABLO_ADI,null,degerler);

        // ver tabanını kapat
        sqLiteDatabase.close();

        // id değerini döndür
        return id;




    }

    // verileri veri tabanından al
    public ArrayList<Ornekkayit> butunKayitlariAl(String sirala){
        // sirala verileri yenidden sirala eskiye sırayaklacak
        // veriyi döngü ile bütün tablodan alıp listeye aktarımı
        ArrayList<Ornekkayit> kayitlarListesi = new ArrayList<>();
        // Secim Sorgusu
        String secimSorgusu = "SELECT * FROM "+VtSabitler.TABLO_ADI +" ORDER BY " +sirala;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        // bütün satırları ve everieri dolaşmak amaçlı cursor oluşturma
        Cursor cursor = sqLiteDatabase.rawQuery(secimSorgusu,null);
        // cursoru bütün kayıtlarda döngü ile dolaşştırıp kayıtları alam
        if (cursor.moveToFirst()) {

            do {
                Ornekkayit ornekkayit = new Ornekkayit(
                        "" + cursor.getInt(cursor.getColumnIndex(VtSabitler.S_ID)),
                        "" + cursor.getString(cursor.getColumnIndex(VtSabitler.S_AD)),
                        "" + cursor.getString(cursor.getColumnIndex(VtSabitler.S_RESIM)),
                        "" + cursor.getString(cursor.getColumnIndex(VtSabitler.S_TELEFON)),
                        "" + cursor.getString(cursor.getColumnIndex(VtSabitler.S_ACIKLAMA)),
                        "" + cursor.getString(cursor.getColumnIndex(VtSabitler.S_EMAIL)),
                        "" + cursor.getString(cursor.getColumnIndex(VtSabitler.S_DOGUM_TARIHI)),
                        "" + cursor.getString(cursor.getColumnIndex(VtSabitler.S_EKLENME_TARIHI)),
                        "" + cursor.getString(cursor.getColumnIndex(VtSabitler.S_GUNCELLEME_TARIHI)));


                // veri tabaıonından alının veriyi listeye ekle
                kayitlarListesi.add(ornekkayit);
            } while (cursor.moveToNext());
            // veri tabını kapat
            sqLiteDatabase.close();
        }
        return kayitlarListesi;
    }

    // kaç adet satır var
    public int kayitSayisiAl(){

        // sorgu
        String sayiSorugus = "SELECT * FROM "+VtSabitler.TABLO_ADI;
        // veritabanı çalıştır
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        // bütün satırları cursor ile dolaş
        Cursor cursor = sqLiteDatabase.rawQuery(sayiSorugus,null);
        // cursor sayısı
        int veriSayısı = cursor.getCount();
        return veriSayısı;
    }



}
