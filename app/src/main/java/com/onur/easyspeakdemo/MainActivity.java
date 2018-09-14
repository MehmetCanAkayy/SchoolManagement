package com.onur.easyspeakdemo;
import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebaseDemo.Artist;
import com.firebaseDemo.MyAdapter;
import com.firebaseDemo.StudentsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jackandphantom.blurimage.BlurImage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button giris;
    private TextView ad,sifre;
    private ImageView background;
    // List<String> kullanicilar = new ArrayList<String>();
    DatabaseReference databaseArtists;
    List<Artist> artistList;
    @Override
    protected void onStart() {
        super.onStart();
        //Retriving data From Firebase
        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                artistList.clear();

                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren() ){
                    //Create Artist Class Object and Returning Value
                    Artist artist = artistSnapshot.getValue(Artist.class);
                    artistList.add(artist);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Student Verisi Çekilemedi.");

            }
        });
    }


    //if(kullanicilar.contains("Osman")) {
    // System.out.println("Osman bulundu");

    //final veritabani vt=new veritabani(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseArtists = FirebaseDatabase.getInstance().getReference("students");
        artistList = new ArrayList<>();
        //Intent sayfagecis= new Intent(MainActivity.this, com.user.StudentRegister.class);
        //Intent sayfagecis= new Intent(MainActivity.this, dersSecimi.class);
        background=findViewById(R.id.background);


        //startActivity(sayfagecis);
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



        BlurImage.with(getApplicationContext()).load(R.drawable.backgorund).intensity(20).Async(true).into(background);
        giris= (Button) findViewById(R.id.giris);
        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //vt.VeriListele();
                Intent sayfagecis=new Intent(MainActivity.this, dersSecimi.class);
                String adi=ad.getText().toString();
                String sifresi=sifre.getText().toString();
                String kullanici;

                final Intent sayfaGecis = new Intent(MainActivity.this, dersSecimi.class);



                for (int i = 0 ; i <artistList.size();i++){
                    if(adi.equals(artistList.get(i).getPhoneNumber())&&sifresi.equals("111")){
                        //Log.d("deneme", "basarili");
                        Toast.makeText(getApplicationContext(), "Başarılı!Yönlendiriliyorsunuz...", Toast.LENGTH_SHORT).show();
                        sayfaGecis.putExtra("phoneNumber", adi);
                        sayfaGecis.putExtra("grade", artistList.get(i).getArtistGrade());

                        startActivityForResult(sayfaGecis, 1);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Yanlış kullanici adi veya şifre", Toast.LENGTH_SHORT).show();
                        //startActivity(sayfagecis);
                    }

                    }



                }

//
//                if (adi.equals(k_adlari[0]) && sifresi.equals(k_sifreleri[0]) || adi.equals(k_adlari[1]) && sifresi.equals(k_sifreleri[1]) || adi.equals(k_adlari[2]) && sifresi.equals(k_sifreleri[2])) {
//                    //Log.d("deneme", "basarili");
//                    Toast.makeText(getApplicationContext(), "Başarılı!Yönlendiriliyorsunuz...", Toast.LENGTH_SHORT).show();
//                    startActivity(sayfagecis);
//                    kullanici=adi;
//                }
//                else
//                    Toast.makeText(getApplicationContext(),"Yanlış kullanici adi veya şifre", Toast.LENGTH_SHORT).show();
//                //startActivity(sayfagecis);
              });

    }


}
