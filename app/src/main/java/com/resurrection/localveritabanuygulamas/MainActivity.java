package com.resurrection.localveritabanuygulamas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton btnKayitEkle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnKayitEkle = findViewById(R.id.btn_kayit_ekle);

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
}