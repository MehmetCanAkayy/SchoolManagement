package com.onur.easyspeakdemo;
import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button giris;
    private TextView ad,sifre;
    // List<String> kullanicilar = new ArrayList<String>();




    //if(kullanicilar.contains("Osman")) {
    // System.out.println("Osman bulundu");

    //final veritabani vt=new veritabani(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ad=(TextView)findViewById(R.id.kul_ad);
        sifre=(TextView) findViewById(R.id.kul_sifre);
        final String[] k_adlari = new String[10];
        k_adlari[0]="nes";
        k_adlari[1]="onr";
        k_adlari[2]="meh";

        System.out.println("Hello");
        final String[] k_sifreleri = new String[10];
        k_sifreleri[0]="111";
        k_sifreleri[1]="222";
        k_sifreleri[2]="333";




        giris= (Button) findViewById(R.id.giris);
        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //vt.VeriListele();
                Intent sayfagecis=new Intent(MainActivity.this, dersSecimi.class);
                String adi=ad.getText().toString();
                String sifresi=sifre.getText().toString();
                String kullanici;


                if (adi.equals(k_adlari[0]) && sifresi.equals(k_sifreleri[0]) || adi.equals(k_adlari[1]) && sifresi.equals(k_sifreleri[1]) || adi.equals(k_adlari[2]) && sifresi.equals(k_sifreleri[2])) {
                    //Log.d("deneme", "basarili");
                    Toast.makeText(getApplicationContext(), "Başarılı!Yönlendiriliyorsunuz...", Toast.LENGTH_SHORT).show();
                    startActivity(sayfagecis);
                    kullanici=adi;
                }
                else
                    Toast.makeText(getApplicationContext(),"Yanlış kullanici adi veya şifre", Toast.LENGTH_SHORT).show();
                //startActivity(sayfagecis);
            }  });

    }


}
