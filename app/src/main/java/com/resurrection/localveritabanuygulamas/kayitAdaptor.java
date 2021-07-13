package com.resurrection.localveritabanuygulamas;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class kayitAdaptor extends RecyclerView.Adapter<kayitAdaptor.kayitTutucu> {
    private Context context;
    private ArrayList<Ornekkayit> ornekList;

    public kayitAdaptor(Context context, ArrayList<Ornekkayit> ornekList) {
        this.context = context;
        this.ornekList = ornekList;
    }

    // satırı görünümünü adaptore bağlama
    @NonNull
    @Override
    public kayitTutucu onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_satir_gorunumu,parent,false);
        return new kayitTutucu(view);

    }


    // verileri alma kontrollere aktarma satırlara yada alt ogelere tıklama gibi işlemler
    @Override
    public void onBindViewHolder(@NonNull kayitAdaptor.kayitTutucu holder, int position) {
        // verleri alma
        Ornekkayit ornekkayit = ornekList.get(position);
        String id = ornekkayit.getId();
        String ad = ornekkayit.getAd();
        String resim = ornekkayit.getResim();
        String aciklama = ornekkayit.getAciklama();
        String telefon = ornekkayit.getTelefon();
        String email = ornekkayit.getEmail();
        String dorumtarihi = ornekkayit.getDogumTarihi();
        String eklemeZamani = ornekkayit.getEklemeTarihi();
        String gumcellemeZamani = ornekkayit.getGuncellemeTarihi();

        System.out.println("ornek kayit .get ad ");
        System.out.println(ornekkayit.getAd());


        // verileri yazdır
        holder.tvAd.setText(ad);
        holder.tvEmail.setText(email);
        holder.tvDogumTarihi.setText(dorumtarihi);
        holder.tvTelefon.setText(telefon);

        //resmi yazdır
        if (resim.equals("null")){
            // resim boşsa varsayılan resim aktar
            holder.profilResmi.setImageResource(R.drawable.ic_baseline_person_24);
        }else {
            // veritabanında eklenmiş resim varsa
            holder.profilResmi.setImageURI(Uri.parse(resim));
        }

        holder.btnSilGuncell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "sil ve güncelle", Toast.LENGTH_SHORT).show();
            }
        });

        // herbir sdatıra tıklandığında
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent veriDetayları = new Intent(context,veriDetaylariActivity.class);
                // ilgi id ye gömre veri detayı göstermek amaçlı id yi gönderiyoruz
                veriDetayları.putExtra("VERI_ID",id);
                context.startActivity(veriDetayları);
            }
        });


    }

    // tablodaki satır sayısı
    @Override
    public int getItemCount() {
        return ornekList.size();
    }

    // kontrol tanımlamaları
    public class kayitTutucu extends RecyclerView.ViewHolder {

        ImageView profilResmi;
        TextView tvAd,tvTelefon,tvEmail,tvDogumTarihi;
        ImageButton btnSilGuncell;

        public kayitTutucu(@NonNull View itemView) {
            super(itemView);

            profilResmi = itemView.findViewById(R.id.profil_resmi);
            tvAd = itemView.findViewById(R.id.tv_Ad);
            tvTelefon = itemView.findViewById(R.id.tv_telefon);
            tvDogumTarihi = itemView.findViewById(R.id.tv_dogumTarihi);
            tvEmail = itemView.findViewById(R.id.tv_email);
            btnSilGuncell = itemView.findViewById(R.id.btn_SilGuncelle);


        }
    }
}

