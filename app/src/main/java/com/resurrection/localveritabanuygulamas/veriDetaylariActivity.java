package com.resurrection.localveritabanuygulamas;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class veriDetaylariActivity extends AppCompatActivity {
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veri_detaylari);

        // action bar
        actionBar = getSupportActionBar();

        actionBar.setTitle("veri detayları");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // geri tuşuna tıklamndığında yapılanı yapğ demek
        return super.onSupportNavigateUp();
    }
}