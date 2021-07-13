package com.resurrection.localveritabanuygulamas;

public class VtSabitler {

    // veritabanı adı
    public static final String VT_ADI ="KAYITLARIM";

    // veritabanı version
    public static final int VT_VERSION =1;

    // veritabanı tablo adı
    public static final String TABLO_ADI ="KAYITLARIM_TABLO";


    // tablonun sutun alanlar
    public static final String S_ID ="ID";
    public static final String S_AD ="AD";
    public static final String S_RESIM ="RESIM";
    public static final String S_ACIKLAMA ="ACIKLAMA";
    public static final String S_TELEFON ="TELEFON";
    public static final String S_EMAIL ="EMAIL";
    public static final String S_DOGUM_TARIHI ="DOGUM_TARIHI";
    public static final String S_EKLENME_TARIHI ="EKLENME_TARIHI";
    public static final String S_GUNCELLEME_TARIHI ="GUNCELLEME_TARIHI";


    // sql tablo oluşturma kodu
    public static final String TABLO_OLUSTUR = "CREATE TABLE "+TABLO_ADI+ " ("
            + S_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            + S_AD +" TEXT,"
            + S_RESIM +" TEXT,"
            + S_ACIKLAMA +" TEXT,"
            + S_TELEFON +" TEXT,"
            + S_EMAIL +" TEXT,"
            + S_DOGUM_TARIHI +" TEXT,"
            + S_EKLENME_TARIHI +" TEXT,"
            + S_GUNCELLEME_TARIHI +" TEXT"
            +")";


}
